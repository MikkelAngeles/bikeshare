package mhel.itu.moapd.bikeshare.viewmodel.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_bike_list.*
import mhel.itu.moapd.bikeshare.R
import mhel.itu.moapd.bikeshare.model.entity.Bike
import mhel.itu.moapd.bikeshare.model.repository.AccountRepository
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
        val rs = b;

        if(!b.isAvailable) {
            Toast.makeText(this.applicationContext, "This bike is not available!", Toast.LENGTH_SHORT).show()
            return
        }

        if(!b.isAvailable) {
            Toast.makeText(this.applicationContext, "This bike is not available!", Toast.LENGTH_SHORT).show()
            return
        }

        finish();
    }
}
