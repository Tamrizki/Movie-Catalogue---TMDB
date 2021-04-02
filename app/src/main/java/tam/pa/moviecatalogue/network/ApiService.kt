package tam.pa.moviecatalogue.network

import retrofit2.Call
import retrofit2.http.GET
import tam.pa.moviecatalogue.data.remote.response.MovieResponse
import tam.pa.moviecatalogue.data.remote.response.TvResponse

interface ApiService {

    @GET("movie/popular?api_key=YOUR_API_KEY&page=1")
    fun getMovie(): Call<MovieResponse>

    @GET("tv/popular?api_key=YOUR_API_KEY&page=1")
    fun getSerialTv(): Call<TvResponse>
}