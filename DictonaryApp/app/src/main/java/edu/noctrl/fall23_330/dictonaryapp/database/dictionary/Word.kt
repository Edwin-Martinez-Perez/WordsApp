package edu.noctrl.fall23_330.dictonaryapp.database.dictionary

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary_table")
data class Word(
    @PrimaryKey @ColumnInfo(name = "word") val id: String,
    @NonNull @ColumnInfo(name = "shortdefs") val shortdefs: String = "",
    @NonNull @ColumnInfo(name = "shortdef_count") val shortDefCount: Int = 0,
    @ColumnInfo(name = "image_file_name") val imageFileName: String? = "",
    @ColumnInfo(name = "status") @NonNull val active: Boolean = true
)

/*
@Entity
data class Dictionary(
    @PrimaryKey @ColumnInfo(name = "word") val id: String,
    @NonNull @ColumnInfo(name = "shortdefs") val shortdefs: List<String>,
    @NonNull @ColumnInfo(name = "shortdef1") val shortDef1: String,
    @ColumnInfo(name = "shortdef2") val shortDef2: String,
    @ColumnInfo(name = "shortdef3") val shortDef3: String,
    @ColumnInfo(name = "shortdef_count") @NonNull val shortDefCount: Int,
    @ColumnInfo(name = "image_file_name") val imageFileName: String,
    @ColumnInfo(name = "status") @NonNull val active: Boolean
)
 */