package com.challenge.nimmsta.budget.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    slices: List<PieChartData>,
) {
    val total = slices.sumOf {
        it.value.toBigDecimal()
    }.toFloat()

    Canvas(modifier = modifier.fillMaxSize()) {
        var startAngle = 0f
        slices.forEach { item ->
            val sweepAngle = item.value / total * 360f
            drawIntoCanvas {
                drawArc(
                    color = item.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true
                )
            }
            startAngle += sweepAngle
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPieChart() {
    PieChart(
        modifier = Modifier,
        listOf(
            PieChartData(90f, Color.Green, "INCOME"),
            PieChartData(60f, Color.Red, "EXPENSE"),
            PieChartData(30f, Color.Blue, "BALANCE"),
        )
    )
}