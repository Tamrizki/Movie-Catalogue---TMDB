package tam.pa.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import tam.pa.moviecatalogue.data.local.LocalDataSource
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity
import tam.pa.moviecatalogue.data.remote.RemoteDataSource
import tam.pa.moviecatalogue.data.remote.response.*
import tam.pa.moviecatalogue.utils.AppExecutors
import tam.pa.moviecatalogue.vo.Resource

class CatalogueRepository (
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
    ): CatalogueDataSource {

    override fun dataMovie(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<ResultsItem>>(appExecutors){

            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovie(), config).build()
            }

            override fun shouldFecth(data: PagedList<MovieEntity>?): Boolean = data == null || data.isEmpty()

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

    override fun dataSerialTv(): LiveData<Resource<PagedList<SerialTvEntity>>> {
        return object : NetworkBoundResource<PagedList<SerialTvEntity>, List<TVResultsItem>>(appExecutors){

            override fun loadFromDB(): LiveData<PagedList<SerialTvEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTv(), config).build()
            }

            override fun shouldFecth(data: PagedList<SerialTvEntity>?): Boolean = data == null || data.isEmpty()

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

    override fun setFavoriteMovie(movie: MovieEntity, state: Boolean) =
        appExecutors.diskIO().execute {
            localDataSource.updateMovie(movie, state)
        }

    override fun setFavoriteTv(tv: SerialTvEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.updateTv(tv, state)
        }
    }

    override fun findMovie(id: Int): LiveData<MovieEntity> = localDataSource.findMovie( id )

    override fun findSerialTv(id: Int): LiveData<SerialTvEntity> = localDataSource.findTv( id )

    override fun favoriteMovie(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovie(), config).build()
    }

    override fun favoriteSerialTv(): LiveData<PagedList<SerialTvEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTv(), config).build()
    }
}