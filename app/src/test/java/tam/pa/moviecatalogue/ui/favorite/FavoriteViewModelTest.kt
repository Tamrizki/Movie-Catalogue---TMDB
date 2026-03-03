package tam.pa.moviecatalogue.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnitRunner
import tam.pa.moviecatalogue.data.CatalogueRepository
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity
import tam.pa.moviecatalogue.utils.DataDummy
import tam.pa.moviecatalogue.vo.Resource

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest{
    private lateinit var viewModel: FavoriteViewModel
    private val movieDummy = DataDummy.detail()
    private val tvDummy = DataDummy.detailTvSeries()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: CatalogueRepository

    @Mock
    private lateinit var pagedListMovie: PagedList<MovieEntity>

    @Mock
    private lateinit var pagedListTv: PagedList<SerialTvEntity>

    @Mock
    private lateinit var observerTv: Observer<PagedList<SerialTvEntity>>

    @Mock
    private lateinit var observeMovie: Observer<PagedList<MovieEntity>>

    @Before
    fun setup(){
        viewModel = FavoriteViewModel( repository )
    }

    @Test
    fun getFavoriteMovie() {
        val dummyMovie = Resource.success(pagedListMovie)
        `when`(dummyMovie.data?.size).thenReturn(10)
        val movie = MutableLiveData<PagedList<MovieEntity>>()
        movie.value = pagedListMovie

        `when`(repository.favoriteMovie()).thenReturn(movie)
        val movieEntity = viewModel.getFavoriteMovie().value
        verify(repository).favoriteMovie()
        assertNotNull(movieEntity)
        assertEquals(10, movieEntity?.size)

        viewModel.getFavoriteMovie().observeForever(observeMovie)
        verify(observeMovie).onChanged(pagedListMovie)
    }

    @Test
    fun getFavoriteTv() {
        val dummyTv = Resource.success(pagedListTv)
        `when`(dummyTv.data?.size).thenReturn(10)
        val tv = MutableLiveData<PagedList<SerialTvEntity>>()
        tv.value = pagedListTv

        `when`(repository.favoriteSerialTv()).thenReturn(tv)
        val tvEntity = viewModel.getFavoriteTv().value
        verify(repository).favoriteSerialTv()
        assertNotNull(tvEntity)
        assertEquals(10, tvEntity?.size)

        viewModel.getFavoriteTv().observeForever(observerTv)
        verify(observerTv).onChanged(pagedListTv)
    }

    @Test
    fun setFavoriteMovie(){
        val movie = movieDummy
        val state = !movieDummy.favorite

        doNothing().`when`(repository).setFavoriteMovie(movie, state)
        viewModel.setFavoriteMovie(movie)
        verify(repository, times(1)).setFavoriteMovie(movie, state)
    }

    @Test
    fun setFavoriteTv(){
        val tv = tvDummy
        val state = !tvDummy.favorite

        doNothing().`when`(repository).setFavoriteTv(tv, state)
        viewModel.setFavoriteTv(tv)
        verify(repository, times(1)).setFavoriteTv(tv, state)
    }
}