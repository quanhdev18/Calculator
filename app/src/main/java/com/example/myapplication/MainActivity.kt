package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity(), View.OnClickListener {
    private lateinit var operationText: TextView
    private lateinit var resultText: TextView

    // Number buttons
    private lateinit var btnZero: TextView
    private lateinit var btnOne: TextView
    private lateinit var btnTwo: TextView
    private lateinit var btnThree: TextView
    private lateinit var btnFour: TextView
    private lateinit var btnFive: TextView
    private lateinit var btnSix: TextView
    private lateinit var btnSeven: TextView
    private lateinit var btnEight: TextView
    private lateinit var btnNine: TextView

    // Operation buttons
    private lateinit var btnAdd: TextView
    private lateinit var btnSubtract: TextView
    private lateinit var btnMultiply: TextView
    private lateinit var btnDivide: TextView
    private lateinit var btnEquals: TextView
    private lateinit var btnDecimal: TextView
    private lateinit var btnToggleSign: TextView

    // Control buttons
    private lateinit var btnClear: TextView
    private lateinit var btnCE: TextView
    private lateinit var btnBackspace: TextView

    private var currentInput = ""
    private var firstOperand = 0.0
    private var secondOperand = 0.0
    private var currentOperator = ""
    private var shouldResetInput = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.linearlayout)

        // Initialize views
        operationText = findViewById(R.id.operation)
        resultText = findViewById(R.id.result)

        // Number buttons
        btnZero = findViewById(R.id.Zero)
        btnOne = findViewById(R.id.One)
        btnTwo = findViewById(R.id.Two)
        btnThree = findViewById(R.id.Three)
        btnFour = findViewById(R.id.Four)
        btnFive = findViewById(R.id.Five)
        btnSix = findViewById(R.id.Six)
        btnSeven = findViewById(R.id.Seven)
        btnEight = findViewById(R.id.Eight)
        btnNine = findViewById(R.id.Nine)

        // Operation buttons
        btnAdd = findViewById(R.id.Addi)
        btnSubtract = findViewById(R.id.Subtr)
        btnMultiply = findViewById(R.id.X)
        btnDivide = findViewById(R.id.Divide)
        btnEquals = findViewById(R.id.Equals)
        btnDecimal = findViewById(R.id.Decimal)
        btnToggleSign = findViewById(R.id.Toggle)

        // Control buttons
        btnClear = findViewById(R.id.Clear)
        btnCE = findViewById(R.id.CE)
        btnBackspace = findViewById(R.id.BS)

        // Set click listeners
        btnZero.setOnClickListener(this)
        btnOne.setOnClickListener(this)
        btnTwo.setOnClickListener(this)
        btnThree.setOnClickListener(this)
        btnFour.setOnClickListener(this)
        btnFive.setOnClickListener(this)
        btnSix.setOnClickListener(this)
        btnSeven.setOnClickListener(this)
        btnEight.setOnClickListener(this)
        btnNine.setOnClickListener(this)

        btnAdd.setOnClickListener(this)
        btnSubtract.setOnClickListener(this)
        btnMultiply.setOnClickListener(this)
        btnDivide.setOnClickListener(this)
        btnEquals.setOnClickListener(this)
        btnDecimal.setOnClickListener(this)
        btnToggleSign.setOnClickListener(this)

        btnClear.setOnClickListener(this)
        btnCE.setOnClickListener(this)
        btnBackspace.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            // Number buttons
            R.id.Zero -> appendNumber("0")
            R.id.One -> appendNumber("1")
            R.id.Two -> appendNumber("2")
            R.id.Three -> appendNumber("3")
            R.id.Four -> appendNumber("4")
            R.id.Five -> appendNumber("5")
            R.id.Six -> appendNumber("6")
            R.id.Seven -> appendNumber("7")
            R.id.Eight -> appendNumber("8")
            R.id.Nine -> appendNumber("9")

            // Decimal point
            R.id.Decimal -> {
                if (currentInput.contains(".").not()) {
                    if (currentInput.isEmpty()) {
                        currentInput = "0."
                    } else {
                        currentInput += "."
                    }
                    updateDisplay()
                }
            }

            // Toggle sign
            R.id.Toggle -> {
                if (currentInput.isNotEmpty() && currentInput != "0") {
                    currentInput = if (currentInput.startsWith("-")) {
                        currentInput.substring(1)
                    } else {
                        "-$currentInput"
                    }
                    updateDisplay()
                }
            }

            // Operator buttons
            R.id.Addi -> setOperator("+")
            R.id.Subtr -> setOperator("-")
            R.id.X -> setOperator("×")
            R.id.Divide -> setOperator("÷")

            // Equals button
            R.id.Equals -> calculateResult()

            // Clear buttons
            R.id.Clear -> clearAll()
            R.id.CE -> clearEntry()
            R.id.BS -> backspace()
        }
    }

    private fun appendNumber(number: String) {
        if (shouldResetInput) {
            currentInput = ""
            shouldResetInput = false
        }

        if (currentInput == "0") {
            currentInput = number
        } else {
            currentInput += number
        }

        updateDisplay()
    }

    private fun setOperator(operator: String) {
        if (currentInput.isNotEmpty()) {
            if (currentOperator.isNotEmpty()) {
                calculateResult()
            }

            firstOperand = currentInput.toDouble()
            currentOperator = operator
            shouldResetInput = true
            updateOperationDisplay()
        } else if (currentOperator.isNotEmpty()) {
            // Allow changing the operator if no new number has been entered
            currentOperator = operator
            updateOperationDisplay()
        }
    }

    private fun calculateResult() {
        if (currentOperator.isEmpty() || currentInput.isEmpty()) return

        secondOperand = currentInput.toDouble()
        val result = when (currentOperator) {
            "+" -> firstOperand + secondOperand
            "-" -> firstOperand - secondOperand
            "×" -> firstOperand * secondOperand
            "÷" -> {
                if (secondOperand == 0.0) {
                    "Error"
                } else {
                    firstOperand / secondOperand
                }
            }
            else -> return
        }

        currentInput = if (result == "Error") {
            "Error"
        } else {
            // Remove .0 if it's an integer
            if (result == result.toString()) {
                result.toString()
            } else {
                result.toString()
            }
        }

        currentOperator = ""
        shouldResetInput = true
        updateDisplay()
        updateOperationDisplay()
    }

    private fun clearAll() {
        currentInput = ""
        firstOperand = 0.0
        secondOperand = 0.0
        currentOperator = ""
        shouldResetInput = false
        updateDisplay()
        updateOperationDisplay()
    }

    private fun clearEntry() {
        currentInput = ""
        updateDisplay()
    }

    private fun backspace() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length - 1)
            if (currentInput.isEmpty()) {
                currentInput = "0"
            }
            updateDisplay()
        }
    }

    private fun updateDisplay() {
        resultText.text = if (currentInput.isEmpty()) "0" else currentInput
    }

    private fun updateOperationDisplay() {
        val operation = if (firstOperand % 1 == 0.0) {
            firstOperand.toInt().toString()
        } else {
            firstOperand.toString()
        } + " $currentOperator"

        operationText.text = operation
    }
}