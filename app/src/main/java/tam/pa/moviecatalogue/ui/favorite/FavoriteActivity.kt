package tam.pa.moviecatalogue.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import tam.pa.moviecatalogue.R
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity
import tam.pa.moviecatalogue.databinding.ActivityFavoriteBinding
import tam.pa.moviecatalogue.ui.detail.DetailActivity
import tam.pa.moviecatalogue.ui.favorite.adapter.FavMovieAdapter
import tam.pa.moviecatalogue.ui.favorite.adapter.FavTvAdapter
import tam.pa.moviecatalogue.utils.DataDummy
import tam.pa.moviecatalogue.utils.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private val binding by lazy { ActivityFavoriteBinding.inflate(layoutInflater) }
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var movieAdapter: FavMovieAdapter
    private lateinit var tvAdapter: FavTvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( binding.root )
        supportActionBar?.title = getString(R.string.text_favorite)
        itemTouchHelperMovie.attachToRecyclerView(binding.rvMovieFavorite)
        itemTouchHelperTv.attachToRecyclerView(binding.rvTvFavorite)
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
        binding.menuFavorite.selectButton(R.id.btn_movie)
        setupListener()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.getFavoriteMovie().observe(this, {
            setupRecyclerviewMovie( it )
        })
        viewModel.getFavoriteTv().observe(this, {
            setupRecyclerviewTv( it )
        })
    }

    private fun setupRecyclerviewTv(tv: PagedList<SerialTvEntity>?) {
        tvAdapter = FavTvAdapter(object : FavTvAdapter.OnAdapterListener{
            override fun OnClick(serialTv: SerialTvEntity) {
                startActivity(Intent(this@FavoriteActivity, DetailActivity::class.java)
                        .putExtra(DataDummy.ID, serialTv.id).putExtra(DataDummy.TYPE, DataDummy.SERIAL_TV))
            }
        })
        tvAdapter.submitList( tv )
        with(binding.rvTvFavorite){
            setHasFixedSize(true)
            adapter = tvAdapter
        }
    }

    private fun setupRecyclerviewMovie(listMovie: PagedList<MovieEntity>?) {
        movieAdapter = FavMovieAdapter(object : FavMovieAdapter.OnAdapterListener{
            override fun OnClick(movie: MovieEntity) {
                startActivity(Intent(this@FavoriteActivity, DetailActivity::class.java)
                        .putExtra(DataDummy.ID, movie.id).putExtra(DataDummy.TYPE, DataDummy.MOVIE)
                )
            }
        })
        movieAdapter.submitList( listMovie )
        with(binding.rvMovieFavorite){
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun setupListener() {
        binding.menuFavorite.setOnSelectListener {
            if (binding.btnMovie.isSelected){
                binding.rvMovieFavorite.visibility = View.VISIBLE
                binding.rvTvFavorite.visibility = View.GONE
            }else {
                binding.rvMovieFavorite.visibility = View.GONE
                binding.rvTvFavorite.visibility = View.VISIBLE
            }
        }
    }

    private val itemTouchHelperMovie = ItemTouchHelper(object : ItemTouchHelper.Callback(){

        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int = makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val swipedPosition = viewHolder.adapterPosition
            val entity = movieAdapter.getSwipeData(swipedPosition)
            entity?.let {
                viewModel.setFavoriteMovie( it )
            }
            val layout = findViewById<View>(R.id.rv_movie_favorite)
            val snackbar = Snackbar.make(layout, getString(R.string.text_undo), Snackbar.LENGTH_LONG)
            snackbar.setAction(getString(R.string.text_ok)) {
                entity?.let { viewModel.setFavoriteMovie(it) }
            }
            snackbar.show()
        }

    })

    private val itemTouchHelperTv = ItemTouchHelper(object : ItemTouchHelper.Callback(){

        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int = makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val swipedPosition = viewHolder.adapterPosition
            val entity = tvAdapter.getSwipeData(swipedPosition)
            entity?.let {
                viewModel.setFavoriteTv( it )
            }
            val layout = findViewById<View>(R.id.rv_movie_favorite)
            val snackbar = Snackbar.make(layout, getString(R.string.text_undo), Snackbar.LENGTH_LONG)
            snackbar.setAction(getString(R.string.text_ok)) {
                entity?.let { viewModel.setFavoriteTv(it) }
            }
            snackbar.show()
        }

    })
}