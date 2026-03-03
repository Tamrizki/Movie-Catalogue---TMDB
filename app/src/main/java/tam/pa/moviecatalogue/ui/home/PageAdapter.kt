package tam.pa.moviecatalogue.ui.home

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import tam.pa.moviecatalogue.R
import tam.pa.moviecatalogue.ui.movie.MovieFragment
import tam.pa.moviecatalogue.ui.serialtv.SerialTvFragment

class PageAdapter(
    private val context: Context,
    fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object{
        @StringRes
        private val TITLE = intArrayOf(R.string.movie, R.string.serial_tv)
    }

    override fun getCount() = 2

    override fun getItem(position: Int): Fragment = when(position){
        0 -> MovieFragment()
        1 -> SerialTvFragment()
        else -> Fragment()
    }

    override fun getPageTitle(position: Int): CharSequence? = context.resources.getString(TITLE[position])

}