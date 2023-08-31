package com.qco.calculator.operation

sealed class Action{
    data class Number(val number:Int):Action()
    object Clear:Action()
    object Delete:Action()
    object Point:Action()
    object Calculate:Action()
    data class Operations(val operation: Operation):Action()
}
