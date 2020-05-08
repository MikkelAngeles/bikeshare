package mhel.itu.moapd.bikeshare.lib

import android.text.format.DateFormat
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue

object ConversionManager {

    fun getPriceFromUsage(from: Date, to: Date, pricePerHour: Float) : Float {
        return BigDecimal.valueOf(TimeUnit.MILLISECONDS.toSeconds(to.time - from.time))
            .divide(BigDecimal(60), 2, RoundingMode.HALF_UP) // Minutes
            .divide(BigDecimal(60), 2, RoundingMode.HALF_UP) // Hours
            .multiply(BigDecimal(pricePerHour.toString()))
            .toFloat()
    }
    fun toHoursAndMinutes(millis : Long) : String {
        val hrs = TimeUnit.MILLISECONDS.toHours(millis).toInt().absoluteValue % 24
        val min = TimeUnit.MILLISECONDS.toMinutes(millis).toInt().absoluteValue % 60
        return String.format("%02d:%02d", hrs, min)
    }

    fun getDifference(from: Date, to: Date) : Long {
        return to.time - from.time
    }

    fun format(date: Date) : String {
        return DateFormat.format("MMM d, yyyy", date) as String
    }

    fun formatCurrencyToDkk(rate: Float) : String {
        val nf: NumberFormat = NumberFormat.getCurrencyInstance()
        nf.setMaximumFractionDigits(0)
        nf.setCurrency(Currency.getInstance("DKK"))
        return nf.format(rate) as String
    }

}