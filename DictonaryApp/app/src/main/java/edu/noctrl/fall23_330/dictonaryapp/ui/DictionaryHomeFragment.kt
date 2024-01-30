package edu.noctrl.fall23_330.dictonaryapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.noctrl.fall23_330.dictonaryapp.DictionaryApplication
import edu.noctrl.fall23_330.dictonaryapp.R
import edu.noctrl.fall23_330.dictonaryapp.databinding.FragmentDictionaryHomeBinding
import edu.noctrl.fall23_330.dictonaryapp.viewmodel.DictionaryViewModel
import edu.noctrl.fall23_330.dictonaryapp.viewmodel.DictionaryViewModelFactory
import edu.noctrl.fall23_330.dictonaryapp.viewmodel.DictionaryViewViewModel


class DictionaryHomeFragment : Fragment() {

    private var _binding: FragmentDictionaryHomeBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: DictionaryViewViewModel by activityViewModels()
    private val wordViewModel: DictionaryViewModel by viewModels {
        DictionaryViewModelFactory((requireActivity().application as DictionaryApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDictionaryHomeBinding.inflate(inflater,container,false)


        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.dictionaryHomeFragment = this
        val adapter = DictionaryHomeAdapter(DictionaryHomeListener { word ->
            sharedViewModel.setWord(word)
            Log.d("DictionaryHome word obj",word.imageFileName.toString())
            //navigate to definition
            findNavController().navigate(R.id.action_dictionaryHomeFragment_to_wordDefinitionFragment)
        })
        binding.recyclerView.adapter = adapter


        wordViewModel.allWords.observe(viewLifecycleOwner) { words ->
            // Update the cached copy of the words in the adapter.
            Log.d("Tag", "Number of words: ${words.size}")
            words.let { adapter.submitList(it) }
        }


        binding.activeButton.setOnClickListener {
            Log.d("Activebutton", "clicked")
            wordViewModel.activeWords.observe(viewLifecycleOwner) { words ->
                // Update the cached copy of the words in the adapter.
                words.let { adapter.submitList(it) }
                Log.d("Activebutton", "after adapter")
            }
        }

        binding.inactiveButton.setOnClickListener {
            Log.d("Inactivebutton", "clicked")
            wordViewModel.inactiveWords.observe(viewLifecycleOwner) { words ->
                // Update the cached copy of the words in the adapter.
                words.let { adapter.submitList(it) }
            }
        }

        binding.showAllButton.setOnClickListener {
            Log.d("ShowAllbutton", "clicked")
            wordViewModel.allWords.observe(viewLifecycleOwner) { words ->
                // Update the cached copy of the words in the adapter.
                words.let { adapter.submitList(it) }
            }
        }
    }

    fun goToSearchWord(){
        Log.d("TAG", "HERE11")
        // Navigate to the next destination to search a word
        findNavController().navigate(R.id.action_dictionaryHomeFragment_to_wordSearchFragment)
        //findNavController().navigate(R.id.action_dictionaryHomeFragment_to_wordSearchFragment)
        Log.d("TAG", "HERE22")
    }

    fun showActiveWords(){

    }
    fun showInactiveWords(){

    }

    fun showAllWords(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}