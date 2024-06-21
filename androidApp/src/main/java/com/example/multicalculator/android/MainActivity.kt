package com.example.calculatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculatorapp.ui.theme.CalculatorAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    calcView()
                }
            }
        }
    }
}

@Composable
fun calcView() {
    var leftNumber by rememberSaveable { mutableStateOf(0) }
    var rightNumber by rememberSaveable { mutableStateOf(0) }
    var operation by rememberSaveable { mutableStateOf("") }
    var complete by rememberSaveable { mutableStateOf(false) }
    var displayText by rememberSaveable { mutableStateOf("") }

    if (complete && operation.isNotEmpty()) {
        var answer = 0
        when (operation) {
            "+" -> answer = leftNumber + rightNumber
            "-" -> answer = leftNumber - rightNumber
            "*" -> answer = leftNumber * rightNumber
            "/" -> answer = if (rightNumber != 0) leftNumber / rightNumber else 0
        }
        displayText = answer.toString()
    } else if (operation.isNotEmpty() && !complete) {
        displayText = rightNumber.toString()
    } else {
        displayText = leftNumber.toString()
    }

    fun numberPress(btnNum: Int) {
        if (complete) {
            leftNumber = 0
            rightNumber = 0
            operation = ""
            complete = false
        }
        if (operation.isNotBlank() && !complete) {
            rightNumber = rightNumber * 10 + btnNum
        } else if (operation.isBlank() && !complete) {
            leftNumber = leftNumber * 10 + btnNum
        }
    }

    fun operationPress(op: String) {
        if (!complete) {
            operation = op
        }
    }

    fun equalsPress() {
        complete = true
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = displayText, style = MaterialTheme.typography.h3)
        Spacer(modifier = Modifier.height(16.dp))

        calcRow(numbers = listOf(1, 2, 3), onPress = { number -> numberPress(number) })
        calcRow(numbers = listOf(4, 5, 6), onPress = { number -> numberPress(number) })
        calcRow(numbers = listOf(7, 8, 9), onPress = { number -> numberPress(number) })
        calcRow(numbers = listOf(0), onPress = { number -> numberPress(number) })

        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            calcOperationButton(op = "+", onPress = { operationPress(it) })
            calcOperationButton(op = "-", onPress = { operationPress(it) })
            calcOperationButton(op = "*", onPress = { operationPress(it) })
            calcOperationButton(op = "/", onPress = { operationPress(it) })
        }
        calcEqualsButton(onPress = { equalsPress() })
    }
}

@Composable
fun calcRow(numbers: List<Int>, onPress: (number: Int) -> Unit) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        for (number in numbers) {
            calcNumericButton(number = number, onPress = onPress)
        }
    }
}

@Composable
fun calcNumericButton(number: Int, onPress: (number: Int) -> Unit) {
    Button(onClick = { onPress(number) }) {
        Text(text = number.toString())
    }
}

@Composable
fun calcOperationButton(op: String, onPress: (operation: String) -> Unit) {
    Button(onClick = { onPress(op) }) {
        Text(text = op)
    }
}

@Composable
fun calcEqualsButton(onPress: () -> Unit) {
    Button(onClick = { onPress() }) {
        Text(text = "=")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculatorAppTheme {
        calcView()
    }
}
