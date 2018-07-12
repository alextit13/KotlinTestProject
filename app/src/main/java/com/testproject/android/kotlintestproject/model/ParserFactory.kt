package com.testproject.android.kotlintestproject.model

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

fun getXmlPullParserFactory() : XmlPullParserFactory {
    return XmlPullParserFactory.newInstance()
}

fun getXmlPullParser(factory : XmlPullParserFactory) : XmlPullParser {
    return factory.newPullParser()
}