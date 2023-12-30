package com.dwinovirhmwt.mobiledeveloperinternsuitmedia.firstscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dwinovirhmwt.mobiledeveloperinternsuitmedia.databinding.ActivityFirstScreenBinding
import com.dwinovirhmwt.mobiledeveloperinternsuitmedia.secondscreen.SecondScreenActivity

class FirstScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCheck.setOnClickListener {
            checkPalindrome()
        }

        binding.buttonNext.setOnClickListener {
            val name = binding.edName.text.toString()
            val intent = Intent(this, SecondScreenActivity::class.java)
            intent.putExtra("name", name)
            startActivity(intent)
        }
    }

    private fun checkPalindrome() {
        val inputText = binding.edPalindrome.text.toString().replace("\\s".toRegex(), "")

        val isPalindrome = inputText.equals(inputText.reversed(), ignoreCase = true)

        val resultMessage = if (isPalindrome) {
            "isPalindrome"
        } else {
            "not palindrome"
        }

        displayResultDialog(resultMessage)
    }

    private fun displayResultDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setPositiveButton("OK") { _, _ -> }
            .create()
            .show()
    }
}
