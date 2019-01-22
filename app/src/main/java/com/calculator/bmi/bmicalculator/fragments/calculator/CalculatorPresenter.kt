package com.calculator.bmi.bmicalculator.fragments.calculator

import android.util.Log

class CalculatorPresenter(val view: ICalculatorView) : ICalculatorPresenter {

    companion object {
        const val FEET = 3048
        const val POUNDS = 4535
    }

    override fun calculateBMI(mass: Double, height: Double, isFeets: Boolean, isPounds: Boolean) {
        var heightInMeters = height / 100
        if (isFeets)
            heightInMeters = convertFeetsToMeters(height / 100)

        var massInKilograms = mass
        if (isPounds)
            massInKilograms = convertPoundsToKilograms(mass)

        val bmi = massInKilograms / (heightInMeters * heightInMeters)

        view.setBMIResult(bmi)
    }

    private fun convertFeetsToMeters(height: Double): Double {
        return height * FEET
    }

    private fun convertPoundsToKilograms(mass: Double): Double {
        return mass * POUNDS;
    }
}