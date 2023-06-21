package com.meal.planner.presentation.ui.food

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.meal.planner.model.enums.FoodScreenType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodScreen(
    navigateBack: () -> Unit,
    foodScreenType: FoodScreenType
) {

    var menuExpanded by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var nameValue by rememberSaveable { mutableStateOf("") }
    var proteinValue by rememberSaveable { mutableStateOf("") }
    var carbValue by rememberSaveable { mutableStateOf("") }
    var fatValue by rememberSaveable { mutableStateOf("") }
    var quantityValue by rememberSaveable { mutableStateOf("") }

    fun isValidDouble(value: String) = value.toDoubleOrNull() != null

    LaunchedEffect(Unit) {
        if (foodScreenType == FoodScreenType.ADD) focusRequester.requestFocus()
    }

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
                title = {
                    Text(
                        text = if (foodScreenType == FoodScreenType.ADD)
                            "Add Food"
                        else
                            "Edit Food"
                    )
                },
                actions = {
                    if (foodScreenType == FoodScreenType.EDIT) {
                        Box {
                            PlainTooltipBox(tooltip = { Text(text = "More") }) {
                                IconButton(
                                    modifier = Modifier.tooltipAnchor(),
                                    onClick = { menuExpanded = true }
                                ) {
                                    Icon(
                                        Icons.Default.MoreVert,
                                        contentDescription = "More"
                                    )
                                }
                            }
                            DropdownMenu(
                                expanded = menuExpanded,
                                onDismissRequest = { menuExpanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Delete Food") },
                                    onClick = {
                                        menuExpanded = false
                                        navigateBack()
                                    }
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 30.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                value = nameValue,
                onValueChange = { nameValue = it },
                label = {
                    Text(text = "Name")
                },
                supportingText = {
                    Text("Enter name of the Food")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                )
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = proteinValue,
                onValueChange = { proteinValue = it },
                label = {
                    Text(text = "Proteins")
                },
                isError = proteinValue.isNotEmpty() && !isValidDouble(proteinValue),
                supportingText = {
                    Text(
                        if (proteinValue.isNotEmpty() && !isValidDouble(proteinValue))
                            "Please enter a valid value"
                        else
                            "Proteins per 100g in grams"
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                )
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = carbValue,
                onValueChange = { carbValue = it },
                label = {
                    Text(text = "Carbs")
                },
                isError = carbValue.isNotEmpty() && !isValidDouble(carbValue),
                supportingText = {
                    Text(
                        if (carbValue.isNotEmpty() && !isValidDouble(carbValue))
                            "Please enter a valid value"
                        else
                            "Carbs per 100g in grams"
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                )
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = fatValue,
                onValueChange = { fatValue = it },
                label = {
                    Text(text = "Fats")
                },
                isError = fatValue.isNotEmpty() && !isValidDouble(fatValue),
                supportingText = {
                    Text(
                        if (fatValue.isNotEmpty() && !isValidDouble(fatValue))
                            "Please enter a valid value"
                        else
                            "Fats per 100g in grams"
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                )
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = quantityValue,
                onValueChange = { quantityValue = it },
                label = {
                    Text(text = "Quantity")
                },
                isError = quantityValue.isNotEmpty() && !isValidDouble(quantityValue),
                supportingText = {
                    Text(
                        if (quantityValue.isNotEmpty() && !isValidDouble(quantityValue))
                            "Please enter a valid value"
                        else
                            "Quantity in grams"
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )

            Button(
                onClick = navigateBack,
                enabled = nameValue.isNotBlank() &&
                        isValidDouble(proteinValue) &&
                        isValidDouble(carbValue) &&
                        isValidDouble(fatValue) &&
                        isValidDouble(quantityValue),
                contentPadding = PaddingValues(horizontal = 50.dp, vertical = 10.dp)
            ) {
                Icon(imageVector = Icons.Filled.Check, contentDescription = "button icon")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = if (foodScreenType == FoodScreenType.ADD) "Add" else "Update")
            }
        }
    }
}
