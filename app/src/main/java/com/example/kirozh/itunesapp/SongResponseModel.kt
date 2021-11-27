package com.example.kirozh.itunesapp

/**
 * @author Kirill Ozhigin on 22.11.2021
 */
data class SongResponseModel(
    var resultCount: Int,
    var results: List<Song>
)