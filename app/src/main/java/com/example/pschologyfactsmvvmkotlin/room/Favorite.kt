package com.example.pschologyfactsmvvmkotlin.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "FavFacts")
data class Favorite(

    @PrimaryKey(autoGenerate = true) val id: Int,

    @ColumnInfo val fact: String
) : Parcelable
