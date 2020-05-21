package com.kevin.testgridandroid

import android.os.Parcel
import android.os.Parcelable



//{
//    "id": "0",
//    "author": "Alejandro Escamilla",
//    "width": 5616,
//    "height": 3744,
//    "url": "https://unsplash.com/...",
//    "download_url": "https://picsum.photos/..."
//}

data class Data (

    var id: String? = "",
    var author: String? = "",
    var width: Long? = 0,
    var height: Long? = 0,
    var url: String? = "",
    var download_url: String? = ""

) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(author)
        parcel.writeValue(width)
        parcel.writeValue(height)
        parcel.writeString(url)
        parcel.writeString(download_url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }
}