package com.example.firstapponkotlin.ui.note

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory
import com.example.firstapponkotlin.R
import com.example.firstapponkotlin.data.Note
import com.example.firstapponkotlin.data.mapToColor
import com.example.firstapponkotlin.databinding.NoteFragmentBinding
import com.example.firstapponkotlin.presentation.NoteViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class NoteFragment : Fragment(R.layout.note_fragment) {

    private var _binding: NoteFragmentBinding? = null
    private val binding get() = _binding!!

    private val note: Note? by lazy(LazyThreadSafetyMode.NONE) { arguments?.getParcelable(NOTE_KEY) }

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, object : Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NoteViewModel(note) as T
            }
        })[NoteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NoteFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.note?.let {
            binding.titleEt.setText(it.title)
            binding.bodyEt.setText(it.note)
            binding.root.setBackgroundColor(it.color.mapToColor(binding.root.context))
        }

        viewModel.showError().observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "Error while saving note!", Toast.LENGTH_LONG).show()
        }

        binding.titleEt.addTextChangedListener {
            viewModel.updateTitle(it?.toString() ?:"")
        }

        binding.bodyEt.addTextChangedListener {
            viewModel.updateNote(it?.toString() ?:"")
        }

        binding.buttonSaveAndExit.setOnClickListener {
            viewModel.saveNote()
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {

        const val NOTE_KEY = "NOTE"

        fun create(note: Note? = null): NoteFragment {
            val fragment = NoteFragment()
            val arguments = Bundle()
            arguments.putParcelable(NOTE_KEY, note)
            fragment.arguments = arguments
            return fragment
        }
    }
}
