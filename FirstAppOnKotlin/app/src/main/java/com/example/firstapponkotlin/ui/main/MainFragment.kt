package com.example.firstapponkotlin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.firstapponkotlin.R
import com.example.firstapponkotlin.adapter.NoteAdapter
import com.example.firstapponkotlin.databinding.ActivityMainBinding
import com.example.firstapponkotlin.databinding.MainFragmentBinding
import com.example.firstapponkotlin.presentation.MainViewModel
import com.example.firstapponkotlin.presentation.MainViewState

class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewMode by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NoteAdapter()
        binding.mainRecycler.adapter = adapter

        viewMode.observeViewState().observe(viewLifecycleOwner) {
            when (it) {
                is MainViewState.Value -> {
                    adapter.submitList(it.notes)
                }
                MainViewState.EMPTY -> Unit
            }
        }

    }
}