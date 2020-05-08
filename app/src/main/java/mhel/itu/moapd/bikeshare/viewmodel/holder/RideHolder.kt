package mhel.itu.moapd.bikeshare.viewmodel.holder

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mhel.itu.moapd.bikeshare.R
import mhel.itu.moapd.bikeshare.lib.ConversionManager
import mhel.itu.moapd.bikeshare.lib.ImageManager
import mhel.itu.moapd.bikeshare.model.entity.Ride

class RideHolder (
    inflater : LayoutInflater,
    parent : ViewGroup
) : RecyclerView.ViewHolder(inflater.inflate(R.layout.ride_list_element, parent, false)) {

    private var name:       TextView = itemView.findViewById(R.id.rideName)
    private var type :      TextView = itemView.findViewById(R.id.rideType)
    private var rate:       TextView = itemView.findViewById(R.id.rideListPriceLabel)
    private var time :      TextView = itemView.findViewById(R.id.rideListTime)
    private var image:      ImageView = itemView.findViewById(R.id.rideListImage)
    private var from:       TextView = itemView.findViewById(R.id.rideListFrom)
    private var to:         TextView = itemView.findViewById(R.id.rideListTo)

    fun bind(ride: Ride) {

        if(ride.endTime == null) {
            rate.text = "Current"
        }
        else rate.text  = "${ConversionManager.formatCurrencyToDkk(ride.price ?: 0.0f)}"
        rate.setTextColor(if(ride.endTime != null) Color.rgb(67, 160, 71) else Color.rgb(229, 57, 53))

        name.text       = ride.name
        type.text       = ride.type
        time.text       = ConversionManager.msToTimeString(ride.time?:0)
        from.text       = ride.startLocation
        to.text         = ride.endLocation?:"Ride still active"

        //Handle edge cases where image is null
        if(ride.image != null) {
            val bitmap = ImageManager.byteArrayToBitmap(ride.image!!)
            if (bitmap != null) image.setImageBitmap(bitmap)
        }
    }
}