package mhel.itu.moapd.bikeshare.viewmodel.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_add_balance.*
import mhel.itu.moapd.bikeshare.R
import mhel.itu.moapd.bikeshare.model.CurrentUser
import mhel.itu.moapd.bikeshare.model.repository.AccountRepository


class AddBalanceActivity : AppCompatActivity() {

    private lateinit var balance : TextView
    private lateinit var userViewModel : CurrentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_balance)
        userViewModel = ViewModelProviders.of(this).get(CurrentUser::class.java);


        //startActivityForResult(intent, 10001)
        this.balance  = this.findViewById(R.id.balanceInput);
        registerEventListeners();
    }

    private fun registerEventListeners() {
        this.submitBalanceBtn.setOnClickListener {
            val rs = AccountRepository.addBalance(0, balance.text.toString().toFloatOrNull());
            setResult(Activity.RESULT_OK)
            userViewModel.balance.postValue(rs)
            finish();
        }
    }

}
