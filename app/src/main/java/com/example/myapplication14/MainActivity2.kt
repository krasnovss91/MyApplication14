package com.example.myapplication14

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import java.util.*


class MainActivity2 : AppCompatActivity() {
    var dBmain: DBmain? = null
    var sqLiteDatabase: SQLiteDatabase? = null
    lateinit var fname: Array<String?>
    lateinit var lname: Array<String?>
    lateinit var id: IntArray
    var lv: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        dBmain = DBmain(this)
        findid()
        displaydata()
    }

    private fun displaydata() {
        sqLiteDatabase = dBmain!!.readableDatabase
        val cursor = sqLiteDatabase?.rawQuery("select *from subject", null)
        if (cursor != null) {
            if (cursor.count > 0) {
                id = IntArray(cursor.count)
                fname = arrayOfNulls(cursor.count)
                lname = arrayOfNulls(cursor.count)
                var i = 0
                while (cursor.moveToNext()) {
                    id[i] = cursor.getInt(0)
                    fname[i] = cursor.getString(1)
                    lname[i] = cursor.getString(2)
                    i++
                }
                val custAdapter: CustAdapter = CustAdapter()
                lv!!.adapter = custAdapter
            }
        }
    }

    private fun findid() {
        lv = findViewById<View>(R.id.lv) as ListView
    }

    private inner class CustAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return fname.size
        }

        override fun getItem(position: Int): Any? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
            var convertView = convertView
            val txtfname: TextView
            val txtlname: TextView
            val txt_edit: ImageButton
            val txt_delete: ImageButton
            val cardview: CardView
            convertView =
                LayoutInflater.from(this@MainActivity2).inflate(R.layout.singledata, parent, false)
            txtfname = convertView.findViewById(R.id.txt_fname)
            txtlname = convertView.findViewById(R.id.txt_lname)
            txt_edit = convertView.findViewById(R.id.txt_edti)
            txt_delete = convertView.findViewById(R.id.txt_delete)
            cardview = convertView.findViewById(R.id.cardview)
            cardview.setOnClickListener { //background random color
                val random = Random()
                cardview.setCardBackgroundColor(
                    Color.argb(
                        255,
                        random.nextInt(256),
                        random.nextInt(256),
                        random.nextInt(256)
                    )
                )
                txt_delete.visibility = View.VISIBLE
                txt_edit.visibility = View.VISIBLE
                txtfname.visibility = View.GONE
                txtlname.visibility = View.GONE
            }
            txtfname.text = fname[position]
            txtlname.text = lname[position]
            txt_edit.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("id", id[position])
                bundle.putString("fname", fname[position])
                bundle.putString("lname", lname[position])
                val intent = Intent(this@MainActivity2, MainActivity::class.java)
                intent.putExtra("studata", bundle)
                startActivity(intent)
            }
            txt_delete.setOnClickListener {
                sqLiteDatabase = dBmain!!.readableDatabase
                val recremove =
                    sqLiteDatabase?.delete("subject", "id=" + id[position], null)?.toLong()
                if (recremove != -1L) {
                    Toast.makeText(this@MainActivity2, "successfully delete", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this@MainActivity2, MainActivity::class.java))
                    displaydata()
                }
            }
            return convertView
        }
    }
}