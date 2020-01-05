package com.emami.charttest

import CustomBarChartRenderer
import android.graphics.Color
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData


fun BarChart.setupBarChart(
    axisColor: Int,
    backgroundColor: Int = Color.WHITE,
    textSize: Float = BarChartUtil.DEFAULT_TEXT_SIZE,
    barRadius: Float = BarChartUtil.DEFAULT_BAR_RADIUS,
    emptyText: String? = null
) {
    this.apply {
        renderer = CustomBarChartRenderer(barRadius, this, this.animator, this.viewPortHandler)
        setBackgroundColor(backgroundColor)
        emptyText?.let {
            setNoDataText(emptyText)
        }
    }
    BarChartUtil.styleChart(this)
    BarChartUtil.setupXAxis(xAxis, axisColor, textSize)
    BarChartUtil.setupYAxis(axisLeft, axisColor, textSize)
}

fun BarChart.showBarChart(data: BarData) {
    this.data = data
    xAxis.valueFormatter = XAxisValueFormatter(ChartDummyDataProvider.quarters)
    animateY(BarChartUtil.DEFAULT_ANIMATION_DURATION)
    notifyDataSetChanged()
    invalidate()
}
