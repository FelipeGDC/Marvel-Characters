package com.fgdc.marvelcharacters.data.datasource.core.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse<T>(
    @Json(name = "attributionHTML")
    val attributionHTML: String,
    @Json(name = "attributionText")
    val attributionText: String,
    @Json(name = "code")
    val code: Int,
    @Json(name = "copyright")
    val copyright: String,
    @Json(name = "data")
    val apiData: ApiResponseData<T>,
    @Json(name = "etag")
    val etag: String,
    @Json(name = "status")
    val status: String
)
