package com.testproject.android.kotlintestproject.viewModel

import android.app.Activity
import com.testproject.android.kotlintestproject.interfaces.AdapterInterface
import com.testproject.android.kotlintestproject.model.Currency
import com.testproject.android.kotlintestproject.model.getXmlPullParser
import com.testproject.android.kotlintestproject.model.getXmlPullParserFactory
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

class ControllerAdapter {

    var NumCode: String = ""
    var CharCode: String = ""
    var Scale: String = ""
    var Name: String = ""
    var Rate: String = ""

    fun ParseXML(activity: Activity,xmlString: String) {
        var list: MutableList<Currency> = ArrayList<Currency>()
        try {
            var factory: XmlPullParserFactory = getXmlPullParserFactory()
            factory.isNamespaceAware = true
            var parser: XmlPullParser = getXmlPullParser(factory)
            parser.setInput(StringReader(xmlString))
            var eventType: Int = parser.eventType

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {

                    var name = parser.name

                    if (name.equals("UpdateFlag")) {
                        var ref = parser.getAttributeValue(null, "UpdateFlag")

                        if (parser.next() == XmlPullParser.TEXT) {
                            var UpdateFlag = parser.text
                        }
                    } else if (name.equals("NumCode")) {
                        if (parser.next() == XmlPullParser.TEXT) {
                            NumCode = parser.text
                        }
                    } else if (name.equals("CharCode")) {
                        if (parser.next() == XmlPullParser.TEXT) {
                            CharCode = parser.text
                        }
                    } else if (name.equals("Scale")) {
                        if (parser.next() == XmlPullParser.TEXT) {
                            Scale = parser.text
                        }
                    } else if (name.equals("Name")) {
                        if (parser.next() == XmlPullParser.TEXT) {
                            Name = parser.text
                        }
                    } else if (name.equals("Rate")) {
                        if (parser.next() == XmlPullParser.TEXT) {
                            Rate = parser.text
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (Name != "" && Scale != "" && CharCode != "" && NumCode != "" && Rate != "") {
                        list.add(Currency(NumCode.toInt(), CharCode, Scale.toInt(), Name, Rate.toFloat()))

                        cleanFields()

                    }
                }

                eventType = parser.next()


            }
        } catch (e: Exception) {

        }
        val i = activity as AdapterInterface
        i.completeListener(list)
    }

    fun cleanFields(){
        NumCode = ""
        CharCode = ""
        Scale = ""
        Name = ""
        Rate = ""
    }
}