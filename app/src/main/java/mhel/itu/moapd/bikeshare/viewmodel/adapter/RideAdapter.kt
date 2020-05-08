package mhel.itu.moapd.bikeshare.viewmodel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.bike_list_element.view.*
import mhel.itu.moapd.bikeshare.model.entity.Bike
import mhel.itu.moapd.bikeshare.model.entity.Ride
import mhel.itu.moapd.bikeshare.viewmodel.holder.BikeHolder
import mhel.itu.moapd.bikeshare.viewmodel.holder.RideHolder

class RideAdapter (
    private val rides: List<Ride>
) : RecyclerView.Adapter<RideHolder>() {

    override fun onCreateViewHolder(p: ViewGroup, v: Int): RideHolder {
        val inflater = LayoutInflater.from(p.context)
        return RideHolder(inflater, p)
    }

    override fun getItemCount(): Int {
        return this.rides.size
    }

    override fun onBindViewHolder(holder: RideHolder, i: Int) {
        holder.bind(this.rides[i])
    }
}