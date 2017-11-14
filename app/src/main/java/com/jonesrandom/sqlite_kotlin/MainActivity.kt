package com.jonesrandom.sqlite_kotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import com.jonesrandom.sqlite_kotlin.database.DatabaseAdapter
import com.jonesrandom.sqlite_kotlin.model.ModelMahasiswa
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbAdapter = DatabaseAdapter(this)

        toolbar.title = "SQLite Kotlin"

        etNama.addTextChangedListener(Watcher(inNama))
        etNim.addTextChangedListener(Watcher(inNim))

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getSemester())
        spinnerSemester.adapter = adapter

        btnInsert.setOnClickListener {

            val nama = etNama.text.toString()
            val nim = etNim.text.toString()
            val semester = spinnerSemester.selectedItem.toString()
            val id = spinnerSemester.selectedItemId

            if (nama.isEmpty()) {
                inNama.error = "Masukan Nama Mahasiswa"
                return@setOnClickListener
            }

            if (nim.isEmpty()) {
                inNim.error = "Masukan Nim Mahasiswa"
                return@setOnClickListener
            }

            if (id < 1) {
                Toast.makeText(this, "Pilih Semester", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val data = ModelMahasiswa()
            data.Nama = nama
            data.Nim = nim.toInt()
            data.Semester = semester

            val stat = dbAdapter.insertData(data)

            if (stat > 0) {
                spinnerSemester.setSelection(0)
                etNama.text.clear()
                etNim.text.clear()

                etNim.clearFocus()
                etNama.clearFocus()
            }

        }
        btnLihatData.setOnClickListener {
            startActivity(Intent(this, DaftarMahasiswa::class.java))
        }

    }

    private fun getSemester(): List<String> {
        return listOf("SEMESTER", "SEMESTER 1", "SEMESTER 2", "SEMESTER 3", "SEMESTER 4", "SEMESTER 5", "SEMESTER 6", "SEMESTER 7")
    }

    private class Watcher(textinput: TextInputLayout) : TextWatcher {

        val input = textinput

        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            input.isErrorEnabled = false
        }
    }
}
