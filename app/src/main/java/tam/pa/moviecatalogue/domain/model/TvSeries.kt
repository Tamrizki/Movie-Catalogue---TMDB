package tam.pa.moviecatalogue.domain.model

data class TvSeries(
    val id: Int,
    val firstAirDate: String?,
    val name: String?,
    val overview: String?,
    val posterPath: String?,
    val voteAverage: Double?,
    val backdropPath: String?,
    val isFavorite: Boolean
)
