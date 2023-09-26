package com.example.contactsapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val REQUEST_PHONE_NUMBER = 111
    lateinit var contactUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val selectContacts = findViewById<Button>(R.id.selectContacts)

        selectContacts.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE;
            startActivityForResult(intent, REQUEST_PHONE_NUMBER)


        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PHONE_NUMBER && resultCode == Activity.RESULT_OK) {
            contactUri = data!!.data!!

            getPhoneNumber()
        }
    }


    @SuppressLint("Range")
    private fun getPhoneNumber() {
        val projection =
            arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER)
        val cursor = contentResolver.query(contactUri, projection, null, null, null);


        cursor?.use {
            if (it.moveToFirst()) {
                val name = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                val phoneNumber = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                val nameText = findViewById<TextView>(R.id.name)
                val phoneText = findViewById<TextView>(R.id.phoneNumber)
                nameText.text = name
                phoneText.text = phoneNumber
            }
        }
    }
}