package edu.noctrl.fall23_330.dictonaryapp.ui


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.noctrl.fall23_330.dictonaryapp.database.dictionary.Word
import edu.noctrl.fall23_330.dictonaryapp.databinding.HomeWordItemBinding


class DictionaryHomeAdapter(val clickListener: DictionaryHomeListener) :
    ListAdapter<Word, DictionaryHomeAdapter.DictionaryViewHolder>(WordsComparator()){

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
//        return DictionaryViewHolder.create(parent)
//    }

//    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
//        val current = getItem(position)
//        holder.bind(
//            current.id,
//            current.shortDefCount,
//            current.active,
//            current.imageFileName
//        )
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : DictionaryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //return WordViewHolder(view)

        return DictionaryViewHolder(
            HomeWordItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, clickListener)
    }


    class DictionaryViewHolder(var binding: HomeWordItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
//        private val wordItemView: TextView = itemView.findViewById()

//        fun bind(text: String?) {
//            wordItemView.text = text
//        }
        fun bind(
            word: Word,
            clickListener: DictionaryHomeListener
        )  {
//            val textViewWord: TextView = itemView.findViewById(R.id.word_text_view)
//            val textViewNumDefinitions: TextView = itemView.findViewById(R.id.def_count_text_view)
//            val textViewStatus: TextView = itemView.findViewById(R.id.word_status_text_view)
//            val textViewImage: TextView = itemView.findViewById(R.id.img_availability_text_view)
//
//            textViewWord.text = word
//            textViewNumDefinitions.text = def_count.toString()
//            if (status) {textViewStatus.text = "Active"}
//            else {textViewStatus.text = "Incative"}
//
//            if(image_file == "") {
//                textViewImage.text = "Unavailable"
//            } else {
//                textViewImage.text = "Available"
//            }
            binding.word = word
            binding.clickListener = clickListener
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                clickListener.onClick(word)
            }
            Log.d("DictionaryHomeAdapt", word.id.toString())
            Log.d("DictionaryHomeAdapt", word.shortdefs.toString())
            Log.d("DictionaryHomeAdapt image", word.imageFileName.toString())


        }


//        companion object {
//            fun create(parent: ViewGroup): DictionaryViewHolder {
//                val view: View = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.home_word_item, parent, false)
//                return DictionaryViewHolder(view)
//            }
//        }
    }


    class WordsComparator : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.id == newItem.id
        }
    }


}

class DictionaryHomeListener(val clickListener: (word: Word) -> Unit) {
    fun onClick(word: Word) = clickListener(word)
}