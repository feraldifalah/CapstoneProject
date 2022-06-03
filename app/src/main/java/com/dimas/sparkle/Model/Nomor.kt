package com.dimas.sparkle.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Nomor(
    var name: String,
    var description: String,
    var photo: Int
) : Parcelable