package com.products.testapp.add_note.presentation.show_note

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.products.testapp.add_note.domain.model.NotesModel
import com.products.testapp.databinding.ItemNotesBinding

class NoteAdapter(private val listener: OnClick) : ListAdapter<NotesModel, RecyclerView.ViewHolder>(NoteDiff) {

    interface OnClick{
        fun updateUser(notesModel: NotesModel)
    }
    var mColors = arrayOf("#F99B7D", "#FFC26F", "#F6FFA6", "#FEFF86" ,"#B0DAFF")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NoteViewHolder).bind(getItem(position))
        holder.itemView.setBackgroundColor(Color.parseColor(mColors[position % 5]))
        val lp = holder.itemView.layoutParams
        val layOutparam = StaggeredGridLayoutManager.LayoutParams(holder.itemView.layoutParams)
//        if (lp != null  && (holder.getLayoutPosition() == 2 || holder.getLayoutPosition() == 13)){
        if (lp != null  && ((holder.getLayoutPosition() + 1) % 3) == 0){
            val p = lp as StaggeredGridLayoutManager.LayoutParams
            p.isFullSpan = true
        }
    }

    inner class NoteViewHolder(private val binding: ItemNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(notesModel: NotesModel) {
            binding.apply {
                title.text = notesModel.title
                content.text = notesModel.contentNotes

                root.setOnClickListener {
                    listener.updateUser(notesModel)
                }
            }
        }
    }
}
object NoteDiff : DiffUtil.ItemCallback<NotesModel>() {
    override fun areItemsTheSame(oldItem: NotesModel, newItem: NotesModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NotesModel, newItem: NotesModel): Boolean {
        return oldItem == newItem
    }

}
