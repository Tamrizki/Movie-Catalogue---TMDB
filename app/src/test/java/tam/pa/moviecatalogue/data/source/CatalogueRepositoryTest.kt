package tam.pa.moviecatalogue.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.*
import org.mockito.Mockito.mock
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import tam.pa.moviecatalogue.data.CatalogueRepository
import tam.pa.moviecatalogue.data.local.LocalDataSource
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity
import tam.pa.moviecatalogue.data.remote.RemoteDataSource
import tam.pa.moviecatalogue.utils.AppExecutors
import tam.pa.moviecatalogue.utils.DataDummy
import tam.pa.moviecatalogue.utils.LiveDataTestUtil
import tam.pa.moviecatalogue.utils.PagedListUtil
import tam.pa.moviecatalogue.vo.Resource
import java.util.concurrent.Executors

class CatalogueRepositoryTest{

    @get:Rule
    var instantTaskExecuteRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val reposotory = CatalogueRepository(local, remote, appExecutors)

    private val movieDummy = DataDummy.movie()
    private val tvSeriesDummy = DataDummy.serialTv()
    private val detailDummy = DataDummy.detail()
    private val detailtvSeriesDummy = DataDummy.detailTvSeries()
    private val detailId = 527774
    private val detailTvSeriesId = 85271

    @Test
    fun dataMovie(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovie()).thenReturn(dataSourceFactory)
        reposotory.dataMovie()

        val movieEntity = Resource.success(PagedListUtil.mockPagedList(movieDummy.results))

        verify(local).getAllMovie()
        assertNotNull(movieEntity)
        assertEquals(movieDummy.results.size, movieEntity.data?.size)
    }

    @Test
    fun dataSerialTv(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, SerialTvEntity>
        `when`(local.getAllTv()).thenReturn(dataSourceFactory)
        reposotory.dataSerialTv()

        val tvEntity = Resource.success(PagedListUtil.mockPagedList(tvSeriesDummy.results))

        verify(local).getAllTv()
        assertNotNull(tvEntity)
        assertEquals(tvSeriesDummy.results.size, tvEntity.data?.size)
    }

    @Test
    fun favoriteMovie(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getFavoriteMovie()).thenReturn(dataSourceFactory)
        reposotory.favoriteMovie()

        val movieEntity = Resource.success(PagedListUtil.mockPagedList(movieDummy.results))

        verify(local).getFavoriteMovie()
        assertNotNull(movieEntity)
        assertEquals(movieDummy.results.size, movieEntity.data?.size)
    }

    @Test
    fun favoriteSerialTv(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, SerialTvEntity>
        `when`(local.getFavoriteTv()).thenReturn(dataSourceFactory)
        reposotory.favoriteSerialTv()

        val tvEntity = Resource.success(PagedListUtil.mockPagedList(tvSeriesDummy.results))

        verify(local).getFavoriteTv()
        assertNotNull(tvEntity)
        assertEquals(tvSeriesDummy.results.size, tvEntity.data?.size)
    }

    @Test
    fun findMovie(){
        val detailMovie = MutableLiveData<MovieEntity>()
        detailMovie.value = DataDummy.detail()
        `when`(local.findMovie(detailId)).thenReturn(detailMovie)

        val movieEntity = LiveDataTestUtil.getValue(reposotory.findMovie(detailId))
        verify(local).findMovie(detailId)
        assertNotNull(movieEntity)
        assertEquals(detailDummy.original_title, movieEntity.original_title)
    }

    @Test
    fun findSerialTv(){
        val detailTv = MutableLiveData<SerialTvEntity>()
        detailTv.value = DataDummy.detailTvSeries()
        `when`(local.findTv(detailTvSeriesId)).thenReturn(detailTv)

        val TvEntity = LiveDataTestUtil.getValue(reposotory.findSerialTv(detailTvSeriesId))
        verify(local).findTv(detailTvSeriesId)
        assertNotNull(TvEntity)
        assertEquals(detailtvSeriesDummy.name, TvEntity.name)
    }

    @Test
    fun setFavoriteMovie(){
        val state = !detailDummy.favorite

        `when`(appExecutors.diskIO()).thenReturn(Executors.newSingleThreadExecutor())
        local.updateMovie(detailDummy, state)

        reposotory.setFavoriteMovie(detailDummy, state)
        verify(local, times(1)).updateMovie(detailDummy, state)
        verifyNoMoreInteractions(local)
    }

    @Test
    fun setFavoriteTv(){
        val state = !detailtvSeriesDummy.favorite

        `when`(appExecutors.diskIO()).thenReturn(Executors.newSingleThreadExecutor())
        local.updateTv(detailtvSeriesDummy, state)

        reposotory.setFavoriteTv(detailtvSeriesDummy, state)
        verify(local, times(1)).updateTv(detailtvSeriesDummy, state)
        verifyNoMoreInteractions(local)
    }
}