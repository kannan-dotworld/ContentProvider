package dev.dotworld.contentprovider.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="employee")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name="_id")
    var _id:Int,
    @ColumnInfo(name = "name")var name:String,
   @ColumnInfo(name="salary")  var salary:String) {
}