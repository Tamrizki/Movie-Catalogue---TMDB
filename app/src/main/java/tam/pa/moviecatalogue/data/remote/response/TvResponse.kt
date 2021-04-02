package tam.pa.moviecatalogue.data.remote.response

import com.google.gson.annotations.SerializedName

data class TvResponse(

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("results")
	val results: List<TVResultsItem>,
)

data class TVResultsItem(

	@field:SerializedName("first_air_date")
	val firstAirDate: String,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("backdrop_path")
	val backdropPath: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,
)
