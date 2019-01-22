package com.calculator.bmi.bmicalculator.fragments.calculator

interface ICalculatorPresenter {

    fun calculateBMI(mass: Double, height: Double, isFeets: Boolean, isPounds: Boolean)

}