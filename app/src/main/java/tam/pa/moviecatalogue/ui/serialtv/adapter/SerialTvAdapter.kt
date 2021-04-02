package tam.pa.moviecatalogue.ui.serialtv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import tam.pa.moviecatalogue.R
import tam.pa.moviecatalogue.data.local.entity.MovieEntity
import tam.pa.moviecatalogue.data.local.entity.SerialTvEntity
import tam.pa.moviecatalogue.data.remote.response.TVResultsItem
import tam.pa.moviecatalogue.databinding.ItemsSerialTvBinding
import tam.pa.moviecatalogue.network.ApiConfig

class SerialTvAdapter(
    val listener: OnAdapterListener): PagedListAdapter<SerialTvEntity, SerialTvAdapter.vHolder>(DIFF_CALLBACK) {

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SerialTvEntity>(){
            override fun areItemsTheSame(
                oldItem: SerialTvEntity,
                newItem: SerialTvEntity
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: SerialTvEntity,
                newItem: SerialTvEntity
            ): Boolean = oldItem == newItem

        }
    }

    inner class vHolder(val binding: ItemsSerialTvBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(serialTv: SerialTvEntity) {
            with(binding){
                tvTitle.text = serialTv.name
                tvDate.text = itemView.context.getString(R.string.first_date)+serialTv.first_air_date
                tvRate.text = serialTv.vote_average.toString()
                Glide.with(itemView.context)
                        .load(ApiConfig.BASE_URL_IMAGE+serialTv.poster_path)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_waiting)
                                .error(R.drawable.ic_error))
                        .into(imgPoster)

                btnShare.setOnClickListener {
                    listener.OnShare( serialTv )
                }
                container.setOnClickListener {
                    listener.OnClick( serialTv )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = vHolder(
            ItemsSerialTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SerialTvAdapter.vHolder, position: Int) {
        val serialTv = getItem(position)
        if (serialTv != null){
            holder.bind(serialTv)
        }
    }

    interface OnAdapterListener{
        fun OnClick( serialTv: SerialTvEntity )
        fun OnShare( serialTv: SerialTvEntity )
    }
}