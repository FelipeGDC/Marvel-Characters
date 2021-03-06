package com.fgdc.marvelcharacters.ui.characterDetail.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fgdc.marvelcharacters.R
import com.fgdc.marvelcharacters.databinding.ItemSeriesBinding
import com.fgdc.marvelcharacters.ui.characterDetail.models.SeriesListView
import com.fgdc.marvelcharacters.utils.extensions.simpleLoad

class SeriesListAdapter :
    ListAdapter<SeriesListView, SeriesListAdapter.SeriesViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val binding = ItemSeriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeriesListAdapter.SeriesViewHolder, position: Int) {
        val seriesItem = getItem(position)
        if (seriesItem != null) {
            holder.bind(seriesItem)
        }
    }

    inner class SeriesViewHolder(private val itemBinding: ItemSeriesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(series: SeriesListView) {
            itemBinding.apply {
                item.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.left_to_right)
                seriesCover.simpleLoad(series.image, root.context)
                seriesTitle.text = series.title
                root.setOnClickListener {
                    root.findNavController()
                    val defaultBrowser =
                        Intent.makeMainSelectorActivity(
                            Intent.ACTION_MAIN,
                            Intent.CATEGORY_APP_BROWSER
                        )
                    defaultBrowser.data = Uri.parse(series.url)
                    ContextCompat.startActivity(root.context, defaultBrowser, null)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<SeriesListView>() {

            override fun areItemsTheSame(
                oldSeries: SeriesListView,
                newSeries: SeriesListView
            ) = oldSeries.url == newSeries.url

            override fun areContentsTheSame(
                oldSeries: SeriesListView,
                newSeries: SeriesListView
            ) = oldSeries == newSeries
        }
    }
}
