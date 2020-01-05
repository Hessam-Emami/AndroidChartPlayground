package com.emami.charttest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        barChart.setupBarChart(ContextCompat.getColor(this, R.color.gray))
        refreshButton.setOnClickListener {
            drawBarChart(
                ChartDummyDataProvider.generateDummyDataForY().map { it.toFloat() },
                ChartDummyDataProvider.dummyDataForX.map { it.toFloat() },
                ChartDummyDataProvider.quarters
            )
        }
        refreshButton.performClick()
    }

    //TODO put this into a base class or something
    private fun drawBarChart(
        yValues: List<Float>,
        xValues: List<Float>,
        xLabelValues: Array<String>,
        startColor: Int = ContextCompat.getColor(this, R.color.start),
        endColor: Int = ContextCompat.getColor(this, R.color.end)
    ) {
        val data =
            BarChartUtil.provideBarChartData(yValues, xValues, xLabelValues, startColor, endColor)
        barChart.showBarChart(data)
    }
}
