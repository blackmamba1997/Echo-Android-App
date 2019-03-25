package com.example.atreyabarui.echo.databases

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.atreyabarui.echo.Songs
import java.lang.Exception

class EchoDatabase : SQLiteOpenHelper {

    var _songList = ArrayList<Songs>()

    object Staticated {
        var DB_NAME = "FavouriteDatabase"
        var DB_TABLE = "FavouriteTable"
        val COLUMN_ID = "SongID"
        val COLUMN_SONG_TITLE = "SongTitle"
        val COLUMN_SONG_ARTIST = "SongArtist"
        val COLUMN_SONG_PATH = "SongPath"
        var DB_VERSION = 1
    }

    constructor(context: Context?) : super(context, Staticated.DB_NAME, null, Staticated.DB_VERSION)

    override fun onCreate(sqliteDatabase: SQLiteDatabase?) {
        sqliteDatabase?.execSQL("CREATE TABLE " + Staticated.DB_TABLE + "( " + Staticated.COLUMN_ID + " INTEGER, "
                + Staticated.COLUMN_SONG_ARTIST + " STRING, "
                + Staticated.COLUMN_SONG_TITLE + " STRING, " + Staticated.COLUMN_SONG_PATH + " STRING);")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun storeAsFavourite(id: Int?, artist: String?, songTItle: String?, path: String?) {
        val db = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(Staticated.COLUMN_ID, id)
        contentValues.put(Staticated.COLUMN_SONG_ARTIST, artist)
        contentValues.put(Staticated.COLUMN_SONG_TITLE, songTItle)
        contentValues.put(Staticated.COLUMN_SONG_PATH, path)
        db.insert(Staticated.DB_TABLE, null, contentValues)
        db.close()
    }

    fun queryDBList(): ArrayList<Songs>? {
        try {


            val db = this.readableDatabase
            val query_params = "SELECT * FROM " + Staticated.DB_TABLE
            var cSor = db.rawQuery(query_params, null)
            if (cSor.moveToFirst()) {
                do {
                    var _id = cSor.getInt(cSor.getColumnIndexOrThrow(Staticated.COLUMN_ID))
                    var _artist = cSor.getString(cSor.getColumnIndexOrThrow(Staticated.COLUMN_SONG_ARTIST))
                    var _title = cSor.getString(cSor.getColumnIndexOrThrow(Staticated.COLUMN_SONG_TITLE))
                    var _songPath = cSor.getString(cSor.getColumnIndexOrThrow(Staticated.COLUMN_SONG_PATH))
                    _songList.add(Songs(_id.toLong(), _title, _artist, _songPath, 0))

                } while (cSor.moveToNext())
            } else {
                return null
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return _songList
    }

    fun checkifIdExists(_id: Int?): Boolean {
        var storeId = -1090
        var db = this.readableDatabase
        var query_params = "SELECT * FROM " + Staticated.DB_TABLE + " WHERE SongID = '$_id'"
        val cSor = db.rawQuery(query_params, null)
        if (cSor.moveToFirst()) {
            do {
                storeId = cSor.getInt(cSor.getColumnIndexOrThrow(Staticated.COLUMN_ID))
            } while (cSor.moveToNext())
        } else
            return false
        return storeId != -1090
    }

    fun deleteFavourite(_id: Int) {
        val db = this.writableDatabase
        db.delete(Staticated.DB_TABLE, Staticated.COLUMN_ID + " = " + _id, null)
        db.close()
    }

    fun checkSize(): Int {
        var counter = 0
        var db = this.readableDatabase
        var query_params = "SELECT * FROM " + Staticated.DB_TABLE
        val cSor = db.rawQuery(query_params, null)
        if (cSor.moveToFirst()) {
            do {
                counter = counter + 1
            } while (cSor.moveToNext())
        } else
            return 0
        return counter
    }

}