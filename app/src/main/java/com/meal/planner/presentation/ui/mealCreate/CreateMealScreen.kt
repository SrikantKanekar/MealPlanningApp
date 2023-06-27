package com.meal.planner.presentation.ui.mealCreate

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.meal.planner.presentation.components.TimePickerDialog
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMealScreen(
    viewModel: CreateMealViewModel,
    dietId: String?,
    navigateBack: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var showTimePicker by remember { mutableStateOf(false) }
    val showingPicker = remember { mutableStateOf(true) }
    val state = rememberTimePickerState()
    val configuration = LocalConfiguration.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    PlainTooltipBox(tooltip = { Text(text = "Close") }) {
                        IconButton(
                            modifier = Modifier.tooltipAnchor(),
                            onClick = navigateBack
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "close"
                            )
                        }
                    }
                },
                title = { Text(text = "Create Meal") },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = uiState.name,
                    onValueChange = { viewModel.updateName(it) },
                    label = {
                        Text("Name")
                    },
                    supportingText = {
                        Text("Enter the name of the Meal")
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.clearFocus() }
                    )
                )

                Spacer(modifier = Modifier.height(50.dp))

                Column {
                    Text(
                        text = "Select time",
                        style = MaterialTheme.typography.labelLarge
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showTimePicker = true
                            },
                        value = uiState.timing.format(DateTimeFormatter.ISO_LOCAL_TIME),
                        readOnly = true,
                        onValueChange = { },
                        label = { Text("Time") },
                        singleLine = true
                    )
                }
            }

            Button(
                onClick = {
                    viewModel.createMeal(dietId)
                    navigateBack()
                },
                enabled = uiState.name.isNotBlank(),
                contentPadding = PaddingValues(horizontal = 50.dp, vertical = 10.dp)
            ) {
                Icon(imageVector = Icons.Filled.Check, contentDescription = "button icon")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Create")
            }
        }
    }

    if (showTimePicker) {
        TimePickerDialog(
            title = if (showingPicker.value) "Select Time " else "Enter Time",
            onCancel = { showTimePicker = false },
            onConfirm = {
                val localTime = LocalTime.of(state.hour, state.minute)
                viewModel.updateTime(localTime)
                showTimePicker = false
            },
            toggle = {
                if (configuration.screenHeightDp > 400) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .semantics {
                                isContainer = true
                            }
                    ) {
                        IconButton(
                            modifier = Modifier
                                .size(64.dp, 72.dp)
                                .align(Alignment.BottomStart)
                                .zIndex(5f),
                            onClick = { showingPicker.value = !showingPicker.value }) {
                            val icon = if (showingPicker.value) {
                                Icons.Outlined.Keyboard
                            } else {
                                Icons.Outlined.Schedule
                            }
                            Icon(
                                icon,
                                contentDescription = if (showingPicker.value) {
                                    "Switch to Text Input"
                                } else {
                                    "Switch to Touch Input"
                                }
                            )
                        }
                    }
                }
            }
        ) {
            if (showingPicker.value && configuration.screenHeightDp > 400) {
                TimePicker(state = state)
            } else {
                TimeInput(state = state)
            }
        }
    }
}