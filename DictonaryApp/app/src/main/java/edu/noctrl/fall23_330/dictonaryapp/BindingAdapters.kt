package edu.noctrl.fall23_330.dictonaryapp

import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load


//@BindingAdapter("listData")
//fun bindRecyclerView(recyclerView: RecyclerView, data: LiveData<List<Word>>?) {
//
//    data?.observeForever { words ->
//        words?.let {
//            val adapter = recyclerView.adapter as DictionaryHomeAdapter
//            adapter.submitList(it)
//        }
//    }
//
////    val adapter = recyclerView.adapter as DicitonaryHomeAdapter
////    adapter.submitList(data)
//}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    Log.d("bindingadapter","bindImage+${imgUrl}")

    imgUrl?.let {
        Log.d("bindingadapter","imgURi")
        val imgUri = imgUrl.toUri().buildUpon().scheme("https://www.merriam-webster.com/assets/mw/static/art/dict/").build()
        Log.d("bindingadapter",imgUri.toString())
        imgView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}



