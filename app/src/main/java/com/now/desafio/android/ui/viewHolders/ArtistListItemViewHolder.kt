package com.now.desafio.android.ui.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.now.desafio.android.R
import com.now.desafio.android.data.entities.Artist
import com.now.desafio.android.util.ext.setImageFromUrl
import kotlinx.android.synthetic.main.list_item_user.view.*

class ArtistListItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    fun bind(artist: Artist, eventClick: ((artist: Artist) -> Unit)?, eventLongClick: ((artist: Artist) -> Unit)?) {
        itemView.name.text = artist.name
        itemView.username.text = artist.username
        itemView.progressBar.visibility = View.VISIBLE

        itemView.ic_star.visibility = if (artist.favorited) View.VISIBLE else View.GONE
        itemView.picture.setImageFromUrl(
            artist.img,
            R.drawable.ic_round_account_circle,
            callbackFinish = {
                itemView.progressBar.visibility = View.GONE
            })

        itemView.setOnClickListener { eventClick?.invoke(artist) }

        itemView.setOnLongClickListener {
            eventLongClick?.invoke(artist)
            return@setOnLongClickListener true
        }
    }
}