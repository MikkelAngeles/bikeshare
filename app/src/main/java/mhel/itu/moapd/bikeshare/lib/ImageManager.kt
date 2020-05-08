package mhel.itu.moapd.bikeshare.lib

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

object ImageManager {
    fun bitmapToByteArray(image : Bitmap) : ByteArray {
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    fun byteArrayToBitmap(byteArray: ByteArray?): Bitmap? {
        if(byteArray == null) return null;
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}