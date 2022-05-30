package com.dicoding.picodiploma.myrecyclerview.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Nomor(
    var name: String,
    var description: String,
    var photo: Int
) : Parcelable