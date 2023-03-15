package com.example.firstapponkotlin.ui.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapponkotlin.R
import com.example.firstapponkotlin.adapter.NoteAdapter
import com.example.firstapponkotlin.data.Note
import com.example.firstapponkotlin.databinding.MainFragmentBinding
import com.example.firstapponkotlin.presentation.MainViewModel
import com.example.firstapponkotlin.presentation.MainViewState
import com.example.firstapponkotlin.ui.note.NoteFragment

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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NoteAdapter {
            navigateToNote(it)
        }
        binding.mainRecycler.adapter = adapter

        viewMode.observeViewState().observe(viewLifecycleOwner) {
            when (it) {
                is MainViewState.Value -> {
                    adapter.submitList(it.notes)
                }
                MainViewState.EMPTY -> Unit
            }
        }

        binding.fab.setOnClickListener {
            navigateToCreation()
        }

        binding.mainRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy<=0){
                    binding.fab.show()
                }else{
                    binding.fab.hide()
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun navigateToNote(note: Note) {
        (requireActivity() as MainActivity).navigateTo(NoteFragment.create(note))
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun navigateToCreation() {
        (requireActivity() as MainActivity).navigateTo(NoteFragment.create(null))
    }
}