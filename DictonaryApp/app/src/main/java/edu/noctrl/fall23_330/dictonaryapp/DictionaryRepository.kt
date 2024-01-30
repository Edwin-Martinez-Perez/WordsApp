package edu.noctrl.fall23_330.dictonaryapp

import androidx.annotation.WorkerThread
import edu.noctrl.fall23_330.dictonaryapp.database.dictionary.Word
import edu.noctrl.fall23_330.dictonaryapp.database.dictionary.WordDao
import kotlinx.coroutines.flow.Flow

class DictionaryRepository(private val wordDao: WordDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords: Flow<List<Word>> = wordDao.getAll()

    val activeWords: Flow<List<Word>> = wordDao.getActive()

    val inactiveWords: Flow<List<Word>> = wordDao.getInactive()

    fun checkWordCount(word: String): Flow<Int>{
        return wordDao.checkWordExists(word)
    }

    fun activate(word: String){
        wordDao.activate(word)
    }

    fun deactivate(word: String){
        wordDao.deactivate(word)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}