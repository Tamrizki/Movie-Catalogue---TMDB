package tam.pa.moviecatalogue.utils

import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity
import tam.pa.moviecatalogue.domain.model.Movie
import tam.pa.moviecatalogue.domain.model.TvSeries

object DataMapper {
    fun mapMovieEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.original_title,
                overview = it.overview,
                posterPath = it.poster_path,
                releaseDate = it.release_date,
                voteAverage = it.vote_average,
                backdropPath = it.backdrop_path,
                isFavorite = it.favorite
            )
        }

    fun mapMovieEntityToDomain(it: MovieEntity): Movie =
        Movie(
            id = it.id,
            title = it.original_title,
            overview = it.overview,
            posterPath = it.poster_path,
            releaseDate = it.release_date,
            voteAverage = it.vote_average,
            backdropPath = it.backdrop_path,
            isFavorite = it.favorite
        )

    fun mapMovieDomainToEntity(it: Movie): MovieEntity =
        MovieEntity(
            id = it.id,
            original_title = it.title,
            overview = it.overview,
            poster_path = it.posterPath,
            release_date = it.releaseDate,
            vote_average = it.voteAverage,
            backdrop_path = it.backdropPath,
            favorite = it.isFavorite
        )

    fun mapTvEntitiesToDomain(input: List<SerialTvEntity>): List<TvSeries> =
        input.map {
            TvSeries(
                id = it.id,
                firstAirDate = it.first_air_date,
                name = it.name,
                overview = it.overview,
                posterPath = it.poster_path,
                voteAverage = it.vote_average,
                backdropPath = it.backdrop_path,
                isFavorite = it.favorite
            )
        }

    fun mapTvEntityToDomain(it: SerialTvEntity): TvSeries =
        TvSeries(
            id = it.id,
            firstAirDate = it.first_air_date,
            name = it.name,
            overview = it.overview,
            posterPath = it.poster_path,
            voteAverage = it.vote_average,
            backdropPath = it.backdrop_path,
            isFavorite = it.favorite
        )

    fun mapTvDomainToEntity(it: TvSeries): SerialTvEntity =
        SerialTvEntity(
            id = it.id,
            first_air_date = it.firstAirDate,
            name = it.name,
            overview = it.overview,
            poster_path = it.posterPath,
            vote_average = it.voteAverage,
            backdrop_path = it.backdropPath,
            favorite = it.isFavorite
        )
}
