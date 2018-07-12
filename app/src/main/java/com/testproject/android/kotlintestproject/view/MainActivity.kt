package com.testproject.android.kotlintestproject.view


import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.testproject.android.kotlintestproject.R
import com.testproject.android.kotlintestproject.interfaces.AdapterInterface
import com.testproject.android.kotlintestproject.model.Currency
import com.testproject.android.kotlintestproject.viewModel.CallServer
import com.testproject.android.kotlintestproject.viewModel.ControllerAdapter
import com.testproject.android.kotlintestproject.viewModel.StreamToString
import com.testproject.android.kotlintestproject.model.adapter
import java.io.InputStream

class MainActivity : AppCompatActivity(), AdapterInterface {

    var activity: Activity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stub(false,this)
        checkConnection(this)
    }

    fun initClickerStub(btn_stubTry: Button) {
        btn_stubTry.setOnClickListener { v -> checkConnection(this) }
    }

    inner class GetXMLFromServer : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            var URL = resources.getString(R.string.URL);
            var res: String

            var isT: InputStream = CallServer(URL)
            if (isT != null) {
                res = StreamToString(isT)
            } else {
                res = "NotConnected"
            }
            println("MyXMLString = $res")
            return res
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            if (result.equals("NotConnected")) {
                //stub(true)
            } else {

                if (result != null) {
                    ControllerAdapter().ParseXML(activity, result)
                }
            }
        }
    }

    override fun completeListener(list: MutableList<Currency>) {
        adapter(this, list)
        progress(false,this)
    }
}