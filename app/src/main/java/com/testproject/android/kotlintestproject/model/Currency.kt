package com.testproject.android.kotlintestproject.model

import android.databinding.BaseObservable
import android.support.annotation.NonNull
import java.io.Serializable

class Currency : Serializable,BaseObservable{
    var NumCode: Int = 0
    var CharCode: String = ""
    var Scale: Int = 0
    var Name: String = ""
    var Rate: Float = 0f

    constructor(@NonNull NumCode: Int,@NonNull CharCode: String,@NonNull Scale: Int,@NonNull Name: String,@NonNull Rate: Float) {
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

