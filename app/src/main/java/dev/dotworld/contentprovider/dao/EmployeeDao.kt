package dev.dotworld.contentprovider.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.dotworld.contentprovider.entity.EmployeeEntity
import kotlinx.coroutines.flow.Flow


@Dao
public interface EmployeeDao {
    @Query("SELECT * FROM employee ORDER BY _id ASC")
    suspend fun getAllEmployee(): List<EmployeeEntity>
    @Query("SELECT * FROM employee ORDER BY _id ASC")
     fun getAllEmployeeLiveData(): Flow<List<EmployeeEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(employeeEntity: EmployeeEntity):Long

    @Query("DELETE  FROM employee")
     fun deleteAll()
}