package dev.dotworld.contentprovider

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.dotworld.contentprovider.dao.EmployeeRepo
import dev.dotworld.contentprovider.database.EmployeeDatabase
import dev.dotworld.contentprovider.entity.EmployeeEntity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var employeeViewModel: EmployeeViewModel
    private var uri: Uri? = null
    private var database: EmployeeDatabase? = null
    private lateinit var name: EditText
    private lateinit var saley: EditText
    private lateinit var save: Button
    private lateinit var get: Button
    private var TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        name = findViewById(R.id.name)
        saley = findViewById(R.id.salary)
        get = findViewById(R.id.getData)
        save = findViewById(R.id.save)
        val recyclerView = findViewById<RecyclerView>(R.id.mainRecycler)
        val adapter = EmployeeAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val dao = EmployeeDatabase.getInstance(application).EmployeeDao()
        val repository = EmployeeRepo(dao)
        val factory = EmployeeViewModel.EmployeeViewModelFactory(repository)
        employeeViewModel = ViewModelProvider(this, factory).get(EmployeeViewModel::class.java)

        employeeViewModel.getAllEmployeeLiveData.observe(owner = this) { data ->
            data.let { adapter.submitList(it) }
        }

        save.setOnClickListener {
            val employeeEntity = EmployeeEntity(0, name.text.toString(), saley.text.toString())
            Log.d(TAG, "onCreate: ${employeeEntity._id}")
            Log.d(TAG, "onCreate: ${employeeEntity.name}")
            Log.d(TAG, "onCreate: ${employeeEntity.salary}")
            employeeViewModel.viewModelScope.launch {
                employeeViewModel.addEmp(employeeEntity)            }
        }
        get.setOnClickListener {
            Log.d(TAG, "getAllEmployee from employee: ")
            employeeViewModel.getAllEmployeeLiveData.observe(owner = this) { data ->
                data.let { adapter.submitList(it) }
            }
        }

    }
}