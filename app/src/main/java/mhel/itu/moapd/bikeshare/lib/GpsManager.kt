package mhel.itu.moapd.bikeshare.lib

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.io.IOException
import java.util.*

class GpsManager(val activity: FragmentActivity) : LocationListener {

    var locationManager: LocationManager? = this.activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
    var lastLocation: Location? = null
    var currentLocation: Location? = null

    fun estimateDistance(loc : Location) : String {
        val myPos = requestLocationUpdates() ?: return "Unknown distance";
        val tmp = FloatArray(3);
        Location.distanceBetween(myPos.longitude, myPos.latitude, loc.longitude, loc.latitude, tmp)
        val dist = tmp[0];
        return dist.toString();
    }

    fun getNearestAddress() : String {
        val rs = requestLocationUpdates() ?: return "Unknown"
        return getAddress(rs.longitude, rs.latitude);
    }

    fun getAddress(longitude: Double, latitude: Double): String {
        val geocoder = Geocoder(activity, Locale.getDefault())
        val stringBuilder = StringBuilder()
        try {
            val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses.isNotEmpty()) {
                val address: Address = addresses[0]
                stringBuilder.apply{
                    append(address.getAddressLine(0)).append("\n")
                }
            } else return "No address found"
        } catch (ex: IOException) { ex.printStackTrace() }
        return stringBuilder.toString()
    }

    fun requestLocationUpdates(): Location? {
        if (ContextCompat.checkSelfPermission(this.activity, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            try {
                locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 5f, this)
                val location = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                this.lastLocation = this.currentLocation
                this.currentLocation = location

            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        return this.currentLocation
    }

    override fun onLocationChanged(location: Location?) {}

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {}

    companion object {
        fun locationToString(loc : Location?) : String {
            return if (loc != null)
                "${loc.latitude}, ${loc.longitude}"
            else
                "Location unknown"
        }
    }
}