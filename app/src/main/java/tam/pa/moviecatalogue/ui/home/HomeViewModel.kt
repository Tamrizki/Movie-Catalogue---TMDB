package tam.pa.moviecatalogue.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import tam.pa.moviecatalogue.domain.model.Movie
import tam.pa.moviecatalogue.domain.model.TvSeries
import tam.pa.moviecatalogue.domain.usecase.CatalogueUseCase
import tam.pa.moviecatalogue.vo.Resource

class HomeViewModel(
    private val useCase: CatalogueUseCase
    ): ViewModel() {

    fun getMovie(): LiveData<Resource<PagedList<Movie>>> = useCase.getAllMovies()

    fun getSerialTv() : LiveData<Resource<PagedList<TvSeries>>> = useCase.getAllTvSeries()

}
