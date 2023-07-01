package com.meal.planner.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun DashboardIndicator(
    modifier: Modifier = Modifier,
    name: String,
    unit: String,
    current: Int,
    target: Int,
    size: IndicatorSize
) {
    val sweepAngel = remember { Animatable(0f) }
    val currentValue = remember { Animatable(0, Int.VectorConverter) }
    val colorScheme = MaterialTheme.colorScheme

    LaunchedEffect(current) {
        var percent = current.toFloat() / target
        if (percent > 1) percent = 1f else if (percent < 0) percent = 0f

        this.launch {
            currentValue.animateTo(
                targetValue = current,
                animationSpec = tween(1500, easing = FastOutSlowInEasing)
            )
        }
        this.launch {
            sweepAngel.animateTo(
                targetValue = 280 * percent,
                animationSpec = tween(1500, easing = FastOutSlowInEasing)
            )
        }
    }

    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .align(Alignment.Center)
                .size(
                    when (size) {
                        IndicatorSize.SMALL -> 55.dp
                        IndicatorSize.MEDIUM -> 85.dp
                        IndicatorSize.LARGE -> 145.dp
                    }
                )
        ) {
            val stroke = Stroke(
                width = when (size) {
                    IndicatorSize.SMALL -> 6.dp.toPx()
                    IndicatorSize.MEDIUM -> 8.dp.toPx()
                    IndicatorSize.LARGE -> 13.dp.toPx()
                },
                cap = StrokeCap.Round
            )

            drawArc(
                color = colorScheme.surface,
                startAngle = 130f + sweepAngel.value,
                sweepAngle = 280f - sweepAngel.value,
                useCenter = false,
                style = stroke
            )

            drawArc(
                color = colorScheme.primary,
                startAngle = 130f,
                sweepAngle = sweepAngel.value,
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
            val ratio = current.toDouble() / target

            Text(
                text = "$current/$target $unit",
                style = style,
                color = when {
                    ratio < 0.95 -> MaterialTheme.colorScheme.error
                    ratio > 1.05 -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.onSurface
                }
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

enum class IndicatorSize {
    SMALL, MEDIUM, LARGE
}