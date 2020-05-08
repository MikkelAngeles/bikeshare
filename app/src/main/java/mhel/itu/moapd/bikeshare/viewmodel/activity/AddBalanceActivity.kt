package mhel.itu.moapd.bikeshare.viewmodel.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_add_balance.*
import mhel.itu.moapd.bikeshare.R
import mhel.itu.moapd.bikeshare.lib.ConversionManager.formatCurrencyToDkk
import mhel.itu.moapd.bikeshare.model.CurrentUser
import mhel.itu.moapd.bikeshare.model.repository.AccountRepository


class AddBalanceActivity : AppCompatActivity() {
    private lateinit var balance : TextView
    private val userViewModel: CurrentUser by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_balance)
        this.balance  = this.findViewById(R.id.balanceInput);
        registerEventListeners();
    }

    private fun registerEventListeners() {
        this.submitBalanceBtn.setOnClickListener {
            val newVal = balance.text.toString().toFloatOrNull();
            MaterialAlertDialogBuilder(this)
                .setTitle("Confirm new balance")
                .setMessage("You are about to add ${formatCurrencyToDkk(newVal?:0f)}")
                .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
                    closeContextMenu()
                }
                .setPositiveButton(resources.getString(R.string.confirm)) { dialog, which ->
                    val rs = AccountRepository.addBalance(0, balance.text.toString().toFloatOrNull());
                    userViewModel.balance.postValue(rs)
                    setResult(Activity.RESULT_OK)
                    Toast.makeText(this.applicationContext,
                        "Added ${formatCurrencyToDkk(balance.text.toString().toFloatOrNull()?:0f)}  to account! New total is ${formatCurrencyToDkk(rs?:0f)}", Toast.LENGTH_LONG).show()
                    finish();
                }
                .show()

        }
    }

}
