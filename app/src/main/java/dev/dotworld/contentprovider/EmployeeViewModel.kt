package dev.dotworld.contentprovider

import android.util.Log
import androidx.annotation.UiThread
import androidx.lifecycle.*
import dev.dotworld.contentprovider.dao.EmployeeRepo
import dev.dotworld.contentprovider.entity.EmployeeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmployeeViewModel(private val employeeRepo: EmployeeRepo) : ViewModel() {
    var TAG = EmployeeViewModel::class.java.simpleName
    val getAllEmployeeLiveData: LiveData<List<EmployeeEntity>> = employeeRepo.getAllEmployeeLiveData.asLiveData()

    fun addEmp(employeeEntity: EmployeeEntity){
        insertEmployee(employeeEntity)
    }

    fun insertEmployee(employeeEntity: EmployeeEntity) = viewModelScope.launch {
        var row = employeeRepo.insert(employeeEntity)
        if (row > -1) {
            Log.d(TAG, "insert Employee: done")
        } else {
            Log.d(TAG, "insert Employee: fail ")
        }
    }

    class EmployeeViewModelFactory(private val employeeRepo: EmployeeRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EmployeeViewModel::class.java)) {
                return EmployeeViewModel(employeeRepo) as T
            }
            throw  IllegalArgumentException("Unknown ViewModel class")
        }
    }
}