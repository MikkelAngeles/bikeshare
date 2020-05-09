package mhel.itu.moapd.bikeshare.viewmodel.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_bike.*
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

    private var isRegisterBikeButtonEnabled = true
    private val REQUEST_IMAGE_CAPTURE = 1
    private var bitmapImage : Bitmap? = null
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
        this.image      = this.findViewById(R.id.bikeUploadImageView);

        this.location.text = gpsManager.getNearestAddress()

        if (savedInstanceState != null) {
            this.isRegisterBikeButtonEnabled = savedInstanceState.getBoolean("isRegisterBikeButtonEnabled")
            savedInstanceState.getByteArray("bikeImage")?.let {
                this.bitmapImage = ImageManager.byteArrayToBitmap(it)
                this.image.setImageBitmap(this.bitmapImage)
            }
        }


        registerEventListeners();
    }

    private fun registerEventListeners() {
        this.addBikeSubmitBtn.setOnClickListener {
            if(validateInput()) {
                val currLoc = this.gpsManager.requestLocationUpdates()
                val lon = currLoc?.longitude.toString().toFloatOrNull();
                val lat = currLoc?.latitude.toString().toFloatOrNull();

                val rs = BikeRepository.add(
                    Bike(
                        name = name.text.toString(),
                        type = type.text.toString(),
                        location = location.text.toString(),
                        rate = rate.text.toString().toFloatOrNull(),
                        image = ImageManager.bitmapToByteArray(this.bitmapImage as Bitmap),
                        isAvailable = true,
                        lon = lon,
                        lat = lat
                    )
                )
                if(rs != null ) {
                    finish();
                    Toast.makeText(
                        this.applicationContext,
                        "Bike added",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this.applicationContext,
                        "Something went wrong",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(
                    this.applicationContext,
                    "Form is not filled out correctly",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        this.uploadBikeImageBtn.setOnClickListener {
            this.dispatchTakePictureIntent()
        }
    }

    private fun validateInput() : Boolean {
        if (this.name.text.isBlank()) return false
        if (this.type.text.isBlank()) return false
        if (this.rate.text.isBlank() || this.rate.text.toString().toFloatOrNull() == null) return false
        if (this.bitmapImage == null) return false
        return true
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap

            this.bitmapImage = Bitmap.createScaledBitmap(
                imageBitmap,
                imageBitmap.width * 2,
                imageBitmap.height * 2,
                true
            )

            this.image.setImageBitmap(this.bitmapImage)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState?.putBoolean("isRegisterBikeButtonEnabled", this.isRegisterBikeButtonEnabled)

        this.bitmapImage?.let { outState?.putByteArray("bitmapImage", ImageManager.bitmapToByteArray(it)) }

        super.onSaveInstanceState(outState)
    }
}
