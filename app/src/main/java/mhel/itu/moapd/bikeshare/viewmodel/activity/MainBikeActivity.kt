package mhel.itu.moapd.bikeshare.viewmodel.activity

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import mhel.itu.moapd.bikeshare.R
import mhel.itu.moapd.bikeshare.lib.GPSUtils
import mhel.itu.moapd.bikeshare.viewmodel.fragment.MainBikeFragment

class MainBikeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GPSUtils.instance.findDeviceLocation(this)

        if (this.findViewById<FrameLayout>(R.id.fragment_bike_container) != null && savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_bike_container,
                    MainBikeFragment()
                )
                .commitNow()
        }
    }
}
