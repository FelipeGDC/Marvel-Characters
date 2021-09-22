package com.fgdc.marvelcharacters.data.datasource.core

import com.fgdc.marvelcharacters.domain.model.ComicListDomain
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComicMarvel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "urls")
    val urls: List<UrlComic>,
    @Json(name = "prices")
    val prices: List<Price>,
    @Json(name = "thumbnail")
    val thumbnail: ThumbnailComic,
) {
    fun toComicListDomain() = ComicListDomain(
        title,
        thumbnail.path + "." + thumbnail.extension,
        urls[0].url,
        prices[0].price
    )
}

@JsonClass(generateAdapter = true)
data class UrlComic(
    @Json(name = "type")
    val type: String,
    @Json(name = "url")
    val url: String
)

@JsonClass(generateAdapter = true)
data class Price(
    @Json(name = "price")
    val price: Double,
    @Json(name = "type")
    val type: String
)

@JsonClass(generateAdapter = true)
data class ThumbnailComic(
    @Json(name = "extension")
    val extension: String,
    @Json(name = "path")
    val path: String
)
