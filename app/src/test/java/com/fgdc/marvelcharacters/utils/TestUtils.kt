package com.fgdc.marvelcharacters.utils

import com.fgdc.data.datasource.remote.entity.*
import com.fgdc.marvelcharacters.utils.extensions.empty

fun <T> mockApiResponse(list: List<T>) = ApiResponse(
    attributionHTML = String.empty(),
    attributionText = String.empty(),
    code = 0,
    copyright = String.empty(),
    mockApiResponseData(list),
    String.empty(),
    String.empty()
)

fun <T> mockApiResponseData(list: List<T>) =
    ApiResponseData(count = 0, limit = 0, offset = 0, list, 0)

fun mockCharacters(amount: Int): List<CharacterMarvel> {
    val charactersList = mutableListOf<CharacterMarvel>()
    val character = CharacterMarvel(
        comics = Comics(0, String.empty(), listOf(ComicsItem(String.empty(), String.empty())), 0),
        description = String.empty(),
        events = Events(0, String.empty(), listOf(EventsItem(String.empty(), String.empty())), 0),
        modified = String.empty(),
        id = 0,
        name = "Character Name",
        resourceURI = String.empty(),
        series = Series(0, String.empty(), listOf(SeriesItem(String.empty(), String.empty())), 0),
        stories = Stories(
            0,
            String.empty(),
            listOf(StoriesItem(String.empty(), String.empty(), String.empty())),
            0
        ),
        thumbnail = Thumbnail(String.empty(), String.empty()),
        urls = listOf(Url(String.empty(), String.empty())),
    )
    for (i: Int in 1..amount) {
        charactersList.add(character)
    }
    return charactersList
}

fun mockComics(amount: Int): List<ComicMarvel> {
    val comicsList = mutableListOf<ComicMarvel>()
    val comic = ComicMarvel(
        id = 0,
        title = "Comic name",
        listOf(UrlComic(String.empty(), String.empty())),
        listOf(Price(0.0, String.empty())),
        ThumbnailComic(String.empty(), String.empty())
    )
    for (i: Int in 1..amount) {
        comicsList.add(comic)
    }
    return comicsList
}

fun mockSeries(amount: Int): List<SeriesMarvel> {
    val seriesList = mutableListOf<SeriesMarvel>()
    val series = SeriesMarvel(
        id = 0,
        title = "Series name",
        listOf(UrlSeries(String.empty(), String.empty())),
        ThumbnailSeries(String.empty(), String.empty())
    )
    for (i: Int in 1..amount) {
        seriesList.add(series)
    }
    return seriesList
}
