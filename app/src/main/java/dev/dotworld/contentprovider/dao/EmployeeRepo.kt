package dev.dotworld.contentprovider.dao

import androidx.annotation.WorkerThread
import dev.dotworld.contentprovider.entity.EmployeeEntity
import kotlinx.coroutines.flow.Flow

class EmployeeRepo(private val dao: EmployeeDao) {
    @WorkerThread
  suspend  fun  getAllEmployee(): List<EmployeeEntity> {
      return  dao.getAllEmployee() }
    val getAllEmployeeLiveData: Flow<List<EmployeeEntity>> = dao.getAllEmployeeLiveData()
    @WorkerThread
    suspend fun insert(employeeEntity: EmployeeEntity): Long {
        return  dao.insert(employeeEntity)
    }
}