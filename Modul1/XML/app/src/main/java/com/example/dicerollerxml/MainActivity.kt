package com.example.dicerollerxml

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.dicerollerxml.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            rollDice()
        }
    }

    private fun rollDice()
    {
        val dice = Dice(6)
        val sides = dice.diceRoll()
        val sides2 = dice.diceRoll()
        val button = findViewById<Button>(R.id.button)
        val dR1 = when(sides)
        {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        val dR2 = when (sides2)
        {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        binding.imageView1.setImageResource(dR1)
        binding.imageView2.setImageResource(dR2)

        val massage = if (sides == sides2) {
            "Selamat, anda mendapatkan dadu double"
        } else {
            "Anda belum beruntung"
        }
        Snackbar.make(button, massage, Snackbar.LENGTH_LONG)
            .show()
    }
}
class Dice(private val numSides : Int)
{
    fun diceRoll() : Int
    {
        return (1..numSides).random()
    }
}