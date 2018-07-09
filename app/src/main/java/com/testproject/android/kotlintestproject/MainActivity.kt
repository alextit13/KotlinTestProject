package com.testproject.android.kotlintestproject


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import me.rishabhkhanna.recyclerswipedrag.OnDragListener
import me.rishabhkhanna.recyclerswipedrag.RecyclerHelper
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.io.StringReader
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    private var LOG_TAG: String = "XML"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkConnection()
    }

    private fun checkConnection() {
        var progress: ProgressBar = findViewById(R.id.progress)
        progress.visibility= View.VISIBLE
        var connectivityManager: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state == NetworkInfo.State.CONNECTED){
            // есть подключение
            GetXMLFromServer().execute()
        }else{
            // нет подключения
        }
    }

    fun ParseXML(xmlString: String){
        var list:MutableList<Currency> = ArrayList<Currency>()
        try {
            var factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware=true
            var parser: XmlPullParser = factory.newPullParser()
            parser.setInput(StringReader(xmlString))
            var eventType : Int = parser.eventType

            var NumCode : String = ""
            var CharCode : String = ""
            var Scale : String = ""
            var Name : String = ""
            var Rate : String = ""

            while (eventType!=XmlPullParser.END_DOCUMENT){
                if (eventType==XmlPullParser.START_TAG){

                    var name = parser.name

                    if (name.equals("UpdateFlag")){
                        var ref = parser.getAttributeValue(null,"UpdateFlag")
                        //Log.d(LOG_TAG,"UpdateFlag:" + ref)

                        if (parser.next() == XmlPullParser.TEXT){
                            var UpdateFlag = parser.text
                            //Log.d(LOG_TAG,"DailyExRates:" + UpdateFlag)
                        }
                    }else if (name.equals("NumCode")){
                        if (parser.next() == XmlPullParser.TEXT){
                            NumCode = parser.text
                            //Log.d(LOG_TAG,"NumCode:" + NumCode)
                        }
                    }else if (name.equals("CharCode")){
                        if (parser.next() == XmlPullParser.TEXT){
                            CharCode = parser.text
                            //Log.d(LOG_TAG,"CharCode:" + CharCode)
                        }
                    }else if (name.equals("Scale")){
                        if (parser.next() == XmlPullParser.TEXT){
                            Scale = parser.text
                            //Log.d(LOG_TAG,"Scale:" + Scale)
                        }
                    }else if (name.equals("Name")){
                        if (parser.next() == XmlPullParser.TEXT){
                            Name = parser.text
                            //Log.d(LOG_TAG,"Name:" + Name)
                        }
                    }else if (name.equals("Rate")){
                        if(parser.next() == XmlPullParser.TEXT){
                            Rate = parser.text
                            //Log.d(LOG_TAG,"Rate:" + Rate)
                        }
                    }
                }else if(eventType == XmlPullParser.END_TAG){
                    if (Name!=""&&Scale!=""&&CharCode!=""&&NumCode!=""&&Rate!=""){
                        list.add(Currency(NumCode.toInt(),CharCode,Scale.toInt(),Name,Rate.toFloat()))

                        NumCode = ""
                        CharCode = ""
                        Scale = ""
                        Name = ""
                        Rate = ""

                    }
                }

                eventType = parser.next()


            }
        }catch (e:Exception){

        }
        // complete

        /*loop@ for (s in list){
            Log.d("TAG_LOG_TEST","msg = $s")
        }*/

        adapter(list)

    }

    fun adapter(list: MutableList<Currency>){
        var recyclerView: RecyclerView = findViewById(R.id.main_list)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerCustomAdapter(list,this)



        findViewById<RelativeLayout>(R.id.stub).visibility=View.INVISIBLE
        findViewById<ProgressBar>(R.id.progress).visibility=View.INVISIBLE


        val mIth = ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT) {
                    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                        val fromPos = viewHolder.adapterPosition
                        val toPos = target.adapterPosition
                        // move item in `fromPos` to `toPos` in adapter.
                        return true// true if moved, false otherwise
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    }
                })
        mIth.attachToRecyclerView(recyclerView)

    }


    inner class GetXMLFromServer : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String?): String {
            var URL = "http://www.nbrb.by/Services/XmlExRates.aspx"
            var res: String

            var isT: InputStream = CallServer(URL)
            if (isT!=null){
                res = StreamToString(isT)
            }else{
                res = "NotConnected"
            }
            println("MyXMLString = $res")
            return res
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            if (result.equals("NotConnected")){
                //stub(true)
            }else{

                if (result != null) {
                    ParseXML(result)
                }
            }
        }
    }
}
