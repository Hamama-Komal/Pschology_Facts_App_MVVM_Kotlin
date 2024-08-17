package com.example.pschologyfactsmvvmkotlin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pschologyfactsmvvmkotlin.adapters.ImageFactAdapter
import com.example.pschologyfactsmvvmkotlin.databinding.ActivityMainBinding
import com.example.psychologyfactsmvvmkotlin.adapters.CategoryAdapter
import com.example.psychologyfactsmvvmkotlin.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var imageFactAdapter: ImageFactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        changeStatusBarColor(R.color.medium)
        setSupportActionBar(binding.materialToolbar)

        binding.categoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }

        binding.imageRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        categoryAdapter = CategoryAdapter { categoryWithFacts ->
            val intent = Intent(this, FactsActvity::class.java).apply {
                putExtra("categoryName", categoryWithFacts.category.name)
            }
            startActivity(intent)
        }

        imageFactAdapter = ImageFactAdapter { imageFact, position ->
            val intent = Intent(this, RandomFactsActivity::class.java).apply {
                putParcelableArrayListExtra("imageFactsList", ArrayList(imageFactAdapter.currentList))
                putExtra("position", position)
            }
            startActivity(intent)
        }

        binding.categoryRecyclerView.adapter = categoryAdapter
        binding.imageRecyclerView.adapter = imageFactAdapter

        mainViewModel.categoriesWithFacts.observe(this, Observer { categories ->
            categoryAdapter.submitList(categories)
        })

        mainViewModel.imagesFacts.observe(this, Observer { images ->
            imageFactAdapter.submitList(images)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_fav -> {
                startActivity(Intent(this@MainActivity, FavoriteFactsActivity::class.java))
                true
            }
            R.id.menu_rate -> {
                Toast.makeText(this, "Rate Us 😊", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun changeStatusBarColor(colorResId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, colorResId)
        }
    }
}
