package mhel.itu.moapd.bikeshare.lib
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue

object ConversionManager {

    fun msToTimeString(millis : Long) : String {
        val hrs = TimeUnit.MILLISECONDS.toHours(millis).toInt().absoluteValue % 24
        val min = TimeUnit.MILLISECONDS.toMinutes(millis).toInt().absoluteValue % 60
        val sec = TimeUnit.MILLISECONDS.toSeconds(millis).toInt().absoluteValue % 60
        return String.format("%02d:%02d:%02d", hrs, min, sec)
    }

    fun priceElapsed(millis : Long, rate : Float) : Float {
        val hr = TimeUnit.MILLISECONDS.toSeconds(millis).toFloat()
        return hr * ((rate/60)/60)
    }

    fun priceElapsedFormat(millis : Long, rate : Float) : String {
        return formatCurrencyToDkk(priceElapsed(millis, rate))
    }

    fun timeDelta(d1 : Long?, d2 :Long?) : Long? {
        if (d1 != null) {
            if (d2 != null) {
                return d1.minus(d2)
            }
        }
       return null;
    }

    fun formatCurrencyToDkk(rate: Float) : String {
        val nf: NumberFormat = NumberFormat.getCurrencyInstance(getLocalFromISO("DKK"))
        return nf.format(rate) as String
    }

    private fun getLocalFromISO(iso4217code: String): Locale? {
        var toReturn: Locale? = null
        for (locale in NumberFormat.getAvailableLocales()) {
            val code =
                NumberFormat.getCurrencyInstance(locale).currency.currencyCode
            if (iso4217code == code) {
                toReturn = locale
                break
            }
        }
        return toReturn
    }
}