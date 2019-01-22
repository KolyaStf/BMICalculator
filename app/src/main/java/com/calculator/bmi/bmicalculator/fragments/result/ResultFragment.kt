package com.calculator.bmi.bmicalculator.fragments.result

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.calculator.bmi.bmicalculator.R
import com.calculator.bmi.bmicalculator.fragments.calculator.CalculatorFragment
import kotlinx.android.synthetic.main.fragment_result.view.*

class ResultFragment : Fragment(), CalculatorFragment.OnBMICalculationFragmentListener {

    private var listener: OnResultWatchedListener? = null

    private lateinit var resultView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        initViews(view)
        initOnResultClickListener(view)
        return view
    }

    private fun initViews(view: View) {
        resultView = view.resultValue
    }

    private fun initOnResultClickListener(view: View) {
        view.btnOk.setOnClickListener { onOkPressed() }
    }

    fun setToDefault() {
        resultView.text = "0"
    }

    fun onOkPressed() {
        listener?.onResultWatched()
        setToDefault()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnResultWatchedListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnResultWatchedListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onBMICalculated(result: Double) {
        resultView?.text = result.toString()
    }

    interface OnResultWatchedListener {
        fun onResultWatched()
    }
}
