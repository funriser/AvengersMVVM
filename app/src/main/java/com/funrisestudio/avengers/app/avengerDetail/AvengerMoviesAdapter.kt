package com.funrisestudio.avengers.app.avengerDetail

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.funrisestudio.avengers.R
import com.funrisestudio.avengers.app.view.AvengerMovieView
import com.funrisestudio.avengers.core.extensions.inflate
import com.funrisestudio.avengers.core.extensions.loadFromUrl
import kotlinx.android.synthetic.main.item_poster.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class AvengerMoviesAdapter @Inject constructor() : androidx.recyclerview.widget.RecyclerView.Adapter<AvengerMoviesAdapter.ViewHolder> () {

    var listMovies: List<AvengerMovieView> by Delegates.observable(emptyList()) {
        _,_,_ -> notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(parent.inflate(R.layout.item_poster))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binHolder(listMovies[position])
    }

    override fun getItemCount(): Int  = listMovies.size

    class ViewHolder (itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder (itemView) {

        fun binHolder (avengerMovieView: AvengerMovieView) {
            itemView.ivAvengerMovie.loadFromUrl(avengerMovieView.poster)
        }

    }

}