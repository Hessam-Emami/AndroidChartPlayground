package com.emami.charttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.mikephil.charting.data.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MAIN_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refreshButton.setOnClickListener {
            barChart.data = provideChartData()
            barChart.invalidate()
        }
    }

}

private fun provideChartData(): BarData {
    val entryList = ChartDataProvider.dummyDataForX.mapIndexed { index, xValue ->
        BarEntry(xValue.toFloat(), ChartDataProvider.generateDummyDataForY()[index].toFloat())
    }
    val dataSet = BarDataSet(entryList, "Label")
    return BarData(dataSet)
}

object ChartDataProvider {
    val dummyDataForX = (0..19).toList()
    fun generateDummyDataForY() = List(20) { Random.nextInt(1, 100) }
}