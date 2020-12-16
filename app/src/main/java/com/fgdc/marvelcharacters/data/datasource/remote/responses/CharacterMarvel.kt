package com.fgdc.marvelcharacters.data.datasource.remote.responses


import com.fgdc.marvelcharacters.domain.model.CharacterDetailDomain
import com.fgdc.marvelcharacters.domain.model.CharacterListDomain
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterMarvel(
    @Json(name = "comics")
    val comics: Comics,
    @Json(name = "description")
    val description: String,
    @Json(name = "events")
    val events: Events,
    @Json(name = "id")
    val id: Int,
    @Json(name = "modified")
    val modified: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "resourceURI")
    val resourceURI: String,
    @Json(name = "series")
    val series: Series,
    @Json(name = "stories")
    val stories: Stories,
    @Json(name = "thumbnail")
    val thumbnail: Thumbnail,
    @Json(name = "urls")
    val urls: List<Url>
) {
    fun toCharacterListDomain() =
        CharacterListDomain(id, name, (thumbnail.path + "." + thumbnail.extension))

    fun toCharacterDetailDomain() =
        CharacterDetailDomain(
            name,
            (thumbnail.path + "." + thumbnail.extension),
            description,
            comics.items.map {
                if (it.resourceURI.isNotEmpty()) {
                    it.resourceURI.substring(it.resourceURI.lastIndexOf('/') + 1).toInt()
                } else {
                    0
                }
            },
            series.items.map {
                if (it.resourceURI.isNotEmpty()) {
                    it.resourceURI.substring(it.resourceURI.lastIndexOf('/') + 1).toInt()
                } else {
                    0
                }
            })

}


@JsonClass(generateAdapter = true)
data class Comics(
    @Json(name = "available")
    val available: Int,
    @Json(name = "collectionURI")
    val collectionURI: String,
    @Json(name = "items")
    val items: List<ComicsItem>,
    @Json(name = "returned")
    val returned: Int
)

@JsonClass(generateAdapter = true)
data class ComicsItem(
    @Json(name = "name")
    val name: String,
    @Json(name = "resourceURI")
    val resourceURI: String
)


@JsonClass(generateAdapter = true)
data class Events(
    @Json(name = "available")
    val available: Int,
    @Json(name = "collectionURI")
    val collectionURI: String,
    @Json(name = "items")
    val items: List<EventsItem>,
    @Json(name = "returned")
    val returned: Int
)

@JsonClass(generateAdapter = true)
data class EventsItem(
    @Json(name = "name")
    val name: String,
    @Json(name = "resourceURI")
    val resourceURI: String
)


@JsonClass(generateAdapter = true)
data class Series(
    @Json(name = "available")
    val available: Int,
    @Json(name = "collectionURI")
    val collectionURI: String,
    @Json(name = "items")
    val items: List<SeriesItem>,
    @Json(name = "returned")
    val returned: Int
)

@JsonClass(generateAdapter = true)
data class SeriesItem(
    @Json(name = "name")
    val name: String,
    @Json(name = "resourceURI")
    val resourceURI: String
)


@JsonClass(generateAdapter = true)
data class Stories(
    @Json(name = "available")
    val available: Int,
    @Json(name = "collectionURI")
    val collectionURI: String,
    @Json(name = "items")
    val items: List<StoriesItem>,
    @Json(name = "returned")
    val returned: Int
)

@JsonClass(generateAdapter = true)
data class StoriesItem(
    @Json(name = "name")
    val name: String,
    @Json(name = "resourceURI")
    val resourceURI: String,
    @Json(name = "type")
    val type: String
)

@JsonClass(generateAdapter = true)
data class Thumbnail(
    @Json(name = "extension")
    val extension: String,
    @Json(name = "path")
    val path: String
)


@JsonClass(generateAdapter = true)
data class Url(
    @Json(name = "type")
    val type: String,
    @Json(name = "url")
    val url: String
)