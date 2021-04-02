package tam.pa.moviecatalogue.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity
import tam.pa.moviecatalogue.data.local.room.CatalogueDao

class LocalDataSource private constructor(private val catalogueDao: CatalogueDao){
    companion object{
        private var INSTANCE: LocalDataSource? = null
        fun getInstance(dao: CatalogueDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(dao)
    }

    fun getAllMovie(): DataSource.Factory<Int, MovieEntity> = catalogueDao.getMovie()

    fun getFavoriteMovie(): DataSource.Factory<Int, MovieEntity> = catalogueDao.getMovieFavorite()

    fun insertMovie(movie: List<MovieEntity>) = catalogueDao.insertMovie( movie )

    fun updateMovie(movie: MovieEntity, newState: Boolean) {
        movie.favorite = newState
        catalogueDao.updateMovie( movie )
    }

    fun getAllTv(): DataSource.Factory<Int, SerialTvEntity> = catalogueDao.getSerialTv()

    fun getFavoriteTv(): DataSource.Factory<Int, SerialTvEntity> = catalogueDao.getTvFavorite()

    fun insertTv(serialTv: List<SerialTvEntity>) = catalogueDao.insertTv( serialTv )

    fun updateTv(tv: SerialTvEntity, newState: Boolean) {
        tv.favorite = newState
        catalogueDao.updatetv( tv )
    }

    fun findMovie(id: Int): LiveData<MovieEntity> = catalogueDao.findMovie( id )

    fun findTv(id: Int): LiveData<SerialTvEntity> = catalogueDao.findTv( id )

    fun getMovieForTesting(): List<MovieEntity> = catalogueDao.getMovieForTesting()

    fun getTvForTesting(): List<SerialTvEntity> = catalogueDao.getTvForTesting()

}