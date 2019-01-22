package com.calculator.bmi.bmicalculator.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.calculator.bmi.bmicalculator.fragments.calculator.CalculatorFragment
import com.calculator.bmi.bmicalculator.fragments.result.ResultFragment

class PagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager), CalculatorFragment.OnBMICalculationFragmentListener {

    override fun onBMICalculated(result: Double) {
        val resultFragment = fragmentList.get(1)  as ResultFragment
        resultFragment.onBMICalculated(result)
    }

    private var fragmentList: MutableList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment = fragmentList.get(position)

    override fun getCount(): Int = fragmentList.size


    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }
}