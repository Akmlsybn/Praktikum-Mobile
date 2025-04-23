package com.example.tipcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tipOptions = listOf("15%", "18%", "20%")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, tipOptions)

        binding.autoCompleteTip.setAdapter(adapter)
        binding.autoCompleteTip.setText("15%", false)
        binding.autoCompleteTip.setOnClickListener {
            binding.autoCompleteTip.showDropDown()
        }
        binding.autoCompleteTip.setOnItemClickListener { _, _, _, _ ->
            updateTip()
        }
        binding.etBill.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = updateTip()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        binding.switchRound.setOnCheckedChangeListener { _, _ -> updateTip() } }
    private fun updateTip() {
        val amount = binding.etBill.text.toString().toDoubleOrNull() ?: 0.0
        val tipText = binding.autoCompleteTip.text.toString()
        val tipPercent = tipText.removeSuffix("%").toDoubleOrNull() ?: 15.0
        val roundUp = binding.switchRound.isChecked
        val tip = calculateTip(amount, tipPercent, roundUp)
        binding.tvTotalTip.text = getString(R.string.tip_amount, tip)
    }
    private fun calculateTip(amount: Double, tipPercent: Double, roundUp: Boolean): String {
        var tip = tipPercent / 100 * amount
        if (roundUp) {
            tip = ceil(tip)
        }
        return NumberFormat.getCurrencyInstance().format(tip)
    }
}