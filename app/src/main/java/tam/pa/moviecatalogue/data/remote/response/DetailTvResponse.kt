package tam.pa.moviecatalogue.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailTvResponse(

	@field:SerializedName("backdrop_path")
	val backdropPath: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("first_air_date")
	val firstAirDate: String,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("name")
	val name: String,
)