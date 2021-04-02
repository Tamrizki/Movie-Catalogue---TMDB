package tam.pa.moviecatalogue.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import tam.pa.moviecatalogue.R
import tam.pa.moviecatalogue.data.local.entity.DetailEntity
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity
import tam.pa.moviecatalogue.databinding.ActivityDetailBinding
import tam.pa.moviecatalogue.network.ApiConfig
import tam.pa.moviecatalogue.ui.detail.adapter.DetailAdapter
import tam.pa.moviecatalogue.ui.home.HomeActivity
import tam.pa.moviecatalogue.utils.DataDummy
import tam.pa.moviecatalogue.utils.ViewModelFactory
import tam.pa.moviecatalogue.vo.StatusMessage

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private lateinit var popularAdapter: DetailAdapter
    private var movie: MovieEntity? = null
    private var tv: SerialTvEntity? = null
    private var isMovie = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val intent = intent.extras
        setupProgressBar(true)
        if (intent != null){
            setupObserve(
                    intent.getInt( DataDummy.ID).toString(),
                    intent.getString(DataDummy.TYPE).toString())
            setupListener()
        }
    }

    private fun setupListener() {
        binding.btnFavorite.setOnCheckedChangeListener { btn, isChecked ->
            if (isMovie){
                viewModel.setFavoriteMovie( movie!! )
            }else{
                viewModel.setFavoriteTv( tv!! )
            }
        }
    }

    private fun setupObserve(id : String, type: String) {
        when(type){
            DataDummy.MOVIE ->{
                isMovie = true
                viewModel.findMovie(id.toInt()).observe(this, { movie ->
                    this.movie = movie
                    setFavoriteIcon(movie.favorite)
                    setupView( DetailEntity(movie.original_title, movie.vote_average.toString(), movie.overview, movie.backdrop_path, movie.release_date, movie.favorite) )
                })
            }
            DataDummy.SERIAL_TV ->{
                isMovie = false
                viewModel.findTv( id.toInt() ).observe(this, { detail ->
                    this.tv = detail
                    setFavoriteIcon(detail.favorite)
                    setupView( DetailEntity(detail.name, detail.vote_average.toString(), detail.overview, detail.backdrop_path, detail.first_air_date, detail.favorite) )
                })
            }
        }
        viewModel.getMovie().observe(this, {
            when(it.status){
                StatusMessage.SUCCESS->{
                    setupRecyclerview(it.data!!)
                }
                StatusMessage.ERROR-> {
                    binding.rvPopular.visibility = View.GONE
                    binding.textPopular.visibility = View.GONE
                }
            }
        })
    }

    private fun setupRecyclerview(list: PagedList<MovieEntity>) {

        popularAdapter = DetailAdapter(object : DetailAdapter.onAdapterListener{
            override fun OnClick(data: MovieEntity) {
                startActivity(
                    Intent(this@DetailActivity, DetailActivity::class.java)
                    .putExtra(DataDummy.ID, data.id).putExtra(DataDummy.TYPE, DataDummy.MOVIE)
                )
            }
        })
        popularAdapter.submitList(list)
        with(binding.rvPopular){
            setHasFixedSize( true )
            adapter = popularAdapter
            layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupView(detail: DetailEntity){
        setupProgressBar(false)
        supportActionBar?.title = detail.title
        with(binding){
            Glide.with(this@DetailActivity).load(ApiConfig.BASE_URL_IMAGE+detail.backdropPath)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_waiting).error(R.drawable.ic_error))
                .into(imgPoster)
            tvTitle.text = detail.title
            tvDate.text = getString(R.string.release_at)+detail.releaseDate
            tvDesc.text = detail.overview
            tvRate.text = detail.voteAverage
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, HomeActivity::class.java))
        finishAffinity()
    }

    private fun setupProgressBar(status: Boolean){
        with(binding.progressBar){
            if (status) visibility = android.view.View.VISIBLE
            else visibility = android.view.View.GONE
        }
    }
    private fun setFavoriteIcon(state: Boolean){
        if (state){
            binding.btnFavorite.setBackgroundResource(R.drawable.ic_favorite_on)
        }else{
            binding.btnFavorite.setBackgroundResource(R.drawable.ic_favorite_off)
        }
    }

}