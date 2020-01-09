package com.example.passlin

import android.content.ContentValues
import android.content.Context

import com.example.passlin.FeedReaderContract.FeedEntry
import com.example.passlin.FeedReaderContract.FeedEntry.COLUMN_NAME_DATE_TIME
import com.example.passlin.FeedReaderContract.FeedEntry.COLUMN_NAME_LOGIN
import com.example.passlin.FeedReaderContract.FeedEntry.COLUMN_NAME_NOTE
import com.example.passlin.FeedReaderContract.FeedEntry.COLUMN_NAME_PASSWORD
import com.example.passlin.FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteOpenHelper


class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        private var instance: DBHelper? = null
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Passlin.db"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    COLUMN_NAME_TITLE + "  TEXT PRIMARY KEY," +
                    COLUMN_NAME_LOGIN + " TEXT," +
                    COLUMN_NAME_PASSWORD + " TEXT," +
                    COLUMN_NAME_NOTE + " TEXT," +
                    COLUMN_NAME_DATE_TIME + " TEXT)"

        private const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME
    }

    fun getInstance(context: Context): DBHelper {
        if (instance == null) {
            instance = DBHelper(context)
        }
        return instance as DBHelper
    }

    fun insertIntoDB(title: String, login: String, password: String, note: String?, dateTime: String, context: Context) {
        SQLiteDatabase.loadLibs(context)
        val db = getInstance(context).getWritableDatabase("KILLTHEMALL")
        val cv = ContentValues()
        cv.put(COLUMN_NAME_TITLE, title)
        cv.put(COLUMN_NAME_LOGIN, login)
        cv.put(COLUMN_NAME_PASSWORD, password)
        cv.put(COLUMN_NAME_NOTE, note)
        cv.put(COLUMN_NAME_DATE_TIME, dateTime)

        db.insert(FeedEntry.TABLE_NAME, null, cv)

        val cursor = db.rawQuery("SELECT * FROM '" + FeedEntry.TABLE_NAME + "';", null)
        cursor.close()
        db.close()
    }

    fun getDataFromDB(context: Context): ArrayList<Model> {
        SQLiteDatabase.loadLibs(context)
        val modelList = ArrayList<Model>()
        val db = getInstance(context).getWritableDatabase("KILLTHEMALL")
        val cursor = db.rawQuery("SELECT * FROM '" + FeedEntry.TABLE_NAME + "';", null)
        if (cursor.moveToFirst()) {
            do {
                val model = Model()
                model.title = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TITLE))
                model.login = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_LOGIN))
                model.password = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PASSWORD))
                model.note = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NOTE))
                model.date = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DATE_TIME))

                modelList.add(model )
            } while (cursor.moveToNext())
        }
        return modelList
    }

    fun deleteARow(title: String, context: Context) {
        SQLiteDatabase.loadLibs(context)
        val db = getInstance(context).getWritableDatabase("KILLTHEMALL")
        db.delete(FeedEntry.TABLE_NAME, "title" + " = ?", arrayOf(title))
        db.close()
    }

    fun updateARow(title: String, login: String, password: String, note: String, dateTime: String, context: Context) {

        val db = getInstance(context).getWritableDatabase("KILLTHEMALL")
        val cv = ContentValues()
        cv.put(COLUMN_NAME_TITLE, title)
        cv.put(COLUMN_NAME_LOGIN, login)
        cv.put(COLUMN_NAME_PASSWORD, password)
        cv.put(COLUMN_NAME_PASSWORD, note)
        cv.put(COLUMN_NAME_DATE_TIME, dateTime)

        db.update(FeedEntry.TABLE_NAME, cv, "title" + " = ?", arrayOf(title))
    }
}