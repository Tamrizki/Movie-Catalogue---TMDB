package tam.pa.moviecatalogue.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import tam.pa.moviecatalogue.domain.model.Movie
import tam.pa.moviecatalogue.domain.model.TvSeries
import tam.pa.moviecatalogue.domain.usecase.CatalogueUseCase

class FavoriteViewModel(private val useCase: CatalogueUseCase): ViewModel() {

    fun getFavoriteMovie() : LiveData<PagedList<Movie>> =  useCase.getFavoriteMovies()

    fun getFavoriteTv() : LiveData<PagedList<TvSeries>> =  useCase.getFavoriteTvSeries()

    fun setFavoriteMovie(movie: Movie){
        val newState = !movie.isFavorite
        useCase.setFavoriteMovie(movie, newState)
    }

    fun setFavoriteTv(tv: TvSeries){
        val newState = !tv.isFavorite
        useCase.setFavoriteTvSeries(tv, newState)
    }
}