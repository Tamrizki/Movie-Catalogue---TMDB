package tam.pa.moviecatalogue.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import tam.pa.moviecatalogue.R
import tam.pa.moviecatalogue.databinding.ActivityHomeBinding
import tam.pa.moviecatalogue.ui.favorite.FavoriteActivity
import tam.pa.moviecatalogue.utils.ViewModelFactory

class HomeActivity : AppCompatActivity(){

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val pageAdapter by lazy { PageAdapter(this, supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListener()
        setupView()
    }

    private fun setupListener() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            setupView()
        }
    }

    private fun setupView(){
        binding.swipeRefresh.isRefreshing = false
        binding.imgNoInternet.visibility = View.GONE
        with(binding){
            viewPager.adapter = pageAdapter
            tabLayout.setupWithViewPager( viewPager )
        }
        supportActionBar?.elevation = 0f
        val factory = ViewModelFactory.getInstance(this)
        ViewModelProvider(this, factory)[HomeViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite){
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}