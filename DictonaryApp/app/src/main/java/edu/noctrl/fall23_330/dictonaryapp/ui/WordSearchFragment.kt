package edu.noctrl.fall23_330.dictonaryapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.noctrl.fall23_330.dictonaryapp.R
import edu.noctrl.fall23_330.dictonaryapp.databinding.FragmentWordSearchBinding
import edu.noctrl.fall23_330.dictonaryapp.viewmodel.DictionaryViewViewModel

class WordSearchFragment : Fragment() {
    private var _binding: FragmentWordSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DictionaryViewViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        //fragmentBinding = FragmentWordSearchBinding.inflate(inflater, container, false)
        Log.d("WordSearch","before inflate")
        _binding = FragmentWordSearchBinding.inflate(inflater, container, false)

        Log.d("WordSearch","after inflate")
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
            wordSearchFragment = this@WordSearchFragment
//            val adapter = WordSearchAdapter(WordListener { word ->
//                viewModel.getWordResponse(word)
//                // Navigate to the next destination to add a word
//                findNavController().navigate(R.id.action_wordSearchFragment_to_addWordFragment)
//            })
//            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

        }

//        val searchBttn = binding.searchButton view?.findViewById<FloatingActionButton>(R.id.search_button)
//        searchBttn?.setOnClickListener {
//            val editText: EditText? = view?.findViewById(R.id.searchEditText)
//            val typedText = binding.searchEditText.text.toString()
//            //val textTyped = editText?.text.toString()
//            viewModel.getWordResponse(typedText).observe(viewLifecycleOwner) { words ->
//                // Update the cached copy of the words in the adapter.
//                Log.d("WordSearch", "Number of words: ${words.size}")
//                words.let { adapter.submitList(it) }
//            }
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("Tag", "HERE")
        binding?.wordSearchFragment = this
        val adapter = WordSearchAdapter(WordListener { word ->
            viewModel.onWordClicked(word)
            // Navigate to the next destination to add a word
            findNavController().navigate(R.id.action_wordSearchFragment_to_addWordFragment)
            Log.d("WordSearch", "after findNav")
        })
        binding.recyclerView.adapter = adapter

        //click listener when search buttonn clicked
        binding.searchWordButton.setOnClickListener{
            showResult()
        }

        viewModel.word.observe(viewLifecycleOwner) { word ->
            //observer for word exact match



        }

        viewModel.words.observe(viewLifecycleOwner){ words ->
            //observer for words string list
            // change -> list of string that has suggested data is updated
            // show on recyclerview
            // when the recyclerview is clicked, do the getResponse again and update word

            // recyclerview.adapter = Adapter ( adapter.onclicklistener {searchWord -> viewModel.perform})

            Log.d("WordSearch", "before submitlist")
            words.let { adapter.submitList(it) }

            Log.d("WordSearch", "after submitlist")
            binding.recyclerView.visibility = View.VISIBLE

        }




    }

    override fun onResume() {
        super.onResume()
        viewModel.resetWordList()
    }

    fun showResult() {
        //TODO get word from user in EditText when entere gets hit on key board
        // if it is the list of words, show list of words suggested
        // if it has the exact match, show the one word with short defs
        Log.d("WordSearch", "before get response")

//        val searchBttn = binding.searchButton
//        searchBttn.setOnClickListener {
//            Log.d("WordSearch", "setOnclicklistner")
//            val typedText = binding.searchEditText.text.toString()
//            Log.d("WordSearch", "typed text: @{typedText}")
//            Log.d("WordSearch", typedText)
//            //val textTyped = editText?.text.toString()
//            viewModel.getWordResponse(typedText)
//            Log.d("WordSearch", "after getWordResponse")
//        }

        //viewModel.resetWordList()

        Log.d("WordSearch", "setOnclicklistner")
        val typedText = binding.searchEditText.text.toString()
        //val textTyped = editText?.text.toString()
        viewModel.getWordResponse(typedText)
        Log.d("WordSearch", "after getWordResponse")
        //viewModel.resetWordList()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

//class WordSearchFragment : Fragment() {
//
//    // Binding object instance corresponding to the fragment_word_search.xml layout
//    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
//    // when the view hierarchy is attached to the fragment.
//    private var binding: FragmentWordSearchBinding? = null
//
//    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
//    private val viewModel: DictionaryViewViewModel by activityViewModels()
//
//    private val adapter by lazy { WordSearchAdapter() }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View? {
//        Log.d("Tag", "HERE1")
//        //val fragmentBinding = FragmentWordSearchBinding.inflate(inflater, container, false)
//        //val fragmentBinding = FragmentWordDefinitionBinding.inflate(inflater)
//        val fragmentBinding = FragmentWordSearchBinding.inflate(inflater)
//
//
//
//        return fragmentBinding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        Log.d("Tag", "HERE")
//
////        binding?.apply {
////            // Specify the fragment as the lifecycle owner
////            lifecycleOwner = viewLifecycleOwner
////
////            // Assign the view model to a property in the binding class
////            viewModel = sharedViewModel
////
////            // Assign the fragment
////            wordSearchFragment = this@WordSearchFragment
////
////            //val recyclerView: RecyclerView = yourRootView.findViewById(R.id.yourRecyclerView)
////
////            /*
////            val fab = findViewById<FloatingActionButton>(R.id.fab)
////        fab.setOnClickListener {
////            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
////            startActivityForResult(intent, newWordActivityRequestCode)*/
////        }
//
////
////            recyclerView.adapter = adapter
////            recyclerView.layoutManager = LinearLayoutManager(requireContext())
////            Log.d("Tag", "HERE")
////        }
////        val searchBttn = view.findViewById<FloatingActionButton>(R.id.search_button)
////        searchBttn.setOnClickListener {
////            val editText: EditText? = view?.findViewById(R.id.searchEditText) // Replace with your EditText's ID
////            val textTyped = editText?.text.toString()
////            showResult(textTyped)
////        }
////
////        sharedViewModel.words.observe(viewLifecycleOwner) { words ->
////            if (words.isNotEmpty()) {
////                // Submit the list to the adapter when it's available or updated
////                //adapter.submitList(valuesList)
////                sharedViewModel?.words?.observe(viewLifecycleOwner) { words ->
////                    words?.let {
////                        // Submit the list to the adapter when LiveData emits an updated value
////                        adapter.submitList(it)
////                    }
////                }
////
////            }
////        }
//
//    }
////
////    //when enter button hit, or simultaneously while user is typing, talk to API, and get result, and show the result on the same page
////    fun showResult(word: String) {
////        //TODO get word from user in EditText when entere gets hit on key board
////        // if it is the list of words, show list of words suggested
////        // if it has the exact match, show the one word with short defs
////        sharedViewModel.getWordResponse(word)
////
////    }
////
////
////
////    fun goToAddWord() {
////        //TODO in viewmodel, save word(id), defs, image, etc to liveData
////
////
////        // Navigate to the next destination to add a word
////        findNavController().navigate(R.id.action_wordSearchFragment_to_addWordFragment)
////
////    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding = null
//    }
//
//}