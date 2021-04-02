package tam.pa.moviecatalogue.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import tam.pa.moviecatalogue.data.CatalogueRepository
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity
import tam.pa.moviecatalogue.vo.Resource

class HomeViewModel(
    private val repository: CatalogueRepository
    ): ViewModel() {

    fun getMovie(): LiveData<Resource<PagedList<MovieEntity>>> = repository.dataMovie()

    fun getSerialTv() : LiveData<Resource<PagedList<SerialTvEntity>>> = repository.dataSerialTv()

}