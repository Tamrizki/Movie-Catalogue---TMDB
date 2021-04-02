package tam.pa.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import tam.pa.moviecatalogue.data.CatalogueRepository
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity
import tam.pa.moviecatalogue.vo.Resource

class DetailViewModel(
    private val repository: CatalogueRepository
): ViewModel() {
    fun getMovie() : LiveData<Resource<PagedList<MovieEntity>>> = repository.dataMovie()

    fun findMovie(id: Int) : LiveData<MovieEntity> = repository.findMovie( id )

    fun findTv(id: Int) : LiveData<SerialTvEntity> = repository.findSerialTv( id )

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