package com.example.myapplication14

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBmain(context: Context?) :
    SQLiteOpenHelper(context, DBNAME, null, VER) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = "create table " + TABLE + "(id integer primary key, fname text, lname text)"
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val query = "drop table if exists " + TABLE + ""
        db.execSQL(query)
        onCreate(db)
    }

    companion object {
        private const val DBNAME = "student"
        private const val TABLE = "subject"
        private const val VER = 1
    }
}