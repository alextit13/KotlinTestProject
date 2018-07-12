package com.testproject.android.kotlintestproject.model

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.MotionEvent
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.testproject.android.kotlintestproject.R
import com.testproject.android.kotlintestproject.viewModel.RecyclerCustomAdapter

fun adapter(activity: Activity,list: MutableList<Currency>) {
    var recyclerView: RecyclerView = activity.findViewById(R.id.main_list)

    recyclerView.layoutManager = LinearLayoutManager(activity)
    recyclerView.adapter = RecyclerCustomAdapter(list, activity)

    activity.findViewById<RelativeLayout>(R.id.stub).visibility = View.INVISIBLE
    activity.findViewById<ProgressBar>(R.id.progress).visibility = View.INVISIBLE

    val mIth = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    ItemTouchHelper.UP) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    val fromPos = viewHolder.adapterPosition
                    val toPos = target.adapterPosition

                    recyclerView.setOnTouchListener { v, event ->
                        if (event.action == MotionEvent.ACTION_UP) {
                            (recyclerView.adapter as RecyclerCustomAdapter).changePositionItems(fromPos, toPos)
                        }
                        false
                    }
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                }
            })
    mIth.attachToRecyclerView(recyclerView)
}