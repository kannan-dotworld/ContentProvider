package dev.dotworld.contentprovider

import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.util.Log


class ContentProviderRepo : ContentProvider() {


    private val EMPLOYEE_PROJECTION_MAP: HashMap<String, String>? = null
    private val EMPLOYEE: Int = 1
    private val EMPLOYEE_ID: Int = 2
    private val sURIMatcher = UriMatcher(UriMatcher.NO_MATCH)

    companion object {
        val NAME: String = "name"
        val SALARY: String = "salary"
        val _ID: String = "_id"
        val TAG = ContentProviderRepo::class.java.simpleName
        val DATABASE_NAME = "Dotworls"
        val DATABASE_VERSON = 1
        val TABLE_NAME = "employee"
        val PROVIDER_NAME = "dev.dotworld.contentprovider.ContentProviderRepo"
        val URL = "content://$PROVIDER_NAME/employee"
        val CONTENT_URI = Uri.parse(URL)
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME (_id INTEGER PRIMARY KEY AUTOINCREMENT, $NAME TEXT NOT NULL, $SALARY TEXT NOT NULL);"
    }

    init {
        sURIMatcher.addURI(PROVIDER_NAME, "employee", EMPLOYEE)
        sURIMatcher.addURI(PROVIDER_NAME, "employee/#", EMPLOYEE_ID)


    }

    private lateinit var db: SQLiteDatabase

    // db helpers
    private class DatabaseHelper(context: Context?) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSON) {

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(CREATE_TABLE)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP TABLE  IF EXISTS $TABLE_NAME")
        }
    }


    // CONTENT PROVIDER METHODS
    override fun onCreate(): Boolean {
        val context = context
        val dbHelper = DatabaseHelper(context)
        db = dbHelper.writableDatabase
        return if (db == null) false else true

    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d(TAG, "insert:  salary"+values?.get(SALARY))
        Log.d(TAG, "insert:  name"+values?.get(NAME))
        var rowID: Long = db.insert(TABLE_NAME, "", values)
        if (rowID > 0) {
            var uri = ContentUris.withAppendedId(CONTENT_URI, rowID)
            context?.contentResolver?.notifyChange(uri, null)
            return uri
        }
        throw SQLException(" failed  to add a record into $uri")

    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        var qb = SQLiteQueryBuilder()
        qb.tables = TABLE_NAME
        Log.d(TAG, "query: ")
        when (sURIMatcher.match(uri)) {
            EMPLOYEE -> {
                qb.projectionMap = EMPLOYEE_PROJECTION_MAP
                Log.d(TAG, "query: ")
            }
            EMPLOYEE_ID -> {
                qb.appendWhere(_ID + "=${uri.pathSegments[1]}")
                Log.d(TAG, "pathSegments: ${uri.pathSegments[1]} ")
            }
        }
        var sortOrder1 = sortOrder
        if (sortOrder == null || sortOrder == "") {
            sortOrder1 = NAME
        }
        Log.d(TAG, "projection:  size"+projection?.size)
        var c: Cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder1)
        c.setNotificationUri(context?.contentResolver, uri);
        return c
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        var count = 0
        when (sURIMatcher.match(uri)) {
            EMPLOYEE -> {
                db.delete(TABLE_NAME, selection, selectionArgs)
            }
            EMPLOYEE_ID -> {
                val id = uri.pathSegments[1]
                var selectionQuery =
                    if (selection?.isNotEmpty() == true) " AND ($selection)" else ""
                count = db.delete(TABLE_NAME, "$_ID=$id  $selectionQuery", selectionArgs)
            }
            else -> throw IllegalArgumentException("unkown uri$uri")
        }
        context?.contentResolver?.notifyChange(uri, null);
        return count;
    }

    override fun getType(uri: Uri): String? {

        return when (sURIMatcher.match(uri)) {
            EMPLOYEE -> {
                "vnd.android.cursor.dir/employee";
//                "vnd.android.cursor.dir/vnd.example.employee";
            }
            EMPLOYEE_ID -> {
                "vnd.android.cursor.item/employee";
            }
            else -> throw  IllegalArgumentException("Unsupported URI: $uri");
        }
    }


    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {

        var count = 0
        when (sURIMatcher.match(uri)) {
            EMPLOYEE -> {
                db.update(TABLE_NAME, values, selection, selectionArgs)
            }
            EMPLOYEE_ID -> {
                val id = uri.pathSegments[1]
                var selectionQuery =
                    if (selection?.isNotEmpty() == true) " AND ($selection)" else ""
                count = db.update(TABLE_NAME, values, "$_ID=$id  $selectionQuery", selectionArgs)
            }
            else -> throw IllegalArgumentException("unkown uri$uri")
        }
        context?.contentResolver?.notifyChange(uri, null);
        return count;

    }
}