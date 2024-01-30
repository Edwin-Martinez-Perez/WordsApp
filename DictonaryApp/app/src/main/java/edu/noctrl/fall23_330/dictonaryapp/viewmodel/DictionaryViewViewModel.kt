package edu.noctrl.fall23_330.dictonaryapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.noctrl.fall23_330.dictonaryapp.database.dictionary.Word
import edu.noctrl.fall23_330.dictonaryapp.network.DictionaryApi
import edu.noctrl.fall23_330.dictonaryapp.network.parseJsonStringToListOfWords
import edu.noctrl.fall23_330.dictonaryapp.network.parseJsonToWord
import kotlinx.coroutines.launch

enum class DictionaryApiStatus {LOADING, ERROR, DONE}
private const val BASE_IMAGE_URL = "https://www.merriam-webster.com/assets/mw/static/art/dict/"

class DictionaryViewViewModel: ViewModel() {
    // TODO: Create properties to represent MutableLiveData and LiveData for the API status
    private val _apiStatus = MutableLiveData<DictionaryApiStatus>()
    val apiStatus: LiveData<DictionaryApiStatus> = _apiStatus

    // Create properties to represent MutableLiveData and LiveData for a list of amphibian objects
    private val _words = MutableLiveData<List<String>>()
    val words: LiveData<List<String>> = _words

    // Create properties to represent MutableLiveData and LiveData for a single amphibian object.
    //  This will be used to display the details of an amphibian when a list item is clicked
    private val _word = MutableLiveData<Word>()
    val word: LiveData<Word> = _word


    //TODO necessary? or no
    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    init {
        resetSelectedWord()
        resetWordList()
    }

    fun getWordResponse(searchWord: String) {

        viewModelScope.launch {
            _apiStatus.value = DictionaryApiStatus.LOADING
            try {
                val response = DictionaryApi.retrofitService.getWord(searchWord)
                val jsonString = response.body().toString()
//                var searchedWords = mutableListOf<String>()
                if (jsonString.startsWith("[{")) {
                    // call your parseJsonToWord function
//                    searchedWords.add(parseJsonToWord(searchWord, jsonString).id)
                    _word.value = parseJsonToWord(searchWord, jsonString)
                    _words.value = listOf(word.value?.id.toString())
                    Log.d("ViewViewModel word",word.value?.id.toString())
                    Log.d("ViewViewModel word",word.value?.imageFileName.toString())
                    Log.d("ViewViewModel word",word.value?.shortdefs.toString())
                    Log.d("ViewViewModel word",word.value?.shortDefCount.toString())

                } //else if (jsonString.startsWith("[\"")) {
                    else if (jsonString.startsWith("[")){
                    Log.d("ViewViewModel", jsonString.toString())
                    var parsedWords = parseJsonStringToListOfWords(jsonString)
                    if (parsedWords.isEmpty()) {
                        parsedWords = parsedWords.toMutableList()
                        parsedWords.add("No matching words found!")
                    } else if (jsonString.startsWith("[]")) {
                        Log.d("ViewViewModel","no matching word")
                        parsedWords = listOf("No matching words found!")
                    }
                    _words.value = parsedWords
                    Log.d("ViewViewModel words",words.value.toString())
//                    for (i in parsedWords.indices) {
//                        searchedWords.add(i, parsedWords[i])
//                    }
                }
                _apiStatus.value = DictionaryApiStatus.DONE
            } catch (e: Exception) {
                _apiStatus.value = DictionaryApiStatus.ERROR
                _words.value = listOf()
            }

        }
    }

    fun resetWordList(){
        _words.value = emptyList()
    }

    fun resetSelectedWord(){
        _word.value = Word("", "",0,"", false)
    }

    fun setStatus(status: Boolean){
        _status.value = status
    }

    fun getWord() : Word{
        return word.value!!
    }

    fun setWord(word: Word){
        _word.value = word
    }

    fun onWordClicked(word: String){
        getWordResponse(word)
    }

    fun getURL(): String{
        return BASE_IMAGE_URL
    }

}
