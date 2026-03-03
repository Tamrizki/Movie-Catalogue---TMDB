package tam.pa.moviecatalogue.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import tam.pa.moviecatalogue.data.CatalogueRepository
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity
import tam.pa.moviecatalogue.vo.Resource

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var pagedListMovie: PagedList<MovieEntity>

    @Mock
    private lateinit var pagedListTv: PagedList<SerialTvEntity>

    @Mock
    private lateinit var repository: CatalogueRepository

    @Mock
    private lateinit var observerSerialTv: Observer<Resource<PagedList<SerialTvEntity>>>

    @Mock
    private lateinit var observeMovie: Observer<Resource<PagedList<MovieEntity>>>

    @Before
    fun setUp() {
        viewModel = HomeViewModel( repository )
    }

    @Test
    fun getMovie() {
        val dummyMovie = Resource.success(pagedListMovie)
        `when`(dummyMovie.data?.size).thenReturn(10)
        val movie = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movie.value = dummyMovie

        `when`(repository.dataMovie()).thenReturn(movie)
        val movieEntity = viewModel.getMovie().value?.data
        verify(repository).dataMovie()
        assertNotNull(movieEntity)
        assertEquals(10, movieEntity?.size)

        viewModel.getMovie().observeForever(observeMovie)
        verify(observeMovie).onChanged(dummyMovie)
    }

    @Test
    fun getSerialTv() {
        val dummyTv = Resource.success(pagedListTv)
        `when`(dummyTv.data?.size).thenReturn(10)
        val tv = MutableLiveData<Resource<PagedList<SerialTvEntity>>>()
        tv.value = dummyTv

        `when`(repository.dataSerialTv()).thenReturn(tv)
        val tvEntity = viewModel.getSerialTv().value?.data
        verify(repository).dataSerialTv()
        assertNotNull(tvEntity)
        assertEquals(10, tvEntity?.size)

        viewModel.getSerialTv().observeForever(observerSerialTv)
        verify(observerSerialTv).onChanged(dummyTv)
    }
}