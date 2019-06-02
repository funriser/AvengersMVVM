package com.funrisestudio.avengers.app.avengers

import android.view.View
import android.view.ViewGroup
import com.funrisestudio.avengers.R
import com.funrisestudio.avengers.app.view.AvengerView
import com.funrisestudio.avengers.core.extensions.inflate
import com.funrisestudio.avengers.core.extensions.loadFromUrl
import kotlinx.android.synthetic.main.item_card_avenger.view.*
import kotlin.properties.Delegates

class AvengersAdapter: androidx.recyclerview.widget.RecyclerView.Adapter<AvengersAdapter.ViewHolder> () {

    internal var collection: List<AvengerView> by Delegates.observable(emptyList()) {
        _, _, _ -> notifyDataSetChanged()
    }

    var clickListener: (AvengerView, View) -> Unit = { _,_ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(parent.inflate(R.layout.item_card_avenger))

    override fun getItemCount(): Int = collection.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(collection[position], clickListener)

    class ViewHolder (itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind (avengerItem: AvengerView, itemClick: (AvengerView, View) -> Unit) {
            itemView.tvAvengerName.text = avengerItem.name
            itemView.tvAvengerAlias.text = avengerItem.alias
            itemView.ivAvenger.loadFromUrl(avengerItem.image)
            val transitionName = itemView.context.getString(R.string.imageTransition) + adapterPosition
            itemView.ivAvenger.transitionName = transitionName
            itemView.layoutAvenger.setOnClickListener {
                itemClick.invoke(avengerItem, itemView.ivAvenger)
            }
        }
    }

}