package tam.pa.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import tam.pa.moviecatalogue.data.local.LocalDataSource
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity
import tam.pa.moviecatalogue.data.remote.RemoteDataSource
import tam.pa.moviecatalogue.data.remote.response.*
import tam.pa.moviecatalogue.domain.model.Movie
import tam.pa.moviecatalogue.domain.model.TvSeries
import tam.pa.moviecatalogue.domain.repository.ICatalogueRepository
import tam.pa.moviecatalogue.utils.AppExecutors
import tam.pa.moviecatalogue.utils.DataMapper
import tam.pa.moviecatalogue.vo.Resource

class CatalogueRepository (
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
    ): ICatalogueRepository {

    override fun getAllMovies(): LiveData<Resource<PagedList<Movie>>> {
        return object : NetworkBoundResource<PagedList<Movie>, List<ResultsItem>>(appExecutors){

            override fun loadFromDB(): LiveData<PagedList<Movie>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                
                val dataSourceFactory = localDataSource.getAllMovie().map { DataMapper.mapMovieEntityToDomain(it) }
                return LivePagedListBuilder(dataSourceFactory, config).build()
            }

            override fun shouldFecth(data: PagedList<Movie>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<Resource<List<ResultsItem>>> = remoteDataSource.getMovie()

            override fun saveCallResult(data: List<ResultsItem>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data){
                    val movie = MovieEntity(
                        response.id,
                    response.originalTitle,
                    response.overview,
                    response.posterPath,
                    response.releaseDate,
                    response.voteAverage,
                    response.backdropPath)
                    movieList.add( movie )
                }
                localDataSource.insertMovie(movieList)
            }

        }.asLiveData()
    }

    override fun getAllTvSeries(): LiveData<Resource<PagedList<TvSeries>>> {
        return object : NetworkBoundResource<PagedList<TvSeries>, List<TVResultsItem>>(appExecutors){

            override fun loadFromDB(): LiveData<PagedList<TvSeries>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                val dataSourceFactory = localDataSource.getAllTv().map { DataMapper.mapTvEntityToDomain(it) }
                return LivePagedListBuilder(dataSourceFactory, config).build()
            }

            override fun shouldFecth(data: PagedList<TvSeries>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<Resource<List<TVResultsItem>>> = remoteDataSource.getTvSeries()

            override fun saveCallResult(data: List<TVResultsItem>) {
                val listTv = ArrayList<SerialTvEntity>()
                for (response in data){
                    val tv = SerialTvEntity(
                        response.id,
                        response.firstAirDate,
                        response.name,
                        response.overview,
                        response.posterPath,
                        response.voteAverage,
                        response.backdropPath
                    )
                    listTv.add( tv )
                }
                localDataSource.insertTv( listTv )
            }

        }.asLiveData()
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapMovieDomainToEntity(movie)
        appExecutors.diskIO().execute {
            localDataSource.updateMovie(movieEntity, state)
        }
    }

    override fun setFavoriteTvSeries(tvSeries: TvSeries, state: Boolean) {
        val tvEntity = DataMapper.mapTvDomainToEntity(tvSeries)
        appExecutors.diskIO().execute {
            localDataSource.updateTv(tvEntity, state)
        }
    }

    override fun getMovieDetail(id: Int): LiveData<Movie> {
        return Transformations.map(localDataSource.findMovie(id)) {
            DataMapper.mapMovieEntityToDomain(it)
        }
    }

    override fun getTvSeriesDetail(id: Int): LiveData<TvSeries> {
        return Transformations.map(localDataSource.findTv(id)) {
            DataMapper.mapTvEntityToDomain(it)
        }
    }

    override fun getFavoriteMovies(): LiveData<PagedList<Movie>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build()
        val dataSourceFactory = localDataSource.getFavoriteMovie().map { DataMapper.mapMovieEntityToDomain(it) }
        return LivePagedListBuilder(dataSourceFactory, config).build()
    }

    override fun getFavoriteTvSeries(): LiveData<PagedList<TvSeries>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build()
        val dataSourceFactory = localDataSource.getFavoriteTv().map { DataMapper.mapTvEntityToDomain(it) }
        return LivePagedListBuilder(dataSourceFactory, config).build()
    }
}
