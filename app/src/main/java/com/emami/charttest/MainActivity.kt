package com.emami.charttest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myRecycler.apply {

            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = MyAdapter()
            (adapter as MyAdapter).submitList((1..100).toList())
            layoutManager?.scrollToPosition(Integer.MAX_VALUE / 2)
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(this)
            var lastSnapped: View? = null
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                        snapHelper.findSnapView(this@apply!!.layoutManager)?.let {
                            Log.d("MAIN ACTIVITY", "CENTER POSITION IS ${layoutManager?.getPosition(it)}")
                            lastSnapped = it
                            (it as ViewGroup).removeAllViews()
                            LayoutInflater.from(this@MainActivity).inflate(R.layout.first, it as ViewGroup)
                        }
                    }
                    lastSnapped?.let {
                        lastSnapped = null
                    }
                }
            })
        }


    }


}

class MyAdapter : ListAdapter<Int, RecyclerView.ViewHolder>(DiffCallback) {

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun getItem(position: Int): Int {
        val positionInList = position % currentList.size
        return super.getItem(positionInList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            2 -> FirstViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.first,
                    parent,
                    false
                )
            )
            1 -> SecondViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.second,
                    parent,
                    false
                )
            )
            0 -> ThirdViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.third,
                    parent,
                    false
                )
            )
            else -> FirstViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.first,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FirstViewHolder -> holder.bind(getItem(position))
            is SecondViewHolder -> holder.bind(getItem(position))
            is ThirdViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position % 10 == 0 -> 2
            position % 5 == 0 -> 1
            else -> 0
        }
    }

    class FirstViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {
        fun bind(item: Int) {

        }
    }

    class SecondViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {
        fun bind(item: Int) {

        }
    }

    class ThirdViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {
        fun bind(item: Int) {

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }
}
