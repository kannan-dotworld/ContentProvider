package dev.dotworld.contentprovider

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var uri: Uri?=null
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
        save.setOnClickListener {
            var values = ContentValues()
            values.put(ContentProviderRepo.NAME, name.text.toString())
            values.put(ContentProviderRepo.SALARY, saley.text.toString())
            uri = contentResolver.insert(ContentProviderRepo.CONTENT_URI, values)
            Log.d(TAG, "onCreate: ${uri.toString()}")
            Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show()
        }
        get.setOnClickListener {
            val PROVIDER_NAME = "content://dev.dotworld.contentprovider.ContentProviderRepo"
            var c=managedQuery(Uri.parse(PROVIDER_NAME),null,null,null,"name")
            if (c.moveToFirst()){
                do {
                    Log.d(TAG, "columnNames size : ${c.columnNames.size} || column count =${c.columnCount} || position =${c.position}")
                        Log.d(TAG, "onCreate If : name= "+ c.getString(c.getColumnIndex( ContentProviderRepo.NAME))  +" || column id ="+ c.getString (c.getColumnIndex( ContentProviderRepo._ID))+" ||salary ="+ c.getString(c.getColumnIndex(ContentProviderRepo.SALARY)))

                }while (c.moveToNext())
            }
        }

    }
}