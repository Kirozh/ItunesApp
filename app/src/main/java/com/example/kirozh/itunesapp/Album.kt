package com.example.kirozh.itunesapp

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * @author Kirill Ozhigin on 19.11.2021
 */
@Entity
data class Album(
    var wrapperType: String = "",
    var collectionType: String = "",
    var artistId: String = "",
    @PrimaryKey
    var collectionId: String = "",
    var artistName: String = "",
    var collectionCensoredName: String = "",
    var artworkUrl60: String = "",
    var artworkUrl100: String = "",
    var collectionPrice: Double = 0.0,
    var collectionExplicitness: String = "",
    var trackCount: Int = 0,
    var copyright: String = "",
    var country: String = "",
    var currency: String = "",
    var releaseDate: String = "",
    @SerializedName("primaryGenreName")
    var genre: String = "",
    @SerializedName("collectionName")
    var title: String = "",
    @SerializedName("artistViewUrl")
    var artistUrl: String = "",
    @SerializedName("collectionViewUrl")
    var collectionViewUrl: String = ""

) : Parcelable, Comparable<Album> {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(wrapperType)
        p0?.writeString(collectionType)
        p0?.writeString(artistId)
        p0?.writeString(collectionId)
        p0?.writeString(artistName)
        p0?.writeString(collectionCensoredName)
        p0?.writeString(artworkUrl60)
        p0?.writeString(artworkUrl100)
        p0?.writeDouble(collectionPrice)
        p0?.writeString(collectionExplicitness)
        p0?.writeInt(trackCount)
        p0?.writeString(copyright)
        p0?.writeString(country)
        p0?.writeString(currency)
        p0?.writeString(releaseDate)
        p0?.writeString(genre)
        p0?.writeString(title)
        p0?.writeString(artistUrl)
        p0?.writeString(collectionViewUrl)
    }

    override fun compareTo(other: Album): Int {
        return if (this.title < other.title)
            -1
        else
            if (this.title > other.title)
                1
            else
                0
    }

}
