package com.now.desafio.android.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.now.desafio.android.data.entities.Artist

class ArtistListDiffCallback(
    private val oldList: List<Artist>,
    private val newList: List<Artist>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }
}