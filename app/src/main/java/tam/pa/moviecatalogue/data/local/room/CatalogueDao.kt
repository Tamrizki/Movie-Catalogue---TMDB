package tam.pa.moviecatalogue.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity

@Dao
interface CatalogueDao {

    @Query("SELECT * FROM movieentity")
    fun getMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movieentity where favorite = 1")
    fun getMovieFavorite(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movieentity where id IN (:mId)")
    fun findMovie(vararg mId: Int): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Query("SELECT * FROM serialtventity")
    fun getSerialTv(): DataSource.Factory<Int, SerialTvEntity>

    @Query("SELECT * FROM serialtventity where favorite = 1")
    fun getTvFavorite(): DataSource.Factory<Int, SerialTvEntity>

    @Query("SELECT * FROM serialtventity where id IN (:mId)")
    fun findTv(vararg mId: Int): LiveData<SerialTvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTv(serialTv: List<SerialTvEntity>)

    @Update
    fun updatetv(serialTv: SerialTvEntity)

    @Query("SELECT * FROM movieentity")
    fun getMovieForTesting(): List<MovieEntity>

    @Query("SELECT * FROM serialtventity")
    fun getTvForTesting(): List<SerialTvEntity>
}