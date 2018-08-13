package com.funrisestudio.avengers.app.avengers

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.funrisestudio.avengers.R
import com.funrisestudio.avengers.app.view.AvengerView
import com.funrisestudio.avengers.core.Navigator
import com.funrisestudio.avengers.core.extensions.inflate
import com.funrisestudio.avengers.core.extensions.loadFromUrl
import com.funrisestudio.avengers.core.extensions.toDp
import kotlinx.android.synthetic.main.item_card_avenger.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class AvengersAdapter @Inject constructor(): RecyclerView.Adapter<AvengersAdapter.ViewHolder> () {

    internal var collection: List<AvengerView> by Delegates.observable(emptyList()) {
        _, _, _ -> notifyDataSetChanged()
    }

    var clickListener: (AvengerView, Navigator.Extras) -> Unit = { _,_ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(parent.inflate(R.layout.item_card_avenger))

    override fun getItemCount(): Int = collection.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(collection[position], clickListener)

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind (avengerItem: AvengerView, itemClick: (AvengerView, Navigator.Extras) -> Unit) {
            itemView.tvAvengerName.text = avengerItem.name
            itemView.tvAvengerAlias.text = avengerItem.alias
            itemView.ivAvenger.loadFromUrl(avengerItem.image)
            itemView.layoutAvenger.setOnClickListener {
                itemClick.invoke(avengerItem, Navigator.Extras(itemView.ivAvenger))
            }
        }
    }

}