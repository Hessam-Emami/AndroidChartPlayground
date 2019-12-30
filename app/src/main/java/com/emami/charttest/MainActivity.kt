package com.emami.charttest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MAIN_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupCharInteractions(barChart)
        setupXAxis(barChart.xAxis, ContextCompat.getColor(this, R.color.gray))
        setupYAxis(barChart.axisLeft, ContextCompat.getColor(this, R.color.gray))
        refreshButton.setOnClickListener {
            barChart.data = provideChartData()
            barChart.invalidate()
        }
        refreshButton.performClick()
    }
}

private fun setupXAxis(xAxis: XAxis, gridColor: Int) {
    xAxis.apply {
        isEnabled = true
        setDrawGridLines(false)
        setAvoidFirstLastClipping(true)
        axisLineColor = gridColor
        enableAxisLineDashedLine(1.0f, 1.5f, 0f)
        position = XAxis.XAxisPosition.BOTTOM
    }
}

private fun setupYAxis(yAxis: YAxis, gridColor: Int) {
    yAxis.apply {
        setGridColor(gridColor)
        setDrawAxisLine(false)
        enableGridDashedLine(1.0f, 1f, 0f)
        spaceBottom = 2f
    }
}

private fun setupCharInteractions(barChart: BarChart) {
    barChart.apply {
        setScaleEnabled(false)
        setTouchEnabled(false)
        isDragEnabled = false
        setDrawBorders(false)
        axisRight.isEnabled = false
        setFitBars(true)
    }
}


private fun provideChartData(): BarData {
    //Entry represents each point
    val entryList = ChartDataProvider.dummyDataForX.mapIndexed { index, xValue ->
        BarEntry(xValue.toFloat(), ChartDataProvider.generateDummyDataForY()[index].toFloat())
    }
    //DataSet knows which items points belong together
    val dataSet = BarDataSet(entryList, "Label")
    //Data holds all data needed for chart
    return BarData(dataSet)
}

object ChartDataProvider {
    val dummyDataForX = (0..6).toList()
    fun generateDummyDataForY() = List(7) { Random.nextInt(1, 100) }
    val quarters = listOf("Q1", "Q2", "Q3", "Q4", "Q5", "Q6", "Q7")

}