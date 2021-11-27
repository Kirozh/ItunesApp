package com.example.kirozh.itunesapp

import com.google.gson.annotations.SerializedName

/**
 * @author Kirill Ozhigin on 20.11.2021
 */
data class AlbumResponseModel(
    var resultCount: Int,
    @SerializedName("results")
    var albumResults: List<Album>
)
