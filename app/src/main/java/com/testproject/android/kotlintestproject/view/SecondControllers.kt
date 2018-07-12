package com.testproject.android.kotlintestproject.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.testproject.android.kotlintestproject.R

fun checkConnection(activity: MainActivity) {
    progress(true,activity)
    var progress: ProgressBar = activity.findViewById(R.id.progress)
    progress.visibility = View.VISIBLE
    var connectivityManager: ConnectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED ||
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state == NetworkInfo.State.CONNECTED) {
        // есть подключение
        stub(false,activity)
        activity.GetXMLFromServer().execute()
    } else {
        // нет подключения
        stub(true,activity)
    }
}

fun progress(hint: Boolean,activity: MainActivity) {
    if (hint) { // прогресс виден
        activity.findViewById<ProgressBar>(R.id.progress).visibility = View.VISIBLE
    } else if (!hint) { // прогресс скрыт
        activity.findViewById<ProgressBar>(R.id.progress).visibility = View.INVISIBLE
    }
}

fun stub(check: Boolean, activity: MainActivity) { // если false то стуб скрыт, если true то виден
    var stubView: RelativeLayout = activity.findViewById(R.id.stub)
    activity.initClickerStub(activity.findViewById(R.id.try_refresh))
    if (check) { // стуб виден
        stubView.visibility = View.VISIBLE
        progress(false,activity)
    } else if (!check) { // стуб скрыт
        stubView.visibility = View.INVISIBLE
    }
}
