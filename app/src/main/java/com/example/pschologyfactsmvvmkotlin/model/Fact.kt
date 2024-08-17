package com.example.pschologyfactsmvvmkotlin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fact(
    val id: Int,
    val category: String,
    val text: String
) : Parcelable