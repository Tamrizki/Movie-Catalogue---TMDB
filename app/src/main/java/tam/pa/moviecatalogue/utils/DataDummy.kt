package tam.pa.moviecatalogue.utils

import tam.pa.moviecatalogue.data.local.entity.DetailEntity
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity
import tam.pa.moviecatalogue.data.remote.response.*

object DataDummy {
    const val ID = "idMovie"
    const val TYPE = "Type"
    const val MOVIE = "Movie"
    const val SERIAL_TV = "SerialTV"

    fun movie(): MovieResponse{
        var listMovie: ArrayList<ResultsItem> = ArrayList()
        with(listMovie){
            add(ResultsItem(
                    "Long ago, in the fantasy world of Kumandra, humans and dragons lived together in harmony. But when an evil force threatened the land, the dragons sacrificed themselves to save humanity. Now, 500 years later, that same evil has returned and it’s up to a lone warrior, Raya, to track down the legendary last dragon to restore the fractured land and its divided people.",
                    "en",
                    "Raya and the Last Dragon",
                    "Raya and the Last Dragon",
                    "/rcUcYzGGicDvhDs58uM44tJKB9F.jpg",
                    "/lPsD10PP4rgUGiGR4CCXA6iY0QQ.jpg",
                    4792.847,
                    527774
            ))
            add(ResultsItem(
                    "Prince Akeem Joffer is set to become King of Zamunda when he discovers he has a son he never knew about in America – a street savvy Queens native named Lavelle. Honoring his royal father's dying wish to groom this son as the crown prince, Akeem and Semmi set off to America once again.",
                    "Coming 2 America",
                    "Coming 2 America",
                    "/vKzbIoHhk1z9DWYi8kyFe9Gg0HF.jpg",
                    "/nWBPLkqNApY5pgrJFMiI9joSI30.jpg",
                    "2021-03-05",
                    2264.515,
                    484718
            ))
        }
        return MovieResponse(
                1,
                listMovie
        )
    }
    fun serialTv(): TvResponse{
        val results: ArrayList<TVResultsItem> = ArrayList()
        with(results){
            add(TVResultsItem(
                    "2014-10-07",
                    "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
                    "/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg",
                    "/jC1KqsFx8ZyqJyQa2Ohi7xgL7XC.jpg",
                    7.7,
                    "The Flash",
                    60735
            ))
            add(TVResultsItem(
                    "2017-01-26",
                    "Set in the present, the series offers a bold, subversive take on Archie, Betty, Veronica and their friends, exploring the surreality of small-town life, the darkness and weirdness bubbling beneath Riverdale’s wholesome facade.",
                    "/colNDt09PACkwtCgWILJQ8i6vSR.jpg",
                    "/wRbjVBdDo5qHAEOVYoMWpM58FSA.jpg",
                    1627.704,
                    "Riverdale",
                    0))
        }
        return TvResponse(
                1,
                results
        )
    }
    fun detail(): MovieEntity{
        return MovieEntity(
                527774,
                "Raya and the Last Dragon",
                "Long ago, in the fantasy world of Kumandra, humans and dragons lived together in harmony. But when an evil force threatened the land, the dragons sacrificed themselves to save humanity. Now, 500 years later, that same evil has returned and it’s up to a lone warrior, Raya, to track down the legendary last dragon to restore the fractured land and its divided people.",
                "/hJuDvwzS0SPlsE6MNFOpznQltDZ.jpg",
                "2021-03-03",
                8.4,
                "/hJuDvwzS0SPlsE6MNFOpznQltDZ.jpg",
                false
        )
    }
    fun detailTvSeries(): SerialTvEntity{
        return SerialTvEntity(
                85271,
                "2021-01-15",
                "WandaVision",
                "Wanda Maximoff and Vision—two super-powered beings living idealized suburban lives—begin to suspect that everything is not as it seems.",
                "/gJ8VX6JSu3ciXHuC2dDGAo2lvwM.png",
                8.5,
                "/gJ8VX6JSu3ciXHuC2dDGAo2lvwM.png"
        )
    }
}