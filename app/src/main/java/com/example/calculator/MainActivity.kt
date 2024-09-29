package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape // Добавлено
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    var number1 by remember { mutableStateOf("") }
    var number2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Окна для ввода чисел
        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = number1,
                onValueChange = { number1 = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                placeholder = { Text(text = "Число 1") }
            )

            TextField(
                value = number2,
                onValueChange = { number2 = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                placeholder = { Text(text = "Число 2") }
            )

            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "=", modifier = Modifier.padding(end = 4.dp))
                Text(
                    text = result,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        // Контейнер с кнопками "-", "÷" и "*"
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Контейнер с кнопкой "+"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.3f) // Используйте вес для равномерного распределения
                        .height(161.dp)
                        .padding(8.dp)
                ) {
                    Button(
                        onClick = { calculateResult(number1, number2, "+") { result = it } },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .height(80.dp)
                            .padding(4.dp),
                        shape = RoundedCornerShape(0.dp) // Острые углы
                    ) {
                        Text(text = "+")
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f) // Используйте вес для равномерного распределения
                        .padding(8.dp)
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { calculateResult(number1, number2, "-") { result = it } },
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp),
                            shape = RoundedCornerShape(0.dp) // Острые углы
                        ) {
                            Text(text = "-")
                        }
                        Button(
                            onClick = { calculateResult(number1, number2, "÷") { result = it } },
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp),
                            shape = RoundedCornerShape(0.dp) // Острые углы
                        ) {
                            Text(text = "÷")
                        }
                    }

                    // Кнопка "*" под кнопками "-" и "÷"
                    Button(
                        onClick = { calculateResult(number1, number2, "*") { result = it } },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .height(80.dp), // Увеличение высоты для кнопки "*"
                        shape = RoundedCornerShape(0.dp) // Острые углы
                    ) {
                        Text(text = "*")
                    }
                }
            }

            // Строка с кнопкой "C"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        number1 = ""
                        number2 = ""
                        result = ""
                    },
                    modifier = Modifier
                        .weight(3f)
                        .padding(4.dp),
                    shape = RoundedCornerShape(0.dp) // Острые углы
                ) {
                    Text(text = "C")
                }
            }
        }
    }
}

fun calculateResult(num1: String, num2: String, operation: String, onResult: (String) -> Unit) {
    val n1 = num1.toDoubleOrNull()
    val n2 = num2.toDoubleOrNull()
    val result: String = when {
        n1 == null || n2 == null -> "Введите числа"
        operation == "+" -> (n1 + n2).toString()
        operation == "-" -> (n1 - n2).toString()
        operation == "*" -> (n1 * n2).toString()
        operation == "÷" -> if (n2 != 0.0) (n1 / n2).toString() else "Ошибка: деление на 0"
        else -> "Ошибка"
    }
    onResult(result)
}

@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    CalculatorTheme {
        MainContent(Modifier.fillMaxSize())
    }
}