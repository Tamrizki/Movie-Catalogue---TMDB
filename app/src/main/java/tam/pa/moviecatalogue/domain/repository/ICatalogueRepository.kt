package tam.pa.moviecatalogue.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import tam.pa.moviecatalogue.domain.model.Movie
import tam.pa.moviecatalogue.domain.model.TvSeries
import tam.pa.moviecatalogue.vo.Resource

interface ICatalogueRepository {
    fun getAllMovies(): LiveData<Resource<PagedList<Movie>>>
    fun getAllTvSeries(): LiveData<Resource<PagedList<TvSeries>>>
    fun getFavoriteMovies(): LiveData<PagedList<Movie>>
    fun getFavoriteTvSeries(): LiveData<PagedList<TvSeries>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
    fun setFavoriteTvSeries(tvSeries: TvSeries, state: Boolean)
    fun getMovieDetail(id: Int): LiveData<Movie>
    fun getTvSeriesDetail(id: Int): LiveData<TvSeries>
}
