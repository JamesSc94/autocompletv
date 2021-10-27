package com.jamessc94.autocompletetvj.DB

import androidx.room.*
import com.jamessc94.autocompletetvj.Util.converters_date
import java.time.OffsetDateTime
import java.util.*

@Entity(tableName = "search")
data class D_search(
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0L,

    @ColumnInfo(name = "created_at")
    @TypeConverters(converters_date::class)
    var created_at : String = "",

    @ColumnInfo(name = "title")
    var title : String = "",

    @ColumnInfo(name = "category")
    var category: String = "",

    @ColumnInfo(name = "extra")
    var extra: String = "",

    @ColumnInfo(name = "img")
    var img: String = "",

    @ColumnInfo(name = "pos")
    var pos : Int = 0

){
    override fun toString(): String = title

}