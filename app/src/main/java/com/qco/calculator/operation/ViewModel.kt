package com.qco.calculator.operation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.math.ln
import kotlin.math.pow

class ViewModel : ViewModel() {
    var state by mutableStateOf(State())
        private set

    // set of variables that we need
    private val expression: MutableList<String> = mutableListOf()
    private var stack: MutableList<Operation> = mutableListOf()
    private var newElement = ""
    private var prevElement = ""
    private var openBracket = 0

    fun onAction(action: Action) {
        when (action) {
            is Action.Number -> addNumber(action.number)
            is Action.Point -> addPoint()
            is Action.Operations -> addOperation(action.operation)
            is Action.Delete -> delete()
            is Action.Clear -> clear()
            is Action.Calculate -> calculate()
        }
    }

    //----------Functions----------//
    private fun addNumber(number: Int) {

        // * We will add number directly to expression in two cases:
        //      1- if expression List is Empty
        //      2- if expression's last element was operator

        // ** otherwise we need to modify last number
        // for example if I want to enter 64, I will press 6 and it will be added as last element to
        // expression => [6], hence when I press 4 it will be added as a new last element too like => [6,4], so to Avoid that
        // I must remove 6 and concatenate it with 4 and add it again as [64].

        prevElement = newElement

        // case *
        if (expression.isEmpty() || !isNumber(prevElement)) {
            addNumberDirectly(number = number)
        }

        // case **
        else if (isNumber(prevElement)) {
            modifyNumber(number = number)
        }

    }

    private fun addPoint() {
        // There are only two decimal point cases:
        // 1- when the expression is empty or the last element is operator, in this case we add "0.".
        // 2- when last element is number that doesn't contain "." .

        // case 1
        if (expression.isEmpty() || !isNumber(newElement)) {
            addZeroDot()
        }
        // case 2
        else if (isNumber(newElement) && !newElement.contains('.')) {
            addDot()
        }
    }

    private fun addOperation(operation: Operation) {
        // operation are divided to 3 categories:
        // 1- open bracket.
        // 2- close bracket.
        // 3- rest of operators.

        // case 1 (open bracket): conditions
        // - if expression list is empty
        // - or previous element is operator
        // - previous element can be any operator but not open bracket.

        if (operation.symbol == "(") {
            if (newElement != "(") {
                if (expression.isEmpty() || !isNumber(newElement)) {
                    openBracket += 1
                    //this is what will appear on result TV
                    prevElement = newElement
                    newElement = operation.symbol
                    state = state.copy(
                        result = state.result + newElement
                    )

                    addToStack(operation = operation)
                }
            }
        }

        // case 2 (close bracket): conditions
        // - expression list must be not empty
        // - previous element can be either a number or close bracket
        else if (operation.symbol == ")") {
            if (expression.isNotEmpty()) {
                if (isNumber(number = newElement) || newElement == ")") {
                    //this is what will appear on result TV
                    prevElement = newElement
                    newElement = operation.symbol
                    state = state.copy(
                        result = state.result + newElement
                    )

                    openBracket -= 1
                    dropUntilOpenBracket()
                }
            }
        }

        // case 3 (rest of operators): conditions
        // - expression must be not empty
        // - last element must be a number
        else {
            if (expression.isNotEmpty() && isNumber(newElement)) {

                //this is what will appear on result TV
                prevElement = newElement
                newElement = operation.symbol
                state = state.copy(
                    result = state.result + newElement
                )
                //* to add operator to stack directly, one of three conditions must be true:
                //  1- stack is empty .
                //  2- new operator's priority is higher than stack last operator's priority .
                //  3- last operator equals "(" .
                //** other than that we need to drop last operator/s from stack .
                if (stack.isEmpty() || operation.priority > stack.last().priority || stack.last().symbol == "(") {
                    addToStack(operation = operation)
                } else {
                    dropThenAddToStack(operator = operation)
                }
            }
        }
    }

