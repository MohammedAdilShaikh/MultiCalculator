package com.example.multicalculator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CalcView()
                }
            }
        }
    }

    @Composable
    fun CalcView() {
        // Mutable state to hold the display value
        val display = remember { mutableStateOf("0") }

        Column(modifier = Modifier.background(Color.LightGray)) {
            // First row with CalcDisplay
            CalcDisplay(display)

            // Second row with operators
            Column {
                // Replace with your logic to create operation buttons
                // Example:
                CalcOperationButton("+", display)
                CalcOperationButton("-", display)
                CalcOperationButton("*", display)
                CalcOperationButton("/", display)
            }

            // Numeric buttons in rows
            for (i in 7 downTo 1 step 3) {
                CalcRow(display, i, 3)
            }

            // Row for zero and equals button
            Row {
                CalcNumericButton(0, display)
                CalcEqualsButton(display)
            }
        }
    }

    @Composable
    fun CalcRow(display: MutableState<String>, startNum: Int, numButtons: Int) {
        val endNum = startNum + numButtons

        Row(modifier = Modifier.padding(0.dp)) {
            for (i in startNum until endNum) {
                CalcNumericButton(i, display)
            }
        }
    }

    @Composable
    fun CalcDisplay(display: MutableState<String>) {
        androidx.compose.material.Text(
            text = display.value,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(5.dp)
        )
    }

    private @Composable
    fun Text(text: String, modifier: Modifier) {

    }

    @Composable
    fun CalcNumericButton(number: Int, display: MutableState<String>) {
        Button(
            onClick = { display.value += number.toString() },
            modifier = Modifier.padding(4.dp)
        ) {
            androidx.compose.material.Text(number.toString())
        }
    }

    @Composable
    fun CalcOperationButton(operation: String, display: MutableState<String>) {
        Button(
            onClick = { /* Implement operation logic here */ },
            modifier = Modifier.padding(4.dp)
        ) {
            androidx.compose.material.Text(operation)
        }
    }

    @Composable
    fun CalcEqualsButton(display: MutableState<String>) {
        Button(
            onClick = { display.value = "0" },
            modifier = Modifier.padding(4.dp)
        ) {
            androidx.compose.material.Text("=")
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
