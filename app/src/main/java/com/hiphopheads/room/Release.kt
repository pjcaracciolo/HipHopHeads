package com.hiphopheads.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "releaseTable")
class Release (
    @ColumnInfo(name="title") val title: String,
    @ColumnInfo(name="url") val url: String,
    @ColumnInfo(name="permalink") val permalink: String,
    @ColumnInfo(name="thumbnail") val thumbnail: String,
    @ColumnInfo(name="timestamp") val timestamp: Long,
    @ColumnInfo(name="spotify", defaultValue = "none") val spotify: String,
    @ColumnInfo(name="apple", defaultValue = "none") val apple: String,
    @ColumnInfo(name="amazon", defaultValue = "none") val amazon: String,
    @ColumnInfo(name="tidal", defaultValue = "none") val tidal: String,
    @ColumnInfo(name="deezer", defaultValue = "none") val deezer: String,
    @ColumnInfo(name="youtubeMusic", defaultValue = "none") val youtubeMusic: String,
    @ColumnInfo(name="youtube", defaultValue = "none") val youtube: String,
    @ColumnInfo(name="soundcloud", defaultValue = "none") val soundcloud: String,
    @ColumnInfo(name="bandcamp", defaultValue = "none") val bandcamp: String
    ) {
    @PrimaryKey(autoGenerate = true) var id = 0
}