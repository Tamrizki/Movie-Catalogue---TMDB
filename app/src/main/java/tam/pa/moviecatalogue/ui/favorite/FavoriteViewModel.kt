package tam.pa.moviecatalogue.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import tam.pa.moviecatalogue.data.CatalogueRepository
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity

class FavoriteViewModel(private val repository: CatalogueRepository): ViewModel() {

    fun getFavoriteMovie() : LiveData<PagedList<MovieEntity>> =  repository.favoriteMovie()

    fun getFavoriteTv() : LiveData<PagedList<SerialTvEntity>> =  repository.favoriteSerialTv()

    fun setFavoriteMovie(movie: MovieEntity){
        val entity = movie
        val newState = !entity.favorite
        repository.setFavoriteMovie(entity, newState)
    }

    fun setFavoriteTv(tv: SerialTvEntity){
        val entity = tv
        val newState = !entity.favorite
        repository.setFavoriteTv(entity, newState)
    }
}