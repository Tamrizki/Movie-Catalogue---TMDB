package tam.pa.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import tam.pa.moviecatalogue.data.CatalogueRepository
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity
import tam.pa.moviecatalogue.utils.DataDummy
import tam.pa.moviecatalogue.vo.Resource

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    lateinit var viewModel: DetailViewModel
    private val movieId = 527774
    private val tvSeriesId = 85271
    private val dummyMovie = DataDummy.detail()
    private val dummyTv = DataDummy.detailTvSeries()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var pagedListMovie: PagedList<MovieEntity>

    @Mock
    private lateinit var repository: CatalogueRepository

    @Mock
    private lateinit var observerListMovie: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var observerMovie: Observer<MovieEntity>

    @Mock
    private lateinit var observerTvSeries: Observer<SerialTvEntity>

    @Before
    fun setUp() {
        viewModel = DetailViewModel( repository )
    }

    @Test
    fun findMovie() {
        val movie = MutableLiveData<MovieEntity>()
        movie.value = dummyMovie
        `when`(repository.findMovie(movieId)).thenReturn(movie)
        viewModel.findMovie(movieId).observeForever(observerMovie)
        verify(observerMovie).onChanged(dummyMovie)
    }

    @Test
    fun findTv() {
        val serialTv = MutableLiveData<SerialTvEntity>()
        serialTv.value = dummyTv
        `when`(repository.findSerialTv(tvSeriesId)).thenReturn(serialTv)
        viewModel.findTv(tvSeriesId).observeForever(observerTvSeries)
        verify(observerTvSeries).onChanged(dummyTv)
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

        viewModel.getMovie().observeForever(observerListMovie)
        verify(observerListMovie).onChanged(dummyMovie)
    }

    @Test
    fun setFavoriteMovie(){
        val movie = dummyMovie
        val state = !dummyMovie.favorite

        doNothing().`when`(repository).setFavoriteMovie(movie, state)
        viewModel.setFavoriteMovie(movie)
        verify(repository, Mockito.times(1)).setFavoriteMovie(movie, state)
    }

    @Test
    fun setFavoriteTv(){
        val tv = dummyTv
        val state = !dummyTv.favorite

        doNothing().`when`(repository).setFavoriteTv(tv, state)
        viewModel.setFavoriteTv(tv)
        verify(repository, Mockito.times(1)).setFavoriteTv(tv, state)
    }
}