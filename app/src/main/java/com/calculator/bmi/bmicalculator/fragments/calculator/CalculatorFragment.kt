package com.calculator.bmi.bmicalculator.fragments.calculator

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import com.calculator.bmi.bmicalculator.R
import com.suke.widget.SwitchButton
import kotlinx.android.synthetic.main.fragment_calculator.view.*


class CalculatorFragment : Fragment(), ICalculatorView {

    private var listener: OnBMICalculationFragmentListener? = null

    private lateinit var presenter: ICalculatorPresenter

    private lateinit var isPoundsSwitch: SwitchButton

    private lateinit var isFeetsSwitch: SwitchButton

    private lateinit var weight: TextView

    private lateinit var height: TextView

    private lateinit var heightSeekbar: SeekBar

    private var isPounds = false

    private var isFeets = true

    private val step = 1
    private val max = 400
    private val min = 0

    private var heightD: Double = 0.0
    private var weightD: Double = 0.0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)
        presenter = CalculatorPresenter(this)
        initViews(view)
        setSeekBarMinMax()
        setToDefault()
        setAddOrReduceWeightClickListener(view)
        setOnSeekBarChangeListener()
        initOnCheckedChangeListeners(view)
        setOnResultClickListener(view)
        return view
    }

    private fun setOnResultClickListener(view: View) {
        view.btnCalculate.setOnClickListener { presenter.calculateBMI(weightD, heightD, isFeets, isPounds) }
    }

    private fun setAddOrReduceWeightClickListener(view: View) {
        view.addWeightIv.setOnClickListener {
            increaseWeight();
        }

        view.reduceWeightIv.setOnClickListener {
            decreaseWeight();
        }
    }

    private fun initOnCheckedChangeListeners(view: View) {
        isPoundsSwitch.setOnCheckedChangeListener { view, isChecked ->
            isPounds = isChecked
        }

        isFeetsSwitch.setOnCheckedChangeListener { view, isChecked ->
            isFeets = !isChecked
        }
    }

    private fun setSeekBarMinMax() {
        heightSeekbar.max = (max - min) / step
    }

    private fun setOnSeekBarChangeListener() {
        heightSeekbar.setOnSeekBarChangeListener(
                object : OnSeekBarChangeListener {
                    override fun onStopTrackingTouch(seekBar: SeekBar) {}

                    override fun onStartTrackingTouch(seekBar: SeekBar) {}

                    override fun onProgressChanged(seekBar: SeekBar, progress: Int,
                                                   fromUser: Boolean) {
                        val value = min + progress * step
                        heightD = value.toDouble()
                        height.text = value.toString()
                    }
                }
        )
    }

    private fun initViews(view: View) {
        isPoundsSwitch = view.kgOrPoundSwitch
        isFeetsSwitch = view.feetsOrCmSwitch
        weight = view.weightValue
        height = view.heightValue
        heightSeekbar = view.seekBar
    }

    private fun onCalculatePressed(result: Double) {
        listener?.onBMICalculated(result)
    }

    fun setToDefault() {
        weight.text = "0"
        height.text = "0"
        isPoundsSwitch.isChecked = false
        isFeetsSwitch.isChecked = false
        heightSeekbar.progress = 0
        weightD = 0.0
        heightD = 0.0
    }

    private fun decreaseWeight() {
        var weightD = weight.text.toString().toDouble()
        if (weightD != 0.0)
            weightD--
        weight.text = weightD.toString()
        this.weightD = weightD

    }

    private fun increaseWeight() {
        var weightD = weight.text.toString().toDouble()
        weightD++
        weight.text = weightD.toString()
        this.weightD = weightD
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBMICalculationFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnBMICalculationFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun setBMIResult(bmi: Double) {
        onCalculatePressed(bmi)
    }

    interface OnBMICalculationFragmentListener {
        fun onBMICalculated(result: Double)
    }
}
