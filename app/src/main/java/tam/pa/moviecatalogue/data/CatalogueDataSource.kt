package tam.pa.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity
import tam.pa.moviecatalogue.vo.Resource

interface CatalogueDataSource {

    fun dataMovie(): LiveData<Resource<PagedList<MovieEntity>>>

    fun dataSerialTv(): LiveData<Resource<PagedList<SerialTvEntity>>>

    fun setFavoriteMovie(movie: MovieEntity, state: Boolean)

    fun setFavoriteTv(tv: SerialTvEntity, state: Boolean)

    fun findMovie(id: Int): LiveData<MovieEntity>

    fun findSerialTv(id: Int): LiveData<SerialTvEntity>

    fun favoriteMovie(): LiveData<PagedList<MovieEntity>>

    fun favoriteSerialTv(): LiveData<PagedList<SerialTvEntity>>
}