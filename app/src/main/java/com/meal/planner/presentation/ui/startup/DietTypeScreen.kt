package com.meal.planner.presentation.ui.startup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.meal.planner.model.enums.DietType

@Composable
fun DietTypeScreen(
    navigate: () -> Unit
) {
    var dietType by rememberSaveable { mutableStateOf(DietType.BULKING) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Select diet type",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            DietType.values().forEach { type ->
                val isSelected = dietType == type

                val buttonModifier = Modifier
                    .padding(vertical = 18.dp, horizontal = 6.dp)
                    .fillMaxWidth()
                    .height(55.dp)

                if (isSelected) {
                    Button(
                        modifier = buttonModifier,
                        onClick = { dietType = type },
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Next button icon"
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = type.name,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    OutlinedButton(
                        modifier = buttonModifier,
                        onClick = { dietType = type },
                    ) {
                        Text(text = type.name)
                    }
                }
            }
        }

        Button(
            onClick = navigate,
            contentPadding = PaddingValues(horizontal = 50.dp, vertical = 10.dp)
        ) {
            Icon(imageVector = Icons.Filled.Check, contentDescription = "Next button icon")
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Next")
        }
    }
}