    private fun delete() {
        //deleting is like a reverse operation to add number and add operator.

        // we can delete in only one case which is if result is not empty.
        if (state.result.isNotEmpty()) {

            // is there was only one element in the result, we should call clear function
            // to delete and reset variables.
            if (state.result.length == 1){
                clear()
            }

            // other than we perform an operation that's similar to undo operation
            else{
                val last = state.result.last().toString()

                // if last element in result is number
                if (isNumber(last)) {

                    // if last element in the expression list is number
                    if (isNumber(expression.last())) {
                        // if that element length is higher than one, that mean we should modify it
                        if (expression.last().length > 1) {

                            modifiedDeletion()

                        }

                        // if last element length is equal one, that mean we should directly delete it
                        else {

                            directDeletion()

                        }
                    }

                    // if last element in the expression list is not number
                    else {
                        // in this case definitely penultimate element is number
                        // we should check, if penultimate element length is higher than one, to modify deletion
                        val penultimateIndex = expression.size - 2
                        if (expression[penultimateIndex].length > 1) {
                            state = state.copy(
                                result = state.result.dropLast(1)
                            )
                            //modify
                            var temp = expression[penultimateIndex]
                            temp = temp.dropLast(1)
                            expression.removeAt(penultimateIndex)
                            //add it to penultimate
                            expression.add(index = penultimateIndex, element = temp)

                        }

                        // if penultimate element length equal to one, directly delete it.
                        else {
                            state = state.copy(
                                result = state.result.dropLast(1)
                            )
                            expression.removeAt(expression.size - 2)

                        }
                    }
                }

                // if last element in result is not number
                else {

                    // we check, if stack is empty and expression last element is operator,
                    // then remove expression last element.
                    if (stack.isEmpty()) {
                        if (!isNumber(expression.last())) {
                            state = state.copy(
                                result = state.result.dropLast(1)
                            )
                            expression.removeLast()
                            newElement = expression.last()
                        }
                    }

                    // if stack is not empty.
                    // if last operator in stack is open bracket, we will remove it and newElement will equal stack last operator
                    // else we will remove it and newElement will equal expression last element.
                    else {
                        state = state.copy(
                            result = state.result.dropLast(1)
                        )
                        if (stack.last().symbol == "("){
                            stack.removeLast()
                            openBracket --
                            if (stack.isNotEmpty()){
                                newElement = stack.last().symbol
                            }
                        }
                        else{
                            stack.removeLast()
                            newElement = expression.last()
                        }
                    }
                }
            }

        }
    }

    private fun clear() {
        // restoring initial values
        state = State()
        expression.clear()
        stack.clear()
        prevElement = ""
        newElement = ""
        openBracket = 0
    }

    private fun calculate() {

        // to calculate an expression
        // 1- first of all open brackets number must equal close brackets number.
        // 2- then expression must be in standard form, like 2 numbers and 1 operator
        // or 1 number and 1 operator in case of factorial.

        // case 1:
        if (openBracket == 0) {

            // case 2:
            if (
                expression.size == 1
                && stack.last().symbol == "!"
                || expression.size >= 2
            ) {

                // if there are still operators in stack,
                // it should be popped out and added to expression list.
                if (stack.isNotEmpty()) {
                    while (stack.isNotEmpty()) {
                        val temp = stack.last().symbol
                        expression.add(temp)
                        stack.removeLast()
                    }

                }

                // giving the value of the expression evaluation to result variable.
                val result = expressionEvaluation(expression = expression)

                // giving the result value to state result variable
                state = state.copy(
                    result = result
                )

                // setting prevElement, newElement and stack to be ready for more expressions
                prevElement = ""
                newElement = expression.last()
                stack.clear()
            }
        }
    }

    //----------Sub Functions----------//
    private fun isNumber(number: String): Boolean {
        // isNumber initial value is 1, so if it find numbers or decimal point it will be multiplied by one.
        // other than that it will be multiplied by zero.
        // then return the boolean value, if isNumber is equal one it will be true, other than that it will be false.
        var isNumber = 1
        number.forEach { digit ->
            isNumber *= if (digit.isDigit() || digit == '.') {
                1
            } else 0
        }
        return isNumber == 1
    }

    private fun addNumberDirectly(number: Int) {

        //this is what will appear on result TV
        newElement = number.toString()
        state = state.copy(
            result = state.result + newElement
        )

        //this is what will be processed on background
        expression.add(element = newElement)
    }

    private fun modifyNumber(number: Int) {

        //this is what will appear on result TV
        newElement += number.toString()
        val length = newElement.length
        state = state.copy(result = state.result.dropLast(length - 1))
        state = state.copy(result = state.result + newElement)

        //this is what will be processed on background
        expression.removeLast()
        expression.add(element = newElement)
    }

    private fun directDeletion(){
        //this is what will appear on result TV
        state = state.copy(
            result = state.result.dropLast(1)
        )

        // deletion
        expression.removeLast()
        newElement = if (stack.isEmpty()) {
            ""
        } else {
            stack.last().symbol
        }
    }

