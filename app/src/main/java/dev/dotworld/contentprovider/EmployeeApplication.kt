package dev.dotworld.contentprovider

import android.app.Application
import android.util.Log
import dev.dotworld.contentprovider.dao.EmployeeDao
import dev.dotworld.contentprovider.dao.EmployeeRepo
import dev.dotworld.contentprovider.database.EmployeeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class EmployeeApplication:Application() {
    private val TAG= EmployeeApplication::class.java.simpleName
    val applicationScop = CoroutineScope(SupervisorJob())
//    val database by lazy {
//        Log.d(TAG, "database: lazy init")
//        EmployeeDatabase.getDatabase(this,applicationScop)
//    }
//    val  repository by lazy { EmployeeRepo(database.EmployeeDao()) }

}