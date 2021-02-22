package com.insspring.poifox.signup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.delivery.ui.base.BaseMvpActivity
import com.insspring.poifox.R
import com.insspring.poifox.initial.InitialActivity
import com.insspring.poifox.login.LoginActivity
import com.insspring.poifox.model.Register
import io.realm.Realm
import io.realm.kotlin.createObject
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*


class SignupActivity : BaseMvpActivity(), View.OnClickListener, SignupView {

    @InjectPresenter
    lateinit var signupPresenter: SignupPresenter

    override fun getLayoutId(): Int = R.layout.activity_signup

    private var vEtUsernameSignup: EditText? = null
    private var vEtPasswordSignup: EditText? = null

    private var mRealm: Realm? = null

    }

    override fun onClick(view: View) {
        if (vEtPasswordSignup?.text.toString().trim { it <= ' ' }.isEmpty()) {
            vEtPasswordSignup?.error = "enter password"
            vEtPasswordSignup?.requestFocus()
        }
        if (vEtUsernameSignup?.text.toString().trim { it <= ' ' }.isEmpty()) {
            vEtUsernameSignup?.error = "enter username"
            vEtUsernameSignup?.requestFocus()
        } else {
            writeToDataBase(
                vEtUsernameSignup?.text.toString().trim { it <= ' ' },
                vEtPasswordSignup?.text.toString().trim { it <= ' ' })
        }
    }

    private fun initListeners() {
        vTvLogIn.setOnClickListener {
            signupPresenter.onLoginClicked()
            finish()
        }

    }

    private fun writeToDataBase(username: String?, password: String?) {
        mRealm?.executeTransactionAsync({ bgRealm ->
            val register: Register = bgRealm.createObject()
            register.username = username
            register.password = password
        }, {
            Toast.makeText(this@SignupActivity, "success", Toast.LENGTH_SHORT).show()
        }) { error ->
            Toast.makeText(this@SignupActivity, "wrong data", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm?.close()
    }

    }

    override fun initRealm() {
        mRealm = Realm.getDefaultInstance()
    }

    override fun updateEditText() {
        vEtUsernameSignup = findViewById(R.id.vEtUsernameSignup)
        vEtPasswordSignup = findViewById(R.id.vEtPasswordSignup)
    }

    override fun updateTitleName() {
        val spannable = SpannableString(getString(R.string.poifox))
        spannable.setSpan(
            ForegroundColorSpan(Color.WHITE),
            0,
            2,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(Color.parseColor("#EB874B")),
            3,
            6,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        vTvTitleNameSignUp.text = spannable
    }

}


