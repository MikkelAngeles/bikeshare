package mhel.itu.moapd.bikeshare.viewmodel.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_bike_list.*
import kotlinx.android.synthetic.main.activity_bike_list.view.*
import kotlinx.android.synthetic.main.activity_bike_list.view.bikeListCount
import mhel.itu.moapd.bikeshare.R
import mhel.itu.moapd.bikeshare.model.repository.BikeRepository
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
                it.adapter = BikeAdapter(this.bikeList)
            }
        } else emptyStateLabel.visibility = View.VISIBLE;
    }
}
