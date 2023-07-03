package com.meal.planner.presentation.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.meal.planner.model.enums.DietType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var weightDialogOpened by remember { mutableStateOf(false) }
    var dietTypeDialogOpened by remember { mutableStateOf(false) }

    val isValidWeight = uiState.weight.toDoubleOrNull() != null

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                },
                title = { Text(text = "Settings") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {

            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { weightDialogOpened = true }
                        .padding(horizontal = 22.dp, vertical = 18.dp)
                ) {
                    Text(
                        text = "Set target weight",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { dietTypeDialogOpened = true }
                        .padding(horizontal = 22.dp, vertical = 18.dp)

                ) {
                    Text(
                        text = "Set Diet type",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }

    if (weightDialogOpened) {
        AlertDialog(
            onDismissRequest = {
                weightDialogOpened = false
                viewModel.resetWeight()
            },
            title = { Text(text = "Set target weight") },
            text = {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    value = uiState.weight,
                    onValueChange = { viewModel.updateWeight(it) },
                    placeholder = {
                        Text(text = "Example: 65kg")
                    },
                    isError = uiState.weight.isNotEmpty() && !isValidWeight,
                    supportingText = {
                        Text(
                            if (uiState.weight.isNotEmpty() && !isValidWeight)
                                "Please enter a valid weight"
                            else
                                "Enter your target weight in kg"
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(
                        onDone = { if (isValidWeight) viewModel.saveWeight() }
                    )
                )
            },
            confirmButton = {
                TextButton(
                    enabled = isValidWeight,
                    onClick = {
                        viewModel.saveWeight()
                        weightDialogOpened = false
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        weightDialogOpened = false
                        viewModel.resetWeight()
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (dietTypeDialogOpened) {
        AlertDialog(
            onDismissRequest = { dietTypeDialogOpened = false },
            title = { Text(text = "Set Diet type") },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    DietType
                        .values()
                        .filter { it != DietType.NULL }
                        .forEach { type ->
                            val isSelected = uiState.dietType == type

                            val buttonModifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)

                            if (isSelected) {
                                Button(
                                    modifier = buttonModifier,
                                    onClick = { viewModel.updateDietType(type) },
                                ) {
                                    Box(modifier = Modifier.fillMaxWidth()) {
                                        Icon(
                                            imageVector = Icons.Filled.Check,
                                            contentDescription = "button icon"
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
                                    onClick = { viewModel.updateDietType(type) },
                                ) {
                                    Text(text = type.name)
                                }
                            }
                        }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { dietTypeDialogOpened = false }
                ) {
                    Text("Close")
                }
            }
        )
    }
}