package mhel.itu.moapd.bikeshare.viewmodel.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_main.*
import mhel.itu.moapd.bikeshare.R
import mhel.itu.moapd.bikeshare.model.entity.Account
import mhel.itu.moapd.bikeshare.model.repository.AccountRepository
import mhel.itu.moapd.bikeshare.viewmodel.activity.AddBalanceActivity
import mhel.itu.moapd.bikeshare.viewmodel.activity.AddBikeActivity
import mhel.itu.moapd.bikeshare.viewmodel.activity.BikeListActivity
import mhel.itu.moapd.bikeshare.viewmodel.activity.RideHistoryActivity


class MainBikeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.registerEventListeners();

        //Secure default account is populated. Add more if you like.
        val rs = AccountRepository.find(0);
        if(rs == null) AccountRepository.add(
            Account(
                userName    = "Mikkel Helmersen",
                rides       = 0,
                balance     = 1337f
            )
        )

        val user = AccountRepository.find(0);
        if(user != null) {
            this.userNameLabel.text = user.userName.toString();
            this.ridesCountLabel.text = user.rides.toString();
            this.balanceValue.text = user.balance.toString();
        } else {
            this.userNameLabel.text = "Anonymous user";
            this.ridesCountLabel.text = "0";
            this.balanceValue.text = "0";
        }
    }

    private fun registerEventListeners() {
        this.addBikeButton.setOnClickListener {
            this.startActivity(Intent(this.activity, AddBikeActivity::class.java));
        }
        this.viewBikesButton.setOnClickListener {
            this.startActivity(Intent(this.activity, BikeListActivity::class.java));
        }

        this.viewRideHistory.setOnClickListener {
            this.startActivity(Intent(this.activity, RideHistoryActivity::class.java));
        }

        this.addBalanceButton.setOnClickListener {
            this.startActivity(Intent(this.activity, AddBalanceActivity::class.java));
        }
    }

}
