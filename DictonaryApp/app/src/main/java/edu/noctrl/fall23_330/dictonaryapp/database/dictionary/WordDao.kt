package edu.noctrl.fall23_330.dictonaryapp.database.dictionary

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    //show all - both inactive and active
    @Query("SELECT * FROM dictionary_table ORDER BY word")
    fun getAll(): Flow<List<Word>>

    //active
    @Query("SELECT * FROM dictionary_table WHERE status = 1 ORDER BY word")
    fun getActive(): Flow<List<Word>>

    //inactive
    @Query("SELECT * FROM dictionary_table WHERE status = 0 ORDER BY word")
    fun getInactive(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(word: Word)

    @Query("DELETE FROM dictionary_table")
    fun deleteAll()

    @Query("UPDATE dictionary_table SET status = 1 WHERE word = :wordID")
    fun activate(wordID: String)

    @Query("UPDATE dictionary_table SET status = 0 WHERE word = :wordID")
    fun deactivate(wordID: String)

    @Query("SELECT COUNT(*) FROM dictionary_table WHERE word = :wordSearched")
    fun checkWordExists(wordSearched: String) : Flow<Int>

}
