package com.example.sqliteapp

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btnAdd = findViewById<Button>(R.id.btnAdd)
        btnAdd.setOnClickListener(this)
        var btnRead = findViewById<Button>(R.id.btnRead)
        btnRead.setOnClickListener(this)
        var btnClear = findViewById<Button>(R.id.btnClear)
        btnClear.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        var etName = findViewById<EditText>(R.id.etName)
        var etEmail = findViewById<EditText>(R.id.etEmail)
        var dbHelper = DBHelper(this)
        val name = etName.text.toString()
        val email = etEmail.text.toString()
        val database: SQLiteDatabase = dbHelper.writableDatabase
        val contentValues = ContentValues()
        when (v.id) {
            R.id.btnAdd -> {
                contentValues.put(DBHelper.KEY_NAME, name)
                contentValues.put(DBHelper.KEY_MAIL, email)
                database.insert(DBHelper.TABLE_CONTACTS, null, contentValues)
            }

            R.id.btnRead -> {
                val cursor =
                    database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null)
                if (cursor.moveToFirst()) {
                    val idIndex = cursor.getColumnIndex(DBHelper.KEY_ID)
                    val nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME)
                    val emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL)
                    do {
                        Log.d(
                            "mLog", "ID = " + cursor.getInt(idIndex) +
                                    ", name = " + cursor.getString(nameIndex) +
                                    ", email = " + cursor.getString(emailIndex)
                        )
                    } while (cursor.moveToNext())
                } else Log.d("mLog", "0 rows")
                cursor.close()
            }

            R.id.btnClear -> database.delete(DBHelper.TABLE_CONTACTS, null, null)
        }
        dbHelper.close()
    }
}
