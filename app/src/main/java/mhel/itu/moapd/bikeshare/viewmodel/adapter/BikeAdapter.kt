package mhel.itu.moapd.bikeshare.viewmodel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.bike_list_element.view.*
import mhel.itu.moapd.bikeshare.model.entity.Bike
import mhel.itu.moapd.bikeshare.viewmodel.holder.BikeHolder

class BikeAdapter(
    private val bikes: List<Bike>,
    val clickListener: (Bike) -> Unit
) : RecyclerView.Adapter<BikeHolder>() {

    override fun onCreateViewHolder(p: ViewGroup, v: Int): BikeHolder {
        val inflater = LayoutInflater.from(p.context)
        return BikeHolder(inflater, p)
    }

    override fun getItemCount(): Int {
        return this.bikes.size
    }

    override fun onBindViewHolder(holder: BikeHolder, i: Int) {
        holder.bind(this.bikes[i])
        holder.itemView.rentBikeButton.setOnClickListener { clickListener(this.bikes[i]) }
    }
}