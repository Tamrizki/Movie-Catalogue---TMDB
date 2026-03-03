package tam.pa.moviecatalogue.domain.model

data class Movie(
    val id: Int,
    val title: String?,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val backdropPath: String?,
    val isFavorite: Boolean
)
