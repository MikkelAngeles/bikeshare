package mhel.itu.moapd.bikeshare.viewmodel.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_add_bike.*
import kotlinx.android.synthetic.main.fragment_main.*
import mhel.itu.moapd.bikeshare.R
import mhel.itu.moapd.bikeshare.lib.GpsManager
import mhel.itu.moapd.bikeshare.lib.ImageManager
import mhel.itu.moapd.bikeshare.model.entity.Bike
import mhel.itu.moapd.bikeshare.model.repository.BikeRepository

class AddBikeActivity : AppCompatActivity() {

    private lateinit var image      : ImageView
    private lateinit var name       : TextView
    private lateinit var type       : TextView
    private lateinit var location   : TextView
    private lateinit var rate       : TextView
    private lateinit var imageBtn   : Button
    private lateinit var submitBtn  : Button

    private var isRegisterBikeButtonEnabled = true
    private val REQUEST_IMAGE_CAPTURE = 1
    private var bikeImage : Bitmap? = null
    private lateinit var gpsManager : GpsManager

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bike)

        this.gpsManager = GpsManager(this)
        this.gpsManager.requestLocationUpdates();

        this.name       = this.findViewById(R.id.bikeNameEntry);
        this.type       = this.findViewById(R.id.bikeTypeEntry);
        this.location   = this.findViewById(R.id.locationEntry);
        this.rate       = this.findViewById(R.id.bikeRateEntry);

        this.location.text = GpsManager.locationToString(this.gpsManager.currentLocation);

        if (savedInstanceState != null) {
            this.isRegisterBikeButtonEnabled = savedInstanceState.getBoolean("isRegisterBikeButtonEnabled")
            savedInstanceState.getByteArray("bikeImage")?.let {
                this.bikeImage = ImageManager.byteArrayToBitmap(it)
                this.image.setImageBitmap(this.bikeImage)
            }
        }


        registerEventListeners();
    }

    private fun registerEventListeners() {
        this.addBikeSubmitBtn.setOnClickListener {
            BikeRepository.add(
                Bike(
                    name        = name.text.toString(),
                    type        = type.text.toString(),
                    location    = name.text.toString(),
                    rate        = rate.text.toString().toFloatOrNull(),
                    image       = ImageManager.bitmapToByteArray(this.bikeImage as Bitmap),
                    isAvailable = true
                )
            )
            finish();
        }

        this.uploadBikeImageBtn.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            this.bikeImage = Bitmap.createScaledBitmap(
                imageBitmap,
                imageBitmap.width * 2,
                imageBitmap.height * 2,
                true
            )
            this.image.setImageBitmap(this.bikeImage)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState?.putBoolean("isRegisterBikeButtonEnabled", this.isRegisterBikeButtonEnabled)
        this.bikeImage?.let { outState?.putByteArray("bikeImage", ImageManager.bitmapToByteArray(it)) }
        super.onSaveInstanceState(outState)
    }
}
