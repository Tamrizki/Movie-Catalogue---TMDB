package tam.pa.moviecatalogue.ui.splashscreen

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.GeneralLocation
import androidx.test.espresso.action.GeneralSwipeAction
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Swipe
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import tam.pa.moviecatalogue.R
import tam.pa.moviecatalogue.data.local.LocalDataSource
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity
import tam.pa.moviecatalogue.data.local.room.CatalogueDatabase
import tam.pa.moviecatalogue.ui.home.HomeActivity
import tam.pa.moviecatalogue.utils.EspressoIdlingResource

@RunWith(AndroidJUnit4ClassRunner::class)
class  InstrumentalTesting{
    private lateinit var local: LocalDataSource
    private lateinit var listMovie: List<MovieEntity>
    private lateinit var listTv: List<SerialTvEntity>

    private lateinit var instumentalContext: Context


    @Before
    fun setUp(){
        instumentalContext = InstrumentationRegistry.getInstrumentation().targetContext
        ActivityScenario.launch(HomeActivity::class.java)

        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())

        local = LocalDataSource.getInstance(CatalogueDatabase.getInstance(instumentalContext).catalogueDao())

        listMovie = local.getMovieForTesting()
        listTv = local.getTvForTesting()
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadMovie(){
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(scrollToPosition<RecyclerView.ViewHolder>(9))
    }

    @Test
    fun loadDetailMovie(){
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.ll_detail_movie)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(ViewMatchers.withText(listMovie[0].original_title)))
        onView(withId(R.id.tv_rate)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rate)).check(matches(ViewMatchers.withText(listMovie[0].vote_average.toString())))
        onView(withId(R.id.tv_desc)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_desc)).check(matches(ViewMatchers.withText(listMovie[0].overview)))
        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))

        onView(withId(R.id.btn_favorite)).perform(click())

        onView(withId(R.id.rv_popular)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_popular)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(9))
        onView(withId(R.id.rv_popular)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        onView(withId(R.id.ll_detail_movie)).check(matches(isDisplayed()))
    }

    @Test
    fun loadSerialTv(){
        onView(ViewMatchers.withText("Serial TV")).perform(click())
        onView(withId(R.id.rv_serial_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_serial_tv)).perform(scrollToPosition<RecyclerView.ViewHolder>(19))
    }

    @Test
    fun loadDatailSerialTV(){
        onView(ViewMatchers.withText("Serial TV")).perform(click())
        onView(withId(R.id.rv_serial_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_serial_tv)).perform(scrollToPosition<RecyclerView.ViewHolder>(19))

        onView(withId(R.id.rv_serial_tv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.ll_detail_movie)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(ViewMatchers.withText(listTv[0].name)))
        onView(withId(R.id.tv_rate)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rate)).check(matches(ViewMatchers.withText(listTv[0].vote_average.toString())))
        onView(withId(R.id.tv_desc)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_desc)).check(matches(ViewMatchers.withText(listTv[0].overview)))
        onView(withId(R.id.tv_date)).check(matches(isDisplayed()))
        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))

        onView(withId(R.id.btn_favorite)).perform(click())
    }

    @Test
    fun loadfavorite(){
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.action_favorite)).perform(click())
        onView(withId(R.id.rv_movie_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie_favorite)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, GeneralSwipeAction(
                Swipe.SLOW,
                GeneralLocation.CENTER_RIGHT, GeneralLocation.CENTER_LEFT, Press.FINGER
        )))
        onView(ViewMatchers.withText("Ok")).perform(click())
        onView(withId(R.id.btn_serial_tv)).perform(click())
        onView(withId(R.id.rv_tv_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_favorite)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, GeneralSwipeAction(
                Swipe.SLOW,
                GeneralLocation.CENTER_RIGHT, GeneralLocation.CENTER_LEFT, Press.FINGER
        )))
        onView(ViewMatchers.withText("Ok")).perform(click())
    }
}