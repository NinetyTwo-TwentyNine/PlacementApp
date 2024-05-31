package com.example.placementapp.UI

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.placementapp.data.Constants.APP_PREFERENCES_PUSHES
import com.example.placementapp.data.Constants.APP_PREFERENCES_STAY
import com.example.placementapp.databinding.ActivityMainBinding
import com.example.placementapp.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#05080D")))

        viewModel.editPreferences(APP_PREFERENCES_STAY, viewModel.getPreference(APP_PREFERENCES_STAY, true))
        viewModel.editPreferences(APP_PREFERENCES_PUSHES, viewModel.getPreference(APP_PREFERENCES_PUSHES, true))
    }
}