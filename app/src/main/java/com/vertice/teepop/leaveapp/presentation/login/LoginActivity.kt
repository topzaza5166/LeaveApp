package com.vertice.teepop.leaveapp.presentation.login

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.orhanobut.hawk.Hawk
import com.vertice.teepop.leaveapp.LeaveApplication
import com.vertice.teepop.leaveapp.R
import com.vertice.teepop.leaveapp.data.model.Employee
import com.vertice.teepop.leaveapp.data.remote.LoginApi
import com.vertice.teepop.leaveapp.presentation.leave.LeaveActivity
import com.vertice.teepop.leaveapp.util.Constant
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject
import io.reactivex.disposables.Disposable


class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var loginApi: LoginApi

    private val TAG: String = LoginActivity::class.java.simpleName

    var disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        LeaveApplication.component.inject(this)

        btSingIn.setOnClickListener({
            val username = editUsername.text.toString()
            val password = editPassword.text.toString()

            val dialog = ProgressDialog.show(this, "", "Please Wait", true)
            if (username != "" && password != "") {
                val disposable = postLogin(username, password, dialog)
                disposables.add(disposable)
            }
        })


//        editPassword.setOnEditorActionListener { textView: TextView, actionId: Int, keyEvent: KeyEvent? ->
//            var handled = false
//            if (actionId == EditorInfo.IME_ACTION_SEND) {
//
//                handled = true
//            }
//            handled
//        }
    }

    override fun onStart() {
        super.onStart()
        disposables = CompositeDisposable()
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }

    private fun postLogin(username: String, password: String, dialog: ProgressDialog): Disposable? {
        return loginApi.login(LoginApi.LoginBody(username, password))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { response ->
                            dialog.cancel()
                            responseSuccess(response)
                        },
                        { error ->
                            dialog.cancel()
                            Toast.makeText(this, "Something was wrong", Toast.LENGTH_SHORT).show()
                            Log.e(TAG, error.toString())
                        }
                )
    }

    private fun responseSuccess(response: Employee) {
        Hawk.put(Constant.USER_KEY, response)
        Toast.makeText(this, "Name: ${response.name} \n Username: ${response.userName}", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, LeaveActivity::class.java)
        startActivity(intent)
    }

}
