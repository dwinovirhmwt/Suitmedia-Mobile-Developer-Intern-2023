package com.dwinovirhmwt.mobiledeveloperinternsuitmedia.secondscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.dwinovirhmwt.mobiledeveloperinternsuitmedia.R
import com.dwinovirhmwt.mobiledeveloperinternsuitmedia.databinding.ActivityFirstScreenBinding
import com.dwinovirhmwt.mobiledeveloperinternsuitmedia.databinding.ActivitySecondScreenBinding
import com.dwinovirhmwt.mobiledeveloperinternsuitmedia.network.User
import com.dwinovirhmwt.mobiledeveloperinternsuitmedia.thirdscreen.ThirdScreenActivity

class SecondScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        binding.name.text = getString(R.string.name_template, name)

        binding.buttonChooseUser.setOnClickListener {
            @Suppress("DEPRECATION")
            startActivityForResult(Intent(this, ThirdScreenActivity::class.java), REQUEST_SELECT_USER)
        }

        setupToolbar()
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SELECT_USER && resultCode == RESULT_OK) {
            val selectedUser = data?.getSerializableExtra("selectedUser") as? User
            selectedUser?.let {
                // Update the UI or perform any action with the selected user data
                binding.selectedUserName.text = getString(R.string.name_template, "${it.firstName} ${it.lastName}")
            }
        }
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    @Suppress("DEPRECATION")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Handle the back button click
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val REQUEST_SELECT_USER = 123
    }
}