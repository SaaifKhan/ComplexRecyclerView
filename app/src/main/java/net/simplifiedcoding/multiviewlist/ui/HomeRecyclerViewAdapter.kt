package net.simplifiedcoding.multiviewlist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.simplifiedcoding.multiviewlist.R
import net.simplifiedcoding.multiviewlist.databinding.ItemDirectorBinding
import net.simplifiedcoding.multiviewlist.databinding.ItemMovieBinding
import net.simplifiedcoding.multiviewlist.databinding.ItemTitleBinding
import java.lang.IllegalArgumentException

class HomeRecyclerViewAdapter : RecyclerView.Adapter<HomeRecyclerViewHolder>() {
    var items = listOf<HomeRecyclerViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemCLickListener : ((view:View,item:HomeRecyclerViewItem,position : Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        return when (viewType) {
            R.layout.item_title -> HomeRecyclerViewHolder.TitleViewHolder(
                ItemTitleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            R.layout.item_movie -> HomeRecyclerViewHolder.movieViewHolder(
                ItemMovieBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            R.layout.item_director -> HomeRecyclerViewHolder.directorViewHolder(
                ItemDirectorBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            else -> throw IllegalArgumentException("Invalid ViewType")
        }

    }

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        holder.itemCLickListener= itemCLickListener
        when (holder) {
            is HomeRecyclerViewHolder.TitleViewHolder -> holder.bind(items[position] as HomeRecyclerViewItem.Title)
            is HomeRecyclerViewHolder.directorViewHolder -> holder.bind(items[position] as HomeRecyclerViewItem.Director)
            is HomeRecyclerViewHolder.movieViewHolder -> holder.bind(items[position] as HomeRecyclerViewItem.Movie)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is HomeRecyclerViewItem.Director -> R.layout.item_director
            is HomeRecyclerViewItem.Movie -> R.layout.item_movie
            is HomeRecyclerViewItem.Title -> R.layout.item_title
        }
    }
}