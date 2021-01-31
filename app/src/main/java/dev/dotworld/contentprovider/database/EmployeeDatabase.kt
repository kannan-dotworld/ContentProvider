package dev.dotworld.contentprovider.database

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.dotworld.contentprovider.dao.EmployeeDao
import dev.dotworld.contentprovider.entity.EmployeeEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [EmployeeEntity::class],version = 1) // tables

abstract class  EmployeeDatabase :RoomDatabase() {

    abstract fun EmployeeDao() : EmployeeDao
    companion object{
        @Volatile
        private var INSTANCE :EmployeeDatabase?= null
        fun getInstance(context: Context):EmployeeDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EmployeeDatabase::class.java,
                        "employeedb"
                    ).build()
                }
                return instance
            }
        }

    }




}