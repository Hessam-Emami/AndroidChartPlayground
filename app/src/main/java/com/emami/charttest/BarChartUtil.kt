package com.emami.charttest

import CustomBarChartRenderer
import android.graphics.Color
import com.emami.charttest.BarChartUtil.DEFAULT_ANIMATION_DURATION
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter

object BarChartUtil {
    private const val EXTRA_BOTTOM_OFFSET = 15f
    private const val OFFSET_AXIS_Y = 5f
    private const val DEFAULT_BAR_WIDTH = 0.3f
    private const val DEFAULT_OFFSET_AXIS_Y = 10f
    private const val DEFAULT_AXIS_Y_MINIMUM = -1f
    const val DEFAULT_TEXT_SIZE = 15f
    const val DEFAULT_BAR_RADIUS = 25f
    const val DEFAULT_ANIMATION_DURATION = 300
    fun provideBarChartData(
        yValues: List<Float>,
        xValues: List<Float>,
        xLabelValues: Array<String>,
        startGradientColor: Int,
        endGradientColor: Int
    ): BarData {
        //Entry represents each point
        val entryList = xValues.mapIndexed { index, xValue ->
            BarEntry(
                xValue,
                yValues[index]
            )
        }
        //DataSet knows which items points belong together
        val dataSet = provideDataSet(entryList).apply {
            setGradientColor(startGradientColor, endGradientColor)
        }
        //Data holds all data needed for chart
        return BarData(dataSet).apply {
            setDrawValues(false)
            barWidth = DEFAULT_BAR_WIDTH
        }
    }

    internal fun setupXAxis(xAxis: XAxis, gridColor: Int, textSize: Float) {
        xAxis.apply {
            isEnabled = true
            setDrawGridLines(false)
            setAvoidFirstLastClipping(true)
            axisLineColor = gridColor
            position = XAxis.XAxisPosition.BOTTOM
            yOffset = OFFSET_AXIS_Y
            this.textSize = textSize
        }
    }

    fun setupYAxis(yAxis: YAxis, gridColor: Int, textSize: Float) {
        yAxis.apply {
            setGridColor(gridColor)
            setDrawAxisLine(false)
            axisMinimum = DEFAULT_AXIS_Y_MINIMUM
            xOffset = DEFAULT_OFFSET_AXIS_Y
            this.textSize = textSize
        }
    }

    fun styleChart(barChart: BarChart) {
        barChart.apply {
            setScaleEnabled(false)
            setTouchEnabled(false)
            isDragEnabled = false
            setDrawBorders(false)
            axisRight.isEnabled = false
            setFitBars(true)
            setDrawGridBackground(false)
            setDrawValueAboveBar(false)
            legend.isEnabled = false
            description.isEnabled = false
            extraBottomOffset = EXTRA_BOTTOM_OFFSET
        }
    }

}


class XAxisValueFormatter(private val stringXLabels: Array<String>) : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return stringXLabels.getOrNull(value.toInt()) ?: value.toString()
    }
}


private fun provideDataSet(
    entryList: List<BarEntry>
) =
    BarDataSet(entryList, "LABEL")

