package edu.noctrl.fall23_330.dictonaryapp

import android.app.Application
import edu.noctrl.fall23_330.dictonaryapp.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class DictionaryApplication : Application(){
    val applicationScope = CoroutineScope(SupervisorJob())
    //val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { DictionaryRepository(database.wordDao()) }
}