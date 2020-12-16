package com.fgdc.marvelcharacters.domain.model

import com.fgdc.marvelcharacters.ui.characterDetail.models.ComicListView

data class ComicListDomain(
    val title: String,
    val image: String,
    val url: String,
    val price: Double
) {
    fun toComicListView() = ComicListView(title, image, price, url)
}
