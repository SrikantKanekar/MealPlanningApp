package com.meal.planner.presentation.ui.startup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun EnterWeightScreen(
    viewModel: StartupViewModel,
    navigate: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    val isValidWeight = uiState.weight.toDoubleOrNull() != null

    fun saveAndNavigate() {
        viewModel.saveWeight()
        navigate()
    }

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
                text = "Enter weight",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.weight,
                onValueChange = { viewModel.updateWeight(it) },
                placeholder = {
                    Text(text = "Example: 60kg")
                },
                isError = uiState.weight.isNotEmpty() && !isValidWeight,
                supportingText = {
                    Text(
                        if (uiState.weight.isNotEmpty() && !isValidWeight)
                            "Please enter a valid weight"
                        else
                            "Enter your current weight in kg"
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(
                    onDone = { if (isValidWeight) saveAndNavigate() }
                )
            )
        }

        Button(
            onClick = { saveAndNavigate() },
            enabled = isValidWeight,
            contentPadding = PaddingValues(horizontal = 50.dp, vertical = 10.dp)
        ) {
            Icon(imageVector = Icons.Filled.Check, contentDescription = "Next button icon")
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Next")
        }
    }
}