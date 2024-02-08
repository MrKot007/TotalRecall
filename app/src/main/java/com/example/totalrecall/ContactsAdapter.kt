package com.example.totalrecall

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.totalrecall.databinding.ContactItemBinding

class ContactsViewHolder(val binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root)
class ContactsAdapter(val contacts: ArrayList<Contact>) : RecyclerView.Adapter<ContactsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder(ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.binding.contactName.text = contacts[position].name
        holder.binding.phoneNumber.text = contacts[position].phone
    }

}