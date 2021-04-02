package tam.pa.moviecatalogue.ui.serialtv

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity
import tam.pa.moviecatalogue.data.remote.response.TVResultsItem
import tam.pa.moviecatalogue.databinding.FragmentSerialTvBinding
import tam.pa.moviecatalogue.ui.detail.DetailActivity
import tam.pa.moviecatalogue.ui.home.HomeViewModel
import tam.pa.moviecatalogue.ui.serialtv.adapter.SerialTvAdapter
import tam.pa.moviecatalogue.utils.DataDummy
import tam.pa.moviecatalogue.vo.StatusMessage

class SerialTvFragment : Fragment() {
    private lateinit var binding: FragmentSerialTvBinding
    private val viewModel by lazy { ViewModelProvider(requireActivity())[HomeViewModel::class.java] }
    private lateinit var serialTvAdapter: SerialTvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSerialTvBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null){
            setupRecuclerview()
            viewModel.getSerialTv().observe(viewLifecycleOwner, { tv ->
                if (tv != null){
                    when(tv.status){
                        StatusMessage.LOADING -> setupProgressBar(true)
                        StatusMessage.SUCCESS -> {
                            setupProgressBar(false)
                            serialTvAdapter.submitList( tv.data )
                        }
                        StatusMessage.ERROR ->{
                            setupProgressBar(false)
                        }
                    }
                }
            })
        }
    }

    private fun setupRecuclerview() {
        serialTvAdapter = SerialTvAdapter(object : SerialTvAdapter.OnAdapterListener{
            override fun OnClick(serialTv: SerialTvEntity) {
                startActivity(Intent(requireContext(), DetailActivity::class.java)
                    .putExtra(DataDummy.ID, serialTv.id)
                    .putExtra(DataDummy.TYPE, DataDummy.SERIAL_TV))
            }
            override fun OnShare(serialTv: SerialTvEntity) {
                setupShareContent( serialTv )
            }
        })
        with(binding.rvSerialTv){
            setHasFixedSize(true)
            adapter = serialTvAdapter
        }
    }

    private fun setupShareContent(serialTv: SerialTvEntity) {
        val type = "text/plain"
        ShareCompat.IntentBuilder
            .from(requireActivity())
            .setType(type)
            .setChooserTitle("Bagikan ${serialTv.name} sekarang.")
            .setText( serialTv.overview )
            .startChooser()
    }

    private fun setupProgressBar(status: Boolean){
        with(binding.progressBar){
            if (status) visibility = View.VISIBLE
            else visibility = View.GONE
        }
    }
}