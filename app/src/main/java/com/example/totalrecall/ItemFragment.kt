package com.example.totalrecall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.totalrecall.databinding.ItemFragmentBinding
import com.example.totalrecall.databinding.ToDoFragmentBinding

class ContactsFragment(val contacts: ArrayList<Contact>) : Fragment() {
    private lateinit var binding: ItemFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ItemFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contacts.adapter = ContactsAdapter(contacts)
        binding.contacts.layoutManager = LinearLayoutManager(context)
    }
}

class ToDoFragment() : Fragment() {
    private lateinit var binding: ToDoFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ToDoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}

