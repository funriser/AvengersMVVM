package com.funrisestudio.avengers.app.avengers

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.funrisestudio.avengers.R
import com.funrisestudio.avengers.core.extensions.inflate
import com.funrisestudio.avengers.domain.entity.Avenger
import kotlinx.android.synthetic.main.item_card_avenger.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class AvengersAdapter @Inject constructor(): RecyclerView.Adapter<AvengersAdapter.ViewHolder> () {

    internal var collection: List<Avenger> by Delegates.observable(emptyList()) {
        _, _, _ -> notifyDataSetChanged()
    }

    var clickListener: (Avenger) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(parent.inflate(R.layout.item_card_avenger))

    override fun getItemCount(): Int = collection.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(collection[position], clickListener)

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind (avengerItem: Avenger, itemClick: (Avenger) -> Unit) {
            itemView.tvAvengerName.text = avengerItem.name
            itemView.tvAvengerAlias.text = avengerItem.alias
            itemView.layoutAvenger.setOnClickListener { itemClick.invoke(avengerItem) }
        }

    }

}