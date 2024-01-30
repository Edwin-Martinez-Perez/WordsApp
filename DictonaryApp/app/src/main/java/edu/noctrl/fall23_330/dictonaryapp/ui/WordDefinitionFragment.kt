package edu.noctrl.fall23_330.dictonaryapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import coil.load
import edu.noctrl.fall23_330.dictonaryapp.DictionaryApplication
import edu.noctrl.fall23_330.dictonaryapp.databinding.FragmentWordDefinitionBinding
import edu.noctrl.fall23_330.dictonaryapp.viewmodel.DictionaryViewModel
import edu.noctrl.fall23_330.dictonaryapp.viewmodel.DictionaryViewModelFactory
import edu.noctrl.fall23_330.dictonaryapp.viewmodel.DictionaryViewViewModel


class WordDefinitionFragment : Fragment() {
    // Binding object instance corresponding to the fragment_summary.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var _binding: FragmentWordDefinitionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DictionaryViewViewModel by activityViewModels()

    private val wordViewModel: DictionaryViewModel by viewModels {
        DictionaryViewModelFactory((requireActivity().application as DictionaryApplication).repository)
    }


    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    private val sharedViewModel: DictionaryViewViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentWordDefinitionBinding.inflate(inflater)
        //binding = fragmentBinding
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            wordDefinitionFragment = this@WordDefinitionFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.word.observe(viewLifecycleOwner) { word ->

            binding.word.text = word.id
            binding.wordDefinitionDefinition.text = word.shortdefs
            binding.imageDefinition.load("${sharedViewModel.getURL()}${word.imageFileName}.gif")
        }

        binding.activationSwitch.setOnCheckedChangeListener{ buttonView, isChecked ->
            if (isChecked){
                //update status to true
                wordViewModel.activateWord(sharedViewModel.getWord().id)
            } else {
                //update status to false
                wordViewModel.deactivateWord(sharedViewModel.getWord().id)

            }

        }

    }

    override fun onResume(){
        super.onResume()
        sharedViewModel.word.observe(viewLifecycleOwner) {word ->
            if(word.active){
                binding.activationSwitch.text = "Active"
                binding.activationSwitch.isChecked = true
            }
            else{
                binding.activationSwitch.text = "Inactive"
                binding.activationSwitch.isChecked = false
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}