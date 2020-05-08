package mhel.itu.moapd.bikeshare.viewmodel.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_main.*
import mhel.itu.moapd.bikeshare.R
import mhel.itu.moapd.bikeshare.model.CurrentUser
import mhel.itu.moapd.bikeshare.model.entity.Account
import mhel.itu.moapd.bikeshare.model.repository.AccountRepository
import mhel.itu.moapd.bikeshare.model.repository.BikeRepository
import mhel.itu.moapd.bikeshare.model.repository.RideRepository
import mhel.itu.moapd.bikeshare.viewmodel.activity.AddBalanceActivity
import mhel.itu.moapd.bikeshare.viewmodel.activity.AddBikeActivity
import mhel.itu.moapd.bikeshare.viewmodel.activity.BikeListActivity
import mhel.itu.moapd.bikeshare.viewmodel.activity.RideHistoryActivity
import mhel.itu.moapd.bikeshare.lib.ConversionManager
import mhel.itu.moapd.bikeshare.lib.ConversionManager.formatCurrencyToDkk
import mhel.itu.moapd.bikeshare.lib.ConversionManager.priceElapsed
import mhel.itu.moapd.bikeshare.lib.ConversionManager.timeDelta
import java.util.*


class MainBikeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    private lateinit var userViewModel : CurrentUser
    private lateinit var timer : Timer
    private var startTime : Long? = 0
    private var rate : Float? = 0f
    private lateinit var viewRef : View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewRef = view
        userViewModel = ViewModelProviders.of(this).get(CurrentUser::class.java)
        this.registerObservers()
        this.registerEventListeners()
        timer = Timer()
        //Secure default account is populated. Add more if you like.
        val rs = AccountRepository.find(userViewModel.id.value?:0)
        if(rs == null) AccountRepository.add(
            Account(
                userName    = "Mikkel Helmersen",
                rides       = 0,
                balance     = 1337f
            )
        )

        val user = AccountRepository.find(userViewModel.id.value?:0)
        if(user != null) {
            userViewModel.name.postValue(user.userName)
            userViewModel.rides.postValue(user.rides)
            userViewModel.balance.postValue(user.balance)
            userViewModel.activeRide.postValue(user.activeRide)
        }

        this.endCurrentRideBtn.setOnClickListener {
            MaterialAlertDialogBuilder(view.context)
                .setTitle(resources.getString(R.string.endRideTitle))
                .setMessage(resources.getString(R.string.confirm_end_ride_text))
                .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
                    // Respond to neutral button press
                }
                .setPositiveButton(resources.getString(R.string.endRide)) { dialog, which ->
                    if(handleEndRide()) Toast.makeText(view.context, "Ride ended", Toast.LENGTH_LONG).show()
                    else Toast.makeText(view.context, "Something went wrong", Toast.LENGTH_LONG).show()
                }
                .show()
        }
    }

    private fun handleEndRide() : Boolean {
        val usr = AccountRepository.find(userViewModel.id.value?:0)
        if(usr != null) {
            val ride = RideRepository.find(usr.activeRide)
            if(ride != null) {
                val endLocation = ""
                val endTime = System.currentTimeMillis();
                val time    = timeDelta(endTime, ride.startTime)
                val price   = priceElapsed(time?:0, ride.rate?:0f)

                RideRepository.endRide(
                    ride.id,
                    endLocation,
                    endTime,
                    price,
                    time
                )
                if(!BikeRepository.unlock(ride.bikeId)) return false

                val updatedUsr = AccountRepository.endRide(usr.id, price)
                userViewModel.activeRide.postValue(null)
                userViewModel.balance.postValue(updatedUsr!!.balance);
                this.rate = null
                this.startTime = null

            } else return false
        } else return false
        return true
    }

    private fun setTimerTask() {
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                activity!!.runOnUiThread {
                    if(rate == null || startTime == null) this.cancel()
                    else updateElapsed()
                }
            }
        }, 1000, 1000)
    }

    private fun updateElapsed() {
        if(rate == null || startTime == null) return
        val delta = System.currentTimeMillis() - startTime!!;
        this.rideElapsedLabel.text = ConversionManager.msToTimeString(delta)
        val price = priceElapsed(delta, rate!!)
        this.ridePriceElapsedLabel.text = formatCurrencyToDkk(price)
        var deltaBalance = userViewModel.balance.value!!.minus(price)
        if(deltaBalance.compareTo(0) == 0 || deltaBalance.compareTo(0) == -1) {
            handleEndRide()
            timer.cancel();
            timer.purge();
            timer = Timer()
            Toast.makeText(viewRef.context, "Ride ended, you ran out of money!", Toast.LENGTH_LONG).show()
        }
    }

    private fun setActiveRide(rideId : Long?) {
        if(rideId != null) {
            this.currentRideContainer.visibility = View.VISIBLE
            val ride = RideRepository.find(rideId)
            this.startTime = ride!!.startTime!!
            this.rate = ride.rate?:0f;
            this.currentRideLabel.text = "Renting ${ride.name}"
            this.rideElapsedLabel.text = "${ride.startTime}"
            this.ridePriceElapsedLabel.text = rate.toString()
            setTimerTask()
        } else {
            this.currentRideContainer.visibility = View.GONE
            timer.cancel();
            timer.purge();
            timer = Timer()
        }
    }

    private fun registerEventListeners() {
        this.addBikeButton.setOnClickListener {
            this.startActivity(Intent(this.activity, AddBikeActivity::class.java))
        }
        this.viewBikesButton.setOnClickListener {
            this.startActivityForResult(Intent(this.activity, BikeListActivity::class.java), 10001)
        }
        this.viewRideHistory.setOnClickListener {
            this.startActivity(Intent(this.activity, RideHistoryActivity::class.java))
        }
        this.addBalanceButton.setOnClickListener {
            this.startActivityForResult(Intent(this.activity, AddBalanceActivity::class.java), 10001)
        }

    }

    private fun registerObservers() {
        userViewModel.name.observe(this, Observer { newName -> this.userNameLabel.text = newName; })
        userViewModel.rides.observe(this,  Observer { newCount -> this.ridesCountLabel.text = newCount.toString(); })
        userViewModel.balance.observe(this,  Observer { newBalance ->
            this.balanceValue.text = formatCurrencyToDkk(newBalance)
        })

        userViewModel.activeRide.observe(this,  Observer { newRide ->
            setActiveRide(newRide)
        })
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 10001 && resultCode == Activity.RESULT_OK) {
            val ft: FragmentTransaction = fragmentManager!!.beginTransaction()
            ft.detach(this).attach(this).commit()
        }

    }

}
