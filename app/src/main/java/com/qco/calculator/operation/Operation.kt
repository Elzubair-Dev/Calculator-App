package com.qco.calculator.operation

sealed class Operation(val symbol:String, val priority: Int){
    object Add:Operation("+",1)
    object Sub:Operation("-", 1)
    object Multi:Operation("x", 2)
    object Divide:Operation("/", 2)
    object Mod:Operation("%", 3)
    object Pow:Operation("^", 3)
    object Log:Operation("log", 3)
    object Factorial:Operation("!", 3)
    object Open:Operation("(",4)
    object Close:Operation(")",4)
}
