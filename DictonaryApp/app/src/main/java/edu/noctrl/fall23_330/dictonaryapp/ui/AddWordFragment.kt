package edu.noctrl.fall23_330.dictonaryapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import edu.noctrl.fall23_330.dictonaryapp.DictionaryApplication
import edu.noctrl.fall23_330.dictonaryapp.R
import edu.noctrl.fall23_330.dictonaryapp.databinding.FragmentAddWordBinding
import edu.noctrl.fall23_330.dictonaryapp.viewmodel.DictionaryViewModel
import edu.noctrl.fall23_330.dictonaryapp.viewmodel.DictionaryViewModelFactory
import edu.noctrl.fall23_330.dictonaryapp.viewmodel.DictionaryViewViewModel


class AddWordFragment : Fragment() {
    // Binding object instance corresponding to the fragment_pickup.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var _binding: FragmentAddWordBinding? = null
    private val binding get() = _binding!!

    private val sharedviewModel: DictionaryViewViewModel by activityViewModels()

    //private val dbViewModel: DictionaryViewModel by activityViewModels()
//    private val dbViewModel: DictionaryViewModel by activityViewModels()
//    private val wordViewModel: DictionaryViewModel by viewModels {
//        DictionaryViewModelFactory((requireActivity().application as DictionaryApplication).repository)
//    }
    private val wordViewModel: DictionaryViewModel by viewModels {
        DictionaryViewModelFactory((requireActivity().application as DictionaryApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAddWordBinding.inflate(inflater, container, false)
//        binding?.apply {
//            lifecycleOwner = viewLifecycleOwner
//            viewModel = viewModel
//            addWordFragment = this@AddWordFragment
//        }
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedviewModel
            addWordFragment = this@AddWordFragment
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding?.apply {
//            lifecycleOwner = viewLifecycleOwner
//            viewModel = sharedViewModel
//            addWordFragment = this@AddWordFragment
//        }
        sharedviewModel.word.observe(viewLifecycleOwner) { word ->
            //observer for word exact match
            binding.foundWordTextView.text = word.id
            binding.wordDefinitionAdd.text = word.shortdefs
            binding.imageAdd.load("${sharedviewModel.getURL()}${word.imageFileName}.gif")
        }

        binding.addButton.setOnClickListener{
            addWordToDatabase()
        }
    }

    fun addWordToDatabase() {
        //TODO
//        var wordToAdd = sharedviewModel.getWord().id
//        Log.d("word exist","${wordViewModel.checkWordExist(wordToAdd).value}")
//        if (wordViewModel.checkWordExist(wordToAdd).value == 0) {
//            //add word shown (saved in LiveData (and object?)) to the database
//            wordViewModel.insertWord(sharedviewModel.getWord())
//            // Navigate back to the [StartFragment] to start over
//            findNavController().navigate(R.id.action_addWordFragment_to_dictionaryHomeFragment)
//        }else{
//            Toast.makeText(requireContext(),"The word already exists.",Toast.LENGTH_SHORT).show()
//        }
        //add word shown (saved in LiveData (and object?)) to the database
        wordViewModel.insertWord(sharedviewModel.getWord())
        // Navigate back to the [StartFragment] to start over
        findNavController().navigate(R.id.action_addWordFragment_to_dictionaryHomeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}