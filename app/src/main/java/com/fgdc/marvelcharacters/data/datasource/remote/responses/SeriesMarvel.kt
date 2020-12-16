package com.fgdc.marvelcharacters.data.datasource.remote.responses

import com.fgdc.marvelcharacters.domain.model.SeriesListDomain
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SeriesMarvel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "urls")
    val urls: List<UrlSeries>,
    @Json(name = "thumbnail")
    val thumbnail: ThumbnailSeries,
) {
    fun toSeriesListDomain() = SeriesListDomain(
        title,
        thumbnail.path + "." + thumbnail.extension,
        urls[0].url
    )
}

@JsonClass(generateAdapter = true)
data class UrlSeries(
    @Json(name = "type")
    val type: String,
    @Json(name = "url")
    val url: String
)

@JsonClass(generateAdapter = true)
data class ThumbnailSeries(
    @Json(name = "extension")
    val extension: String,
    @Json(name = "path")
    val path: String
)
