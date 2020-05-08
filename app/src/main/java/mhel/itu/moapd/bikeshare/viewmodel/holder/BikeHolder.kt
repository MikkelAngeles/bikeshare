package mhel.itu.moapd.bikeshare.viewmodel.holder
import android.graphics.Color
import mhel.itu.moapd.bikeshare.R
import mhel.itu.moapd.bikeshare.model.entity.Bike

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BikeHolder (
    inflater : LayoutInflater,
    parent : ViewGroup
) : RecyclerView.ViewHolder(inflater.inflate(R.layout.bike_list_element, parent, false)) {

    private var name:           TextView    = itemView.findViewById(R.id.bikeName)
    private var type :          TextView    = itemView.findViewById(R.id.bikeType)
    private var location:       TextView    = itemView.findViewById(R.id.bikeLocation)
    private var rate:           TextView    = itemView.findViewById(R.id.bikeRate)
    private var availability:   TextView    = itemView.findViewById(R.id.bikeAvailability)
    private var image:          ImageView   = itemView.findViewById(R.id.bikeImage)

    fun bind(bike: Bike) {
        name.text       = bike.name
        type.text       = "Type: ${bike.type}"
        location.text   = bike.location
        rate.text       = "${bike.rate.toString()} Dkk/hour"

        availability.text = if(bike.isAvailable) "Available" else "Occupied";
        availability.setTextColor(if(bike.isAvailable) Color.GREEN else Color.RED)

        //image.setImageBitmap(PictureUtils.byteArrayToBitmap(bike.picture!!))
    }
}