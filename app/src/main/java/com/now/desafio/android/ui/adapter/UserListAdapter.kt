package com.now.desafio.android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.now.desafio.android.R
import com.now.desafio.android.data.entities.Artist
import com.now.desafio.android.ui.viewHolders.ArtistListItemViewHolder

class ArtistListAdapter : RecyclerView.Adapter<ArtistListItemViewHolder>() {
    var users = emptyList<Artist>()
        set(value) {
            val result = DiffUtil.calculateDiff(
                ArtistListDiffCallback(
                    field,
                    value
                )
            )
            result.dispatchUpdatesTo(this)
            field = value
        }

    var eventClick: ((artist: Artist) -> Unit)? = null
    var eventLongClick: ((artist: Artist) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistListItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)

        return ArtistListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistListItemViewHolder, position: Int) {
        holder.bind(users[position], eventClick, eventLongClick)
    }

    override fun getItemCount(): Int = users.size

}