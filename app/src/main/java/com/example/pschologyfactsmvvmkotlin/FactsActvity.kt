package com.example.pschologyfactsmvvmkotlin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pschologyfactsmvvmkotlin.adapters.FactAdapter
import com.example.pschologyfactsmvvmkotlin.databinding.ActivityFactsActvityBinding
import com.example.pschologyfactsmvvmkotlin.viewModel.FactViewModel


class FactsActvity : AppCompatActivity() {

    private lateinit var binding: ActivityFactsActvityBinding
    private lateinit var factsViewModel: FactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFactsActvityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val categoryName = intent.getStringExtra("categoryName")

        changeStatusBarColor(R.color.medium)
        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Handle back button press
        binding.materialToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.materialToolbar.title = categoryName

        factsViewModel = ViewModelProvider(this).get(FactViewModel::class.java)
        factsViewModel.loadFactsFromJson(categoryName.toString())

        val factAdapter = FactAdapter { fact ->
            val intent = Intent(this, FactDetailActivity::class.java)
            intent.putExtra("key", "facts")
            intent.putExtra("factText", fact.text)
            intent.putExtra("factId", fact.id)
           intent.putExtra("factsList", ArrayList(factsViewModel.facts.value)) // Pass entire list
            startActivity(intent)
        }
        binding.factsRecycler.layoutManager = LinearLayoutManager(this)
        binding.factsRecycler.adapter = factAdapter

        factsViewModel.facts.observe(this) { factList ->
            factAdapter.submitList(factList)
        }



    }

    private fun changeStatusBarColor(colorResId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, colorResId)
        }
    }

}