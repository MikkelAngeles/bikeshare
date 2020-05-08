package mhel.itu.moapd.bikeshare.viewmodel.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_bike_list.*
import mhel.itu.moapd.bikeshare.R
import mhel.itu.moapd.bikeshare.model.entity.Bike
import mhel.itu.moapd.bikeshare.model.entity.Ride
import mhel.itu.moapd.bikeshare.model.repository.AccountRepository
import mhel.itu.moapd.bikeshare.model.repository.AccountRepository.startRide
import mhel.itu.moapd.bikeshare.model.repository.BikeRepository
import mhel.itu.moapd.bikeshare.model.repository.RideRepository
import mhel.itu.moapd.bikeshare.viewmodel.adapter.BikeAdapter


class BikeListActivity : AppCompatActivity() {

    private val bikeList = BikeRepository.read();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bike_list);

        bikeListCount.text = "Displaying " + bikeList.size + " results";

        if (bikeList.isNotEmpty()) {
            this.findViewById<RecyclerView>(R.id.bike_list).also {
                it.setHasFixedSize(true)
                it.layoutManager = LinearLayoutManager(it.context)
                it.adapter = BikeAdapter(this.bikeList) { b -> callback(b)}
            }
        } else emptyStateLabel.visibility = View.VISIBLE;
    }

    private fun callback(b:Bike) {

        if(!b.isAvailable) {
            Toast.makeText(this.applicationContext, "This bike is not available!", Toast.LENGTH_SHORT).show()
            return
        }

        val user = AccountRepository.find(0);
        if(user?.activeRide != null) {
            Toast.makeText(this.applicationContext, "You are already renting a bike!", Toast.LENGTH_LONG).show()
            return
        } else if (user?.balance!!.compareTo(0) == 0 || user.balance!!.compareTo(0) == -1) {
            Toast.makeText(this.applicationContext, "You have no money, add funds.", Toast.LENGTH_LONG).show()
            return
        }

        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.startRideTitle))
            .setMessage(resources.getString(R.string.confirm_start_title))
            .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
                // Respond to neutral button press
            }
            .setPositiveButton(resources.getString(R.string.startRide)) { dialog, which ->
                if(BikeRepository.lockBike(b.id)) {
                    val rs = RideRepository.add(
                        Ride(
                            userId          = 0,
                            bikeId          = b.id,
                            name            = b.name,
                            type            = b.type,
                            startLocation   = b.location,
                            startTime       = System.currentTimeMillis(),
                            rate            = b.rate,
                            image           = b.image
                        )
                    )
                    if(rs != null) {
                        if(startRide(user!!.id, rs.id)) {
                            Toast.makeText(this.applicationContext, "You are now renting ${b.name}", Toast.LENGTH_LONG).show()
                            setResult(Activity.RESULT_OK)
                            finish()
                        } else Toast.makeText(this.applicationContext, "Failed to start ride", Toast.LENGTH_LONG).show();
                    } else Toast.makeText(this.applicationContext, "Failed to create ride", Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(this.applicationContext, "Failed to lock bike", Toast.LENGTH_LONG).show();
            }
            .show()
    }
}
