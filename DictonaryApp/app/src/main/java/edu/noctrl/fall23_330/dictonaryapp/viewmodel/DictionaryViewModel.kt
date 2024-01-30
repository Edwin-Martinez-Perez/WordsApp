package edu.noctrl.fall23_330.dictonaryapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import edu.noctrl.fall23_330.dictonaryapp.DictionaryRepository
import edu.noctrl.fall23_330.dictonaryapp.database.dictionary.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

//class DictionaryViewModel(private val dictionaryDao: WordDao): ViewModel() {
//    fun fullWords(): Flow<List<Word>> = dictionaryDao.getAll()
//
//    fun activeWords(): Flow<List<Word>> = dictionaryDao.getActive()
//
//    fun inActiveWords(): Flow<List<Word>> = dictionaryDao.getInactive()
//}

class DictionaryViewModel(private val repository: DictionaryRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    val activeWords: LiveData<List<Word>> = repository.activeWords.asLiveData()

    val inactiveWords: LiveData<List<Word>> = repository.inactiveWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertWord(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }

    fun activateWord(word: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.activate(word)
    }

    fun deactivateWord(word: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.deactivate(word)
    }

    fun checkWordExist(word: String) :LiveData<Int> {
        Log.d("check exist","${repository.checkWordCount(word).asLiveData().value}")
        return repository.checkWordCount(word).asLiveData()
    }



}


//class DictionaryViewModelFactory(
//    private val wordDao: WordDao
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(DictionaryViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return DictionaryViewModel(wordDao) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}

class DictionaryViewModelFactory(private val repository: DictionaryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DictionaryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DictionaryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
