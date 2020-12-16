package com.fgdc.marvelcharacters.domain.model

import com.fgdc.marvelcharacters.ui.characterDetail.models.SeriesListView

data class SeriesListDomain(
    val title: String,
    val image: String,
    val url: String,
) {
    fun toSeriesListView() = SeriesListView(title, image, url)
}