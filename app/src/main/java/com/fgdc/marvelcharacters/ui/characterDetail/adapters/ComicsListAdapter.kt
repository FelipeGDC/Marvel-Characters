package com.fgdc.marvelcharacters.ui.characterDetail.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fgdc.marvelcharacters.R
import com.fgdc.marvelcharacters.databinding.ItemComicBinding
import com.fgdc.marvelcharacters.ui.characterDetail.models.ComicListView
import com.fgdc.marvelcharacters.utils.extensions.simpleLoad


class ComicsListAdapter :
    ListAdapter<ComicListView, ComicsListAdapter.ComicsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {
        val binding = ItemComicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComicsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ComicsListAdapter.ComicsViewHolder, position: Int) {
        val comicItem = getItem(position)
        if (comicItem != null) {
            holder.bind(comicItem)
        }
    }


    inner class ComicsViewHolder(private val itemBinding: ItemComicBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(comic: ComicListView) {
            itemBinding.comicCover.simpleLoad(comic.image, itemBinding.root.context)
            itemBinding.comicTitle.text = comic.title

            if (comic.price != 0.0) {
                itemBinding.comicPrice.visibility = View.VISIBLE
                itemBinding.comicPrice.text =
                    itemBinding.root.context.getString(R.string.comic_price, comic.price)
            } else {
                itemBinding.comicPrice.visibility = View.GONE
            }

            itemBinding.root.setOnClickListener {
                itemBinding.root.findNavController()
                val defaultBrowser =
                    Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
                defaultBrowser.data = Uri.parse(comic.url)
                startActivity(itemBinding.root.context, defaultBrowser, null)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<ComicListView>() {

            override fun areItemsTheSame(
                oldComic: ComicListView,
                newComic: ComicListView
            ) = oldComic.url == newComic.url

            override fun areContentsTheSame(
                oldComic: ComicListView,
                newComic: ComicListView
            ) = oldComic == newComic
        }
    }
}