    private fun modifiedDeletion(){
        //this is what will appear on result TV
        state = state.copy(
            result = state.result.dropLast(1)
        )
        //modify
        var temp = expression.last()
        temp = temp.dropLast(1)
        expression.removeLast()
        expression.add(element = temp)
        //new = modified number
        newElement = temp
    }
    private fun addZeroDot() {

        //this is what will appear on result TV
        prevElement = newElement
        newElement = "0."
        state = state.copy(
            result = state.result + newElement
        )

        //this is what will be processed on background
        expression.add(element = newElement)
    }

    private fun addDot() {

        //this is what will appear on result TV
        prevElement = newElement
        newElement += "."
        val length = newElement.length
        state = state.copy(
            result = state.result.dropLast(length - 1)
        )
        state = state.copy(
            result = state.result + newElement
        )

        //this is what will be processed on background
        expression.removeLast()
        expression.add(element = newElement)
    }

    private fun dropUntilOpenBracket() {
        // repeat the statements until you find open bracket or stack is empty
        while (stack.isNotEmpty() && stack.last().symbol != "(") {

            val temp = stack.last().symbol

            expression.add(temp)

            stack.removeLast()
        }

        // then drop open bracket away
        if (stack.last().symbol == "(") {
            stack.removeLast()
        }
    }

    private fun addToStack(operation: Operation) {
        stack.add(operation)
    }

    private fun dropThenAddToStack(operator: Operation) {

        // this is the operation of dropping last operation from stack and add it to expression list
        val temp = stack.last().symbol
        expression.add(temp)
        stack.removeLast()

        // then check again
        // if stack is not empty
        // or new operator is more prior than stack's last operator
        // or last operator in stack is open bracket
        // in those 3 cases we directly add operator to stack
        if (stack.isEmpty() || operator.priority > stack.last().priority || stack.last().symbol == "(") {
            addToStack(operation = operator)
        }

        // other than we call the function again
        else {
            dropThenAddToStack(operator = operator)
        }

    }

    private fun expressionEvaluation(expression: MutableList<String>): String {
        // to separate first operator with it's operand or operands.
        var i = 0
        kotlin.run loop@{
            // so when ever you find not number char, which is operator
            // save this operator's index and return.
            expression.forEachIndexed { index, char ->
                if (!isNumber(char)) {
                    i = index
                    return@loop
                }
            }
        }
        // getting the operator
        val operator = expression[i]

        // if operator is other than factorial operator, that means it takes two operands
        if (operator != "!") {
            // getting first and second numbers
            val second = expression[i - 1]
            val first = expression[i - 2]

            // perform the calculation
            val resultNumber = performCalculation(first = first, second = second, operator = operator)

            // deleting the operator, second number and first number from expression.
            expression.removeAt(index = i)
            expression.removeAt(index = i - 1)
            expression.removeAt(index = i - 2)

            // adding result number to expression in first number index.
            expression.add(index = i - 2, element = resultNumber)

            // if expression's size is higher than one, that means there are still numbers and operators
            // that doesn't executed yet, so we call expressionEvaluation function again.
            if (expression.size > 1) {
                expressionEvaluation(expression = expression)
            }
        }
        // if operator is factorial operator, that means it takes one operand
        else {
            // getting first number.
            val first = expression[i - 1]

            // perform the calculation
            val resultNumber = performCalculation(first = first, operator = operator)

            // deleting the operator and first number from expression.
            expression.removeAt(index = i)
            expression.removeAt(index = i - 1)

            // adding result number to expression in first number index.
            expression.add(index = i - 1, element = resultNumber)

            // if expression's size is higher than one, that means there are still numbers and operators
            // that doesn't executed yet, so we call expressionEvaluation function again.
            if (expression.size > 1) {
                expressionEvaluation(expression = expression)
            }
        }

        // when you get to this point, that means expression size is one, so we return it.
        return expression[0]
    }

    private fun performCalculation(first: String, second: String = "", operator: String): String {

        val result = when (operator) {
            "+" -> {
                first.toDouble() + second.toDouble()
            }
            "-" -> {
                first.toDouble() - second.toDouble()
            }
            "x" -> {
                first.toDouble() * second.toDouble()
            }
            "/" -> {
                first.toDouble() / second.toDouble()
            }
            "%" -> {
                first.toDouble() % second.toDouble()
            }
            "^" -> {
                first.toDouble().pow(second.toDouble())
            }
            "log" -> {
                ln(first.toDouble()) / ln(second.toDouble())
            }
            "!" -> {
                Factorial().execute(first.toDouble())
            }
            else -> {
                0.0
            }
        }
        return result.toString()
    }
}