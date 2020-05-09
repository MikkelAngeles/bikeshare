package mhel.itu.moapd.bikeshare.viewmodel.holder
import android.content.Intent
import android.graphics.Color
import android.location.Location
import mhel.itu.moapd.bikeshare.R
import mhel.itu.moapd.bikeshare.model.entity.Bike

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mhel.itu.moapd.bikeshare.lib.ConversionManager.formatCurrencyToDkk
import mhel.itu.moapd.bikeshare.lib.ImageManager
import java.util.*

class BikeHolder (
    inflater : LayoutInflater,
    parent : ViewGroup
) : RecyclerView.ViewHolder(inflater.inflate(R.layout.bike_list_element, parent, false)) {

    private var name:           TextView    = itemView.findViewById(R.id.bikeName)
    private var type :          TextView    = itemView.findViewById(R.id.bikeType)
    private var location:       TextView    = itemView.findViewById(R.id.bikeLocation)
    private var rate:           TextView    = itemView.findViewById(R.id.bikeListPriceLabel)
    private var availability:   TextView    = itemView.findViewById(R.id.bikeAvailability)
    private var image:          ImageView   = itemView.findViewById(R.id.bikeImage)
    private var rentBtn:        Button      = itemView.findViewById(R.id.rentBikeButton)

    fun bind(bike: Bike) {
        name.text       = bike.name
        type.text       = bike.type
        location.text   = bike.location//"${Random().nextInt(10000)} m" //Random distance just for fun. Alternatively use GpsHandler to calculate distance between two locations. Seems overkill though.
        rate.text       = "${formatCurrencyToDkk(bike.rate?:0.0f)} / hour"

        availability.text = if(bike.isAvailable) "Available" else "Occupied";
        availability.setTextColor(if(bike.isAvailable) Color.rgb(67, 160, 71) else Color.rgb(229, 57, 53))

        if(!bike.isAvailable) rentBtn.setTextColor(Color.GRAY);

        //Handle edge cases where image is null
        if(bike.image != null) {
            val bitmap = ImageManager.byteArrayToBitmap(bike.image!!)
            if (bitmap != null) image.setImageBitmap(bitmap)
        }
    }
}