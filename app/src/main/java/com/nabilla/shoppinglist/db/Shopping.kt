package com.nabilla.shoppinglist.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

@Entity(tableName = "shopping_list")
//@Parcelize
data class Shopping(
    @ColumnInfo(name = "title")
    val shoppingTitle:String,
    @ColumnInfo(name = "price")
    val shoppingPrice:Double,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "selected")
    var isSelected:Int,
    @PrimaryKey(autoGenerate = true) var id:Int = 0
) {
    val createdDateFormatted:String
        get() = DateFormat.getDateTimeInstance().format(timestamp)
}