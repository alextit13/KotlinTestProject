package com.testproject.android.kotlintestproject.viewModel

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.testproject.android.kotlintestproject.R
import com.testproject.android.kotlintestproject.model.Currency
import kotlinx.android.synthetic.main.currency_item.view.*
import java.util.*

class RecyclerCustomAdapter(var data : MutableList<Currency>, var context: Context) : RecyclerView.Adapter<RecyclerCustomAdapter.ViewHolderCustom>() {


    override fun onBindViewHolder(holder: ViewHolderCustom?, position: Int) {
        holder?.CharCodeView?.text = data[position].CharCode
        holder?.RateView?.text = data[position].Rate.toString()
        holder?.NameView?.text = data[position].Name
        holder?.ScaleView?.text = "за ${data[position].Scale} ед."
    }

    fun changePositionItems(startPosition : Int, endPosition : Int){
        Collections.swap(data,startPosition,endPosition)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolderCustom {
        return ViewHolderCustom(LayoutInflater.from(context).inflate(R.layout.currency_item, parent, false))
    }

    class ViewHolderCustom(view: View) : RecyclerView.ViewHolder(view) {
        val CharCodeView = view.CharCode
        val RateView = view.Rate
        val NameView = view.Name
        val ScaleView = view.Scale
    }
}