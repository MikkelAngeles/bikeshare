package mhel.itu.moapd.bikeshare.viewmodel.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_main.*
import mhel.itu.moapd.bikeshare.R
import mhel.itu.moapd.bikeshare.model.CurrentUser
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

    private lateinit var userViewModel : CurrentUser
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userViewModel = ViewModelProviders.of(this).get(CurrentUser::class.java);
        this.registerObservers()
        this.registerEventListeners()

        //Secure default account is populated. Add more if you like.
        val rs = AccountRepository.find(userViewModel.id.value?:0);
        if(rs == null) AccountRepository.add(
            Account(
                userName    = "Mikkel Helmersen",
                rides       = 0,
                balance     = 1337f
            )
        )

        val user = AccountRepository.find(userViewModel.id.value?:0);
        if(user != null) {
            userViewModel.name.postValue(user.userName)
            userViewModel.rides.postValue(user.rides)
            userViewModel.balance.postValue(user.balance)
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
            this.startActivityForResult(Intent(this.activity, AddBalanceActivity::class.java), 10001);
        }
    }

    private fun registerObservers() {
        userViewModel.name.observe(this, Observer { newName -> this.userNameLabel.text = newName; })
        userViewModel.rides.observe(this,  Observer { newCount -> this.ridesCountLabel.text = newCount.toString(); })
        userViewModel.balance.observe(this,  Observer { newBalance ->
            this.balanceValue.text = newBalance.toString();
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
