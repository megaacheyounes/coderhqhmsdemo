package com.younes.coderhqhms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.huawei.hms.support.account.AccountAuthManager
import com.huawei.hms.support.account.request.AccountAuthParamsHelper
import com.huawei.hms.support.account.service.AccountAuthService
import com.huawei.hms.support.hwid.ui.HuaweiIdAuthButton

class MainActivity : AppCompatActivity() {

    lateinit var huaweiSignInButton: HuaweiIdAuthButton
    lateinit var accountAuthService: AccountAuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAccountKit()
        initUI()
    }

    private fun initUI() {
        huaweiSignInButton = findViewById(R.id.huawei_signin)
        huaweiSignInButton.setOnClickListener {
            showHuaweiSignInAuthorizationpage()
        }
    }

    private fun initAccountKit() {
        val accountAuthParams = AccountAuthParamsHelper()
            .setIdToken()
            .setProfile()
            .setEmail()
            .createParams()

        accountAuthService = AccountAuthManager.getService(this, accountAuthParams)
    }

    val REQ_CODE = 1

    private fun showHuaweiSignInAuthorizationpage() {
        startActivityForResult(accountAuthService.signInIntent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                val task = AccountAuthManager.parseAuthResultFromIntent(data)
                if (task.isSuccessful) {
                    val authAccount = task.result
                    Toast.makeText(this, "Welcome ${authAccount.displayName}", Toast.LENGTH_SHORT)
                        .show()
                    redirectToMapKit()

                }
            }
        }
    }

    private fun redirectToMapKit() {
        startActivity(Intent(this, MapsActivity::class.java))
    }

}