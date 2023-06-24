package com.meal.planner.presentation.ui.dietCreate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.meal.planner.presentation.components.DayOfWeekButton
import java.time.DayOfWeek

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDietScreen(
    navigateBack: () -> Unit
) {

    var nameValue by rememberSaveable { mutableStateOf("") }
    var daysOfWeek by rememberSaveable { mutableStateOf<List<DayOfWeek>>(listOf()) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    fun toggleDays(day: DayOfWeek) {
        val list = ArrayList(daysOfWeek)
        if (list.contains(day)) {
            list.remove(day)
        } else {
            list.add(day)
        }
        daysOfWeek = list
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
                title = { Text(text = "Create Diet") },
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
                    value = nameValue,
                    onValueChange = { nameValue = it },
                    label = {
                        Text("Name")
                    },
                    supportingText = {
                        Text("Enter the name of the Diet")
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
                        text = "Select days of the week",
                        style = MaterialTheme.typography.labelLarge
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        DayOfWeek.values().map { day ->
                            DayOfWeekButton(
                                dayOfWeek = day,
                                onClick = { toggleDays(day) },
                                enabled = daysOfWeek.contains(day)
                            )
                        }
                    }
                }

            }

            Button(
                onClick = navigateBack,
                enabled = nameValue.isNotBlank(),
                contentPadding = PaddingValues(horizontal = 50.dp, vertical = 10.dp)
            ) {
                Icon(imageVector = Icons.Filled.Check, contentDescription = "button icon")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Create")
            }
        }
    }
}