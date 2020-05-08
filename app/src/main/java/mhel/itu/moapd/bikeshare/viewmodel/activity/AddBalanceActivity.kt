package mhel.itu.moapd.bikeshare.viewmodel.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_add_balance.*
import kotlinx.android.synthetic.main.activity_add_bike.*
import kotlinx.android.synthetic.main.fragment_main.*
import mhel.itu.moapd.bikeshare.R
import mhel.itu.moapd.bikeshare.model.entity.Account
import mhel.itu.moapd.bikeshare.model.entity.Bike
import mhel.itu.moapd.bikeshare.model.repository.AccountRepository
import mhel.itu.moapd.bikeshare.model.repository.BikeRepository

class AddBalanceActivity : AppCompatActivity() {

    private lateinit var balance : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_balance)

        this.balance  = this.findViewById(R.id.balanceInput);
        registerEventListeners();
    }

    private fun registerEventListeners() {
        this.submitBalanceBtn.setOnClickListener {
            val rs = AccountRepository.addBalance(0, balance.text.toString().toFloatOrNull());

            finish();
        }
    }

}
