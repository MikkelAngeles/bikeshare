package mhel.itu.moapd.bikeshare.viewmodel.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_ride_history.*
import mhel.itu.moapd.bikeshare.R
import mhel.itu.moapd.bikeshare.model.repository.RideRepository
import mhel.itu.moapd.bikeshare.viewmodel.adapter.RideAdapter

class RideHistoryActivity : AppCompatActivity() {

    private val rideList = RideRepository.read();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_history)

        rideHistoryCountLabel.text = "You have ${rideList.size} rides"

        if (rideList.isNotEmpty()) {
            this.findViewById<RecyclerView>(R.id.ride_list).also {
                it.setHasFixedSize(true)
                it.layoutManager = LinearLayoutManager(it.context)
                it.adapter = RideAdapter(this.rideList)
            }
        } else rideEmptyState.visibility = View.VISIBLE;
    }
}
