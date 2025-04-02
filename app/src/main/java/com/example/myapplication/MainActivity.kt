package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.linearlayout);
        val idButton = listOf(
            R.id.Zero, R.id.One, R.id.Two, R.id.Three, R.id.Four, R.id.Five, R.id.Six, R.id.Seven, R.id.Eight, R.id.Nine,
            R.id.Decimal, R.id.X, R.id.Subtr, R.id.Addi,R.id.Divide,R.id.Equals,
        )
        val  operation = findViewById<TextView>(R.id.operation)
        val result = findViewById<TextView>(R.id.result)

        var In2 = ""
        var In1 = ""
        for (id in idButton){
            val textView = findViewById<TextView>(id)
                textView.setOnClickListener {
                    var tam = textView.text.toString()
                    In2+=tam
                    In1 += tam

                    if(tam in setOf("X","-","+","/","=")){
                       In1= tam
                    }
                    result.text = In1
                    if(tam in setOf("X","-","+","/","=")){
                        In1= ""
                    }

                    operation.text= In2
                }
        }

        val Equals = findViewById<TextView>(R.id.Equals)
            Equals.setOnClickListener {
                result.text = calculateExpression(In2).toString()
        }
        val Clear = findViewById<TextView>(R.id.Clear)
            Clear.setOnClickListener {
            In1 =""
            In2 =""
            operation.text=""
            result.text ="0"
        }

        val C = findViewById<TextView>(R.id.CE)
        C.setOnClickListener {
                In1 = In1.dropLast(1)
                In2 = In2.dropLast(1)
                result.text = In1
                operation.text= In2
        }

    }

    fun calculateExpression(expression: String): Double {

        val numbers = expression.split(Regex("[-+*/]"))
        val operators = expression.filter { it in "-+*/" }

        var result = numbers[0].toDouble()

        var index = 1
        for (operator in operators) {
            val number = numbers[index].toDouble()
            result = when (operator) {
                '+' -> result + number
                '-' -> result - number
                '*' -> result * number
                '/' -> result / number
                else -> result
            }
            index++
        }
        return result ?: 0.0
    }

}

