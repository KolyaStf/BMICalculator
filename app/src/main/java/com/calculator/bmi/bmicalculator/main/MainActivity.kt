package com.calculator.bmi.bmicalculator.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.view.View
import com.calculator.bmi.bmicalculator.R
import com.calculator.bmi.bmicalculator.fragments.calculator.CalculatorFragment
import com.calculator.bmi.bmicalculator.fragments.result.ResultFragment
import com.calculator.bmi.bmicalculator.viewpager.PagerAdapter
import com.calculator.bmi.bmicalculator.viewpager.ViewPagerListener
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ResultFragment.OnResultWatchedListener, CalculatorFragment.OnBMICalculationFragmentListener {

    companion object {
        private const val MIN_SCALE = 0.65f
        private const val MIN_ALPHA = 0.3f
    }

    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        supportActionBar?.setCustomView(R.layout.action_bar_title);

        pagerAdapter = PagerAdapter(supportFragmentManager)
        addPagerFragments()
        myViewPager.adapter = pagerAdapter
        myViewPager.setPageTransformer(true, this::zoomOutTransformation)
        myViewPager.addOnPageChangeListener(ViewPagerListener(this::onPageSelected))
        springDotsIndicator.setViewPager(myViewPager)

    }

    private fun addPagerFragments() {
        pagerAdapter.addFragment(CalculatorFragment())
        pagerAdapter.addFragment(ResultFragment())
    }

    private fun onPageSelected(position: Int) {
        when (position) {
            0 -> {

            }
            1 -> {

            }
        }
    }

    private fun zoomOutTransformation(page: View, position: Float) {
        when {
            position < -1 ->
                page.alpha = 0f
            position <= 1 -> {
                page.scaleX = Math.max(MIN_SCALE, 1 - Math.abs(position))
                page.scaleY = Math.max(MIN_SCALE, 1 - Math.abs(position))
                page.alpha = Math.max(MIN_ALPHA, 1 - Math.abs(position))
            }
            else -> page.alpha = 0f
        }
    }

    override fun onResultWatched() {
        myViewPager.setCurrentItem(0, true)
        (pagerAdapter.getItem(0) as CalculatorFragment).setToDefault()
    }

    override fun onBMICalculated(result: Double) {
        pagerAdapter.onBMICalculated(result)
        myViewPager.setCurrentItem(1, true)
    }
}
