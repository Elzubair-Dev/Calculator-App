package com.qco.calculator.operation

import kotlin.math.roundToInt

class Factorial {

    // Factorial is very simple, for example 5! = 5 x 4 x 3 x 2 x 1 => 120
    // But here I used roundToInt function, so if you enter 4.9 for example,
    // It will be rounded to 5, and if you entered 4.2, it will be rounded to 4.

    internal fun execute(number:Double):Double{
        var result = 1.0
        if(number > 0){
            for(i in 1 .. number.roundToInt()){
                result *= i
            }
        }
        else result = 0.0

        return result
    }
}