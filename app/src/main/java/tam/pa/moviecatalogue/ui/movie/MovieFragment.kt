package tam.pa.moviecatalogue.ui.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.databinding.FragmentMovieBinding
import tam.pa.moviecatalogue.ui.detail.DetailActivity
import tam.pa.moviecatalogue.ui.home.HomeViewModel
import tam.pa.moviecatalogue.ui.movie.adapter.MovieAdapter
import tam.pa.moviecatalogue.utils.DataDummy
import tam.pa.moviecatalogue.vo.StatusMessage

class MovieFragment : Fragment() {
    private lateinit var binding: FragmentMovieBinding
    private val viewModel by lazy { ViewModelProvider(requireActivity())[HomeViewModel::class.java] }
    private lateinit var movieAdapter: MovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null){
            setupRecyclerview()
            viewModel.getMovie().observe(viewLifecycleOwner, { result ->
                if (result!=null){
                    when(result.status){
                        StatusMessage.LOADING -> setupProgressBar(true)
                        StatusMessage.SUCCESS ->{
                            setupProgressBar(false)
                            movieAdapter.submitList( result.data )
                        }
                        StatusMessage.ERROR -> {
                            setupProgressBar(false)
                        }
                    }
                }
            })
        }
    }

    private fun setupRecyclerview() {
        movieAdapter = MovieAdapter(object : MovieAdapter.OnAdapterListener{
            override fun OnClick(movie: MovieEntity) {
                startActivity(Intent(requireContext(), DetailActivity::class.java)
                        .putExtra(DataDummy.ID, movie.id).putExtra(DataDummy.TYPE, DataDummy.MOVIE)
                )
            }
        })
        with(binding.rvMovie){
            setHasFixedSize(true)
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        binding.progressBar.visibility = View.GONE
    }

    private fun setupProgressBar(status: Boolean){
        with(binding.progressBar){
            if (status) visibility = View.VISIBLE
            else visibility = View.GONE
        }
    }
}