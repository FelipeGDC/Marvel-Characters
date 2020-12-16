package com.fgdc.marvelcharacters.utils.extensions

import androidx.recyclerview.widget.RecyclerView
import com.fgdc.marvelcharacters.utils.helpers.EndlessScroll

fun RecyclerView.endless(visibleThreshold: Int = 10, loadMore: () -> Unit) {
    this.addOnScrollListener(EndlessScroll(this, visibleThreshold, loadMore))
}