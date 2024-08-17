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
import com.example.pschologyfactsmvvmkotlin.adapters.FavoriteAdapter
import com.example.pschologyfactsmvvmkotlin.databinding.ActivityFavoriteFactsBinding
import com.example.pschologyfactsmvvmkotlin.viewModel.FavoriteViewModel

class FavoriteFactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteFactsBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoriteFactsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        changeStatusBarColor(R.color.medium)
        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Handle back button press
        binding.materialToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        favoriteAdapter = FavoriteAdapter(emptyList()) { favorite ->
            val intent = Intent(this, FactDetailActivity::class.java).apply {
                putExtra("key", "favFacts")
                putExtra("factText", favorite.fact)
                putExtra("factId", favorite.id)
                putParcelableArrayListExtra("favFactsList", ArrayList(favoriteAdapter.getCurrentList()))
            }
            startActivity(intent)
        }


        binding.favFactsRecycler.layoutManager = LinearLayoutManager(this)
        binding.favFactsRecycler.adapter = favoriteAdapter

        favoriteViewModel.allFavorites.observe(this, { favorites ->
            favoriteAdapter.updateData(favorites)
        })
    }

    private fun changeStatusBarColor(colorResId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, colorResId)
        }
    }
}
