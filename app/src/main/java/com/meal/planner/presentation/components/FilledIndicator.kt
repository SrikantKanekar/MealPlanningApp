package com.meal.planner.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun FilledIndicator(
    modifier: Modifier = Modifier,
    name: String,
    unit: String,
    value: Int,
    size: IndicatorSize
) {
    val colorScheme = MaterialTheme.colorScheme

    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .align(Alignment.Center)
                .size(
                    when (size) {
                        IndicatorSize.SMALL -> 65.dp
                        IndicatorSize.MEDIUM -> 85.dp
                        IndicatorSize.LARGE -> 145.dp
                    }
                )
        ) {
            val stroke = Stroke(
                width = when (size) {
                    IndicatorSize.SMALL -> 5.dp.toPx()
                    IndicatorSize.MEDIUM -> 8.dp.toPx()
                    IndicatorSize.LARGE -> 13.dp.toPx()
                },
                cap = StrokeCap.Round
            )

            drawArc(
                color = colorScheme.primary,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = stroke
            )
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val style = when (size) {
                IndicatorSize.SMALL -> MaterialTheme.typography.labelSmall
                IndicatorSize.MEDIUM -> MaterialTheme.typography.labelMedium
                IndicatorSize.LARGE -> MaterialTheme.typography.labelLarge
            }

            Text(
                text = "$value $unit",
                style = style,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = name,
                style = style,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}