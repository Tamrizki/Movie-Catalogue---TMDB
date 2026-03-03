package tam.pa.moviecatalogue.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tam.pa.moviecatalogue.data.remote.response.*
import tam.pa.moviecatalogue.network.ApiService
import tam.pa.moviecatalogue.utils.EspressoIdlingResource
import tam.pa.moviecatalogue.vo.Resource

class RemoteDataSource (private val apiService: ApiService){
    companion object{
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(api: ApiService): RemoteDataSource =
            instance ?: synchronized(this){
                instance ?: RemoteDataSource(api)
            }

    }

    fun getMovie(): LiveData<Resource<List<ResultsItem>>> {
        val listMovie = MutableLiveData<Resource<List<ResultsItem>>>()
        EspressoIdlingResource.increment()
        val response = apiService.getMovie()
        response.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    listMovie.value = Resource.success( response.body()!!.results )
                }else{
                    listMovie.value = Resource.error( response.message(), null )
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
                listMovie.value = Resource.error(t.message, null)
            }
        })
        return listMovie
    }

    fun getTvSeries(): LiveData<Resource<List<TVResultsItem>>>{
        val listTv = MutableLiveData<Resource<List<TVResultsItem>>>()
        EspressoIdlingResource.increment()
        val response = apiService.getSerialTv()
        response.enqueue(object : Callback<TvResponse> {
            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                if (response.isSuccessful) {
                    listTv.value = Resource.success( response.body()!!.results )
                }  else {
                    listTv.value = Resource.error( response.message(), null )
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
                listTv.value = Resource.error( t.message, null )
            }
        })
        return listTv
    }
    interface LoadDetailCallback{
        fun onDetail( detailResponse: DetailResponse )
    }
    interface LoadDetailTvCallback{
        fun onDetailTV( detailResponse: DetailTvResponse )
    }
}