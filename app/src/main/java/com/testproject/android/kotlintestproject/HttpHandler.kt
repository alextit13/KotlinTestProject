package com.testproject.android.kotlintestproject

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

var LOG_TAG: String = "HttpHandler"

fun CallServer (remoteURL:String) : InputStream{
    var inStream: InputStream? = null
    try {
        var url = URL(remoteURL)
        var cc: HttpURLConnection = url.openConnection() as HttpURLConnection
        cc.readTimeout=5000
        cc.connectTimeout=5000
        cc.requestMethod="GET"
        cc.doInput=true
        var response:Int = cc.responseCode
        if (response==HttpURLConnection.HTTP_OK){
            inStream = cc.inputStream
        }
    } catch (e:Exception){
        Log.d(LOG_TAG,"Error in CallServer",e)
    }
    return inStream!!
}

fun StreamToString(stream:InputStream):String {
    var isr = InputStreamReader(stream)
    var reader = BufferedReader(isr)
    var response = StringBuilder()
    var line: String? = null
    try {

        while ({ line = reader.readLine(); line }() != null) {
            //println("lineReaderText = $line")
            response.append(line)
        }

    } catch (e: IOException) {
        Log.e(LOG_TAG, "Error in StreamToString", e)
    } catch (e: Exception) {
        Log.e(LOG_TAG, "Error in StreamToString", e);
    } finally {
        try {
            stream.close()
        } catch (e: IOException) {
            Log.e(LOG_TAG, "Error in StreamToString", e);
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error in StreamToString", e);
        }
    }
    return response.toString()
}
