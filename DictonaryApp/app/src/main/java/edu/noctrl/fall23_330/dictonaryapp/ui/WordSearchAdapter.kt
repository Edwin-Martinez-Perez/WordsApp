package edu.noctrl.fall23_330.dictonaryapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.noctrl.fall23_330.dictonaryapp.databinding.SearchWordItemBinding

class WordSearchAdapter(val clickListener: WordListener) :
    ListAdapter<String, WordSearchAdapter.WordViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
//        val view: View = LayoutInflater.from(parent.context)
//            .inflate(R.layout.search_word_item, parent, false)
        val layoutInflater = LayoutInflater.from(parent.context)
        //return WordViewHolder(view)

        return WordViewHolder(
            SearchWordItemBinding.inflate(layoutInflater,parent,false)
        )
//
//        val layoutInflater = LayoutInflater.from(parent.context)
//        return AmphibianViewHolder(
//            ListViewItemBinding.inflate(layoutInflater, parent, false)
//        )
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, clickListener)
    }

    class WordViewHolder(
        //itemView: View,
        var binding: SearchWordItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        //private val wordItemView: TextView = itemView.findViewById(R.id.search_result_text_view)

        fun bind(word: String?, clickListener: WordListener) {
            //Log.d("WordSearchAdapter", word!!)
//            val wordItemView: TextView = itemView.findViewById(R.id.search_result_text_view)
//            wordItemView.text = word

            binding.searchResultTextView.text = word

            binding.clickListener = clickListener
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                clickListener.onClick(word!!)
            }

        }


//        companion object {
//            fun create(parent: ViewGroup): WordViewHolder {
//                val view: View = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.search_word_item, parent, false)
//                return WordViewHolder(view)
//            }
//        }
    }

//    companion object {
//        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<String>() {
//            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
//                Log.d("WordSearchAdapter", "areitems the same")
//                return oldItem === newItem
//            }
//
//            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
//                Log.d("WordSearchAdapter", "are contents the same")
//                return oldItem == newItem
//            }
//        }
//    }

    class WordsComparator : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}


class WordListener(val clickListener: (word: String) -> Unit) {
    fun onClick(word: String) = clickListener(word)
}