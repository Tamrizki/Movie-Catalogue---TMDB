package tam.pa.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import tam.pa.moviecatalogue.domain.model.Movie
import tam.pa.moviecatalogue.domain.model.TvSeries
import tam.pa.moviecatalogue.domain.usecase.CatalogueUseCase
import tam.pa.moviecatalogue.vo.Resource

class DetailViewModel(
    private val useCase: CatalogueUseCase
): ViewModel() {

    fun findMovie(id: Int) : LiveData<Movie> = useCase.getMovieDetail(id)

    fun findTv(id: Int) : LiveData<TvSeries> = useCase.getTvSeriesDetail(id)

    fun setFavoriteMovie(movie: Movie){
        val newState = !movie.isFavorite
        useCase.setFavoriteMovie(movie, newState)
    }

    fun setFavoriteTv(tv: TvSeries){
        val newState = !tv.isFavorite
        useCase.setFavoriteTvSeries(tv, newState)
    }

    fun getMovie(): LiveData<Resource<PagedList<Movie>>> = useCase.getAllMovies()
}
