package com.example.myapplication14

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var dBmain: DBmain? = null
    var sqLiteDatabase: SQLiteDatabase? = null
    var lname: EditText? = null
    var fname: EditText? = null
    var submit: Button? = null
    var display: Button? = null
    var edit: Button? = null
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dBmain = DBmain(this)
        //create object
        findid()
        insertData()
        cleardata()
        editdata()
    }

    private fun editdata() {
        if (intent.getBundleExtra("studata") != null) {
            val bundle = intent.getBundleExtra("studata")
            id = bundle!!.getInt("id")
            fname!!.setText(bundle.getString("fname"))
            lname!!.setText(bundle.getString("lname"))
            edit!!.visibility = View.VISIBLE
            submit!!.visibility = View.GONE
        }
    }

    private fun cleardata() {
        fname!!.setText("")
        lname!!.setText("")
    }

    private fun insertData() {
        submit!!.setOnClickListener {
            val contentValues = ContentValues()
            contentValues.put("fname", fname!!.text.toString().trim { it <= ' ' })
            contentValues.put("lname", lname!!.text.toString().trim { it <= ' ' })
            sqLiteDatabase = dBmain?.getWritableDatabase()
            val recid = sqLiteDatabase!!.insert("subject", null, contentValues)
            if (recid != null) {
                Toast.makeText(this@MainActivity, "successfully insert", Toast.LENGTH_SHORT).show()
                cleardata()
            } else {
                Toast.makeText(this@MainActivity, "something wrong try again", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        display!!.setOnClickListener {
            val intent = Intent(this@MainActivity, MainActivity2::class.java)
            startActivity(intent)
        }
        edit!!.setOnClickListener {
            val contentValues = ContentValues()
            contentValues.put("fname", fname!!.text.toString().trim { it <= ' ' })
            contentValues.put("lname", lname!!.text.toString().trim { it <= ' ' })
            sqLiteDatabase = dBmain?.getWritableDatabase()
            val recid = sqLiteDatabase!!.update(
                "subject", contentValues,
                "id=$id", null
            ).toLong()
            if (recid != -1L) {
                Toast.makeText(this@MainActivity, "Update successfully", Toast.LENGTH_SHORT).show()
                submit!!.visibility = View.VISIBLE
                edit!!.visibility = View.GONE
                cleardata()
            } else {
                Toast.makeText(this@MainActivity, "something wrong try again", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun findid() {
        fname = findViewById<View>(R.id.fname) as EditText
        lname = findViewById<View>(R.id.lname) as EditText
        submit = findViewById<View>(R.id.submit_btn) as Button
        display = findViewById<View>(R.id.display_btn) as Button
        edit = findViewById<View>(R.id.edit_btn) as Button
    }
}