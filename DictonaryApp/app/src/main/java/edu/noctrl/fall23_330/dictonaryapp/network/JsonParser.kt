package edu.noctrl.fall23_330.dictonaryapp.network

import edu.noctrl.fall23_330.dictonaryapp.database.dictionary.Word
import org.json.JSONArray


fun parseJsonStringToListOfWords(jsonStr: String): List<String> {
    val jsonArrayObj = JSONArray(jsonStr)
    val wordList = mutableListOf<String>()
    for(i in 0 until jsonArrayObj.length()) {
        wordList.add(i, jsonArrayObj.getString(i))
    }
    return wordList
}


//fun parseJsonToWord(wordFound: String, jsonStr: String) : Word {
//    // populate a Word object with values from the jsonString
//    // return the Word object
//    val jsonArrayObj = JSONArray(jsonStr)
//    val entry = jsonArrayObj.getJSONObject(0)
//    var imageId = ""
//    var shortdefs = mutableListOf<String>()
//    var shortdefCount = 0
//
//
//    val jsonObject = jsonArrayObj.getJSONObject(0)
//    val metaObject = jsonObject.getJSONObject("meta")
//
//    // Check if "id" is the word
//    if (metaObject.getString("id").split(":")[0] == wordFound || metaObject.getString("id") == wordFound) {
//        val artObject = jsonObject.optJSONObject("art")
//        //extract image file
//        if (artObject!= null && artObject.has("artid")) {
//            imageId = jsonObject.getJSONObject("art").getString("artid")
//        }
//
//        //extract shortdefs
//        val shortdefJsonArr = jsonObject.getJSONArray("shortdef")
//        for (j in 0 until shortdefJsonArr.length()){
//            shortdefs.add(j,shortdefJsonArr.getString(j))
//            shortdefCount++
//        }
//    }
//
//
//    return Word(wordFound, shortdefs.joinToString(separator = ";"), shortdefCount, imageId, false)
//}

fun parseJsonToWord(wordId: String, jsonStr: String) : Word {
    val json = JSONArray(jsonStr)
    // Gets the first entry in the array
    val entry = json.getJSONObject(0)
    val shortDefArr = entry.getJSONArray("shortdef")
    var image: String? = null
    var shortdefs = mutableListOf<String>()

    if (entry.has("art")) {
        image = entry.getJSONObject("art").getString("artid")
    }

    for (j in 0 until shortDefArr.length()){
            shortdefs.add(j,shortDefArr.getString(j))
    }

    val word = when(shortDefArr.length()){
        0 -> Word(wordId, "No definition available", 0, image)
        else -> Word(wordId, shortdefs.joinToString(separator = ";"), shortDefArr.length(), image)
    }

    return word
}

//fun parseJsonToWord(wordId: String, jsonStr: String) : Word {
//    val json = JSONArray(jsonStr)
//    // Gets the first entry in the array
//    val entry = json.getJSONObject(0)
//    val shortDef = entry.getJSONArray("shortdef")
//    var image: String? = null
//
//    if (entry.has("art")) {
//        image = entry.getJSONObject("art").getString("artid")
//    }
//
//    val word = when (shortDef.length()) {
//        0 -> Word(wordId, "No definition available")
//        1 -> Word(wordId, shortDef.getString(0), imageFileName = image)
//        2 -> Word(wordId, shortDef.getString(0), shortDef.getString(1), imageName = image)
//        else -> Word(
//            wordId, shortDef.getString(0),
//            shortDef.getString(1),
//            shortDef.getString(2),
//            imageFileName = image
//        )
//    }
//    return word
//}