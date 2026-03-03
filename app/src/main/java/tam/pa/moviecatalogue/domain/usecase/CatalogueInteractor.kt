package tam.pa.moviecatalogue.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import tam.pa.moviecatalogue.domain.model.Movie
import tam.pa.moviecatalogue.domain.model.TvSeries
import tam.pa.moviecatalogue.domain.repository.ICatalogueRepository
import tam.pa.moviecatalogue.vo.Resource

class CatalogueInteractor(private val catalogueRepository: ICatalogueRepository) : CatalogueUseCase {
    override fun getAllMovies(): LiveData<Resource<PagedList<Movie>>> =
        catalogueRepository.getAllMovies()

    override fun getAllTvSeries(): LiveData<Resource<PagedList<TvSeries>>> =
        catalogueRepository.getAllTvSeries()

    override fun getFavoriteMovies(): LiveData<PagedList<Movie>> =
        catalogueRepository.getFavoriteMovies()

    override fun getFavoriteTvSeries(): LiveData<PagedList<TvSeries>> =
        catalogueRepository.getFavoriteTvSeries()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) =
        catalogueRepository.setFavoriteMovie(movie, state)

    override fun setFavoriteTvSeries(tvSeries: TvSeries, state: Boolean) =
        catalogueRepository.setFavoriteTvSeries(tvSeries, state)

    override fun getMovieDetail(id: Int): LiveData<Movie> =
        catalogueRepository.getMovieDetail(id)

    override fun getTvSeriesDetail(id: Int): LiveData<TvSeries> =
        catalogueRepository.getTvSeriesDetail(id)
}
