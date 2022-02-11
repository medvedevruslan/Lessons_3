package com.example.sealed_class_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.sealed_class_example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val currency = listOf(Rubel(), Dollar(), Euro())
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            currency.map { it.name })

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.currencySpinner.adapter = adapter

        initViews()

    }


    private fun initViews() {
        binding.apply {
            button.setOnClickListener {
                val low = currencyFromSelection()
                val high = currencyFromSelection()

                low.amount = lowEditText.text.toString().toDouble()
                high.amount = highEditText.text.toString().toDouble()

                // textView.text = String.format("%.2f руб", low.totalValueInRubles())
                textView.text = String.format(
                    "%.2f руб. ~ %.2f руб.", low.totalValueInRubles(), high.totalValueInRubles()
                )

            }
        }
    }

    private fun currencyFromSelection() =
        when (currency[binding.currencySpinner.selectedItemPosition]) {
            is Dollar -> Dollar()
            is Rubel -> Rubel()
            is Euro -> Euro()
            is Tugrik -> Tugrik()
        }

}

class Tugrik : AcceptedCurrency() {
    override val valueInRubels: Double = 4.0
}