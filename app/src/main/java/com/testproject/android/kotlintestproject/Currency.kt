package com.testproject.android.kotlintestproject

import java.io.Serializable

class Currency : Serializable{
    var NumCode: Int = 0
    var CharCode: String = ""
    var Scale: Int = 0
    var Name: String = ""
    var Rate: Float = 0f

    constructor(NumCode: Int, CharCode: String, Scale: Int, Name: String, Rate: Float) {
        this.NumCode = NumCode
        this.CharCode = CharCode
        this.Scale = Scale
        this.Name = Name
        this.Rate = Rate
    }

    override fun toString(): String {
        return "Currency(NumCode=$NumCode, CharCode='$CharCode', Scale=$Scale, Name='$Name', Rate=$Rate)"
    }
}

