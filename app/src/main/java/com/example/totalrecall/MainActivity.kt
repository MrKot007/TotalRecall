package com.example.totalrecall

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import android.Manifest
import com.example.totalrecall.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val REQUEST_PERMISSION = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_PERMISSION)
        }else {
            getContacts()
        }
        binding.contacts.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {
            getContacts()
        }
    }

    private fun getContacts() {
        binding.contacts.adapter = ContactsAdapter(getContactsData())
    }

    private fun getContactsData(): ArrayList<Contact> {
        val contactList = ArrayList<Contact>()
        val contactsCursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        if((contactsCursor?.count ?: 0) > 0) {
            while (contactsCursor != null && contactsCursor.moveToNext()) {
                val rowID = contactsCursor.getString(contactsCursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                val name = contactsCursor.getString(contactsCursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                var phone = ""
                if (contactsCursor.getInt(contactsCursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val phoneNumberCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf<String>(rowID),
                        null
                    )
                    while (phoneNumberCursor!!.moveToNext()) {
                        phone += phoneNumberCursor.getString(phoneNumberCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)) + "\n"
                    }
                    phoneNumberCursor.close()
                }
                contactList.add(Contact(name, phone))
            }
        }
        contactsCursor!!.close()
        return contactList
    }
}