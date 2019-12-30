package com.emami.charttest

import CustomBarChartRenderer
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MAIN_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBarChart(barChart, ContextCompat.getColor(this, R.color.gray))
        refreshButton.setOnClickListener {
            barChart.data = provideChartData(
                Color.BLUE,
                ContextCompat.getColor(this, R.color.start),
                ContextCompat.getColor(this, R.color.end),
                ContextCompat.getDrawable(this, R.drawable.bar_drawable)!!
            )
            barChart.animateY(300)
            barChart.notifyDataSetChanged()
            barChart.setHardwareAccelerationEnabled(true)
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
        //TODO should remove this from here!
        valueFormatter = XAxisValueFormatter(ChartDataProvider.quarters)
        yOffset = 5f
        textSize = 15f
    }
}
private fun setupYAxis(yAxis: YAxis, gridColor: Int) {
    yAxis.apply {
        setGridColor(gridColor)
        setDrawAxisLine(false)
        enableGridDashedLine(1.0f, 1f, 0f)
        axisMinimum = -1f
        xOffset = 10f
    }
}

private fun setupBarChart(barChart: BarChart, axisColor: Int) {
    barChart.apply {
        setScaleEnabled(false)
        setTouchEnabled(false)
        isDragEnabled = false
        setDrawBorders(false)
        axisRight.isEnabled = false
        setFitBars(true)
        renderer = CustomBarChartRenderer(this, this.animator, this.viewPortHandler)
    }
    styleChart(barChart)
    setupXAxis(barChart.xAxis, axisColor)
    setupYAxis(barChart.axisLeft, axisColor)
}

private fun styleChart(barChart: BarChart) {
    barChart.apply {
        setBackgroundColor(Color.WHITE)
        setNoDataText("دیتایی برای نمایش وجود ندارد!")
        setDrawGridBackground(false)
        setDrawValueAboveBar(false)
        legend.isEnabled = false
        description.isEnabled = false
        extraBottomOffset = 15f
    }
}

private class XAxisValueFormatter(private val stringXLabels: Array<String>) : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return stringXLabels.getOrNull(value.toInt()) ?: value.toString()
    }
}

private fun provideChartData(
    barColor: Int,
    startColor: Int,
    endColor: Int,
    drawable: Drawable
): BarData {
    //Entry represents each point
    val entryList = ChartDataProvider.dummyDataForX.mapIndexed { index, xValue ->
        BarEntry(
            xValue.toFloat(),
            ChartDataProvider.generateDummyDataForY()[index].toFloat(),
            drawable
        )
    }.apply {

    }
    //DataSet knows which items points belong together
    val dataSet = provideDataSet(entryList, ChartDataProvider.quarters, barColor).apply {
        setGradientColor(startColor, endColor)

    }
    //Data holds all data needed for chart
    return BarData(dataSet).apply {
        setDrawValues(false)
        barWidth = 0.3f
    }
}

private fun provideDataSet(entryList: List<BarEntry>, xAxisValues: Array<String>, barColor: Int) =
    BarDataSet(entryList, "LABEL").apply {
        color = barColor
    }

object ChartDataProvider {
    val dummyDataForX = (0..6).toList()
    fun generateDummyDataForY() = List(7) { Random.nextInt(1, 100) }
    val quarters = arrayOf("۱دی", "۲دی", "۳دی", "۴دی", "۵دی", "۶دی", "۷دی")
}
