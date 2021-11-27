package com.example.kirozh.itunesapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * @author Kirill Ozhigin on 22.11.2021
 */
@Entity
data class Song(
    var wrapperType: String = "",
    var kind: String = "",
    var artistId: String = "",
    var collectionId: String = "",
    @PrimaryKey
    var trackId: String = "",
    var artistName: String = "",
    var collectionName: String = "",
    var trackName: String = "",
    var collectionCensoredName: String = "",
    var trackCensoredName: String = "",
    var artistViewUrl: String = "",
    var collectionViewUrl: String = "",
    var trackViewUrl: String = "",
    var previewUrl: String = "",
    var artworkUrl30: String = "",
    var artworkUrl60: String = "",
    var artworkUrl100: String = "",
    var collectionPrice: Double = 0.0,
    var trackPrice: Double = 0.0,
    var releaseDate: String = "",
    var collectionExplicitness: String = "",
    var discCount: Int = 0,
    var discNumber: Int = 0,
    var trackCount: Int = 0,
    var trackNumber: Int = 0,
    var trackTimeMillis: String = "",
    var country: String = "",
    var currency: String = "",
    var primaryGenreName: String = "",
    var isStreamable: Boolean = false
)
