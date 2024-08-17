package com.example.pschologyfactsmvvmkotlin

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.pschologyfactsmvvmkotlin.databinding.ActivityFactDetailBinding
import com.example.pschologyfactsmvvmkotlin.model.Fact
import com.example.pschologyfactsmvvmkotlin.room.Favorite
import com.example.pschologyfactsmvvmkotlin.viewModel.FavoriteViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random

class FactDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFactDetailBinding
    private var factsList: List<Fact> = emptyList()
    private var favFactsList: List<Favorite> = emptyList()
    private lateinit var favoriteViewModel: FavoriteViewModel
    private var isFavorite = false
    private lateinit var key: String
    private lateinit var factText: String
    private var factId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        key = intent.getStringExtra("key").orEmpty()
        factText = intent.getStringExtra("factText").orEmpty()
        factId = intent.getIntExtra("factId", 0)

        changeStatusBarColor(R.color.medium)
        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Handle back button press
        binding.materialToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        if (factText.isEmpty()) {
            Log.e("FactDetailActivity", "factText is null or empty")
        }

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        if (key == "favFacts") {
            favFactsList = intent.getParcelableArrayListExtra<Favorite>("favFactsList") ?: emptyList()
        } else {
            factsList = intent.getParcelableArrayListExtra<Fact>("factsList") ?: emptyList()
        }

        binding.textViewFact.text = factText
        binding.texFactId.text = factId.toString()

        lifecycleScope.launch {
            isFavorite = favoriteViewModel.isFavorite(factText)
            updateFavoriteIcon()
        }

        setFavIcon()

        // Handle click events
        binding.imageCopy.setOnClickListener {
            copyTextToClipboard(factText)
        }

        binding.imageShuffle.setOnClickListener {
            shuffleFact()
        }

        binding.imageShare.setOnClickListener {
            shareText(factText)
        }
    }

    private fun setFavIcon() {
        binding.imageIcon.setOnClickListener {
            if (isFavorite) {
                favoriteViewModel.delete(Favorite(factId, factText))
            } else {
                favoriteViewModel.insert(Favorite(0, factText))
            }
            isFavorite = !isFavorite
            updateFavoriteIcon()
        }
    }

    private fun updateFavoriteIcon() {
        if (isFavorite) {
            binding.imageIcon.setImageResource(R.drawable.ic_like) // Change to like icon
        } else {
            binding.imageIcon.setImageResource(R.drawable.ic_unlike) // Change to unlike icon
        }
    }

    private fun copyTextToClipboard(text: String?) {
        if (text != null) {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Fact", text)
            clipboard.setPrimaryClip(clip)
            showToast("Text copied to clipboard")
        }
    }

    private fun shuffleFact() {
        if (key == "facts" && factsList.isNotEmpty()) {
            val randomFact = factsList[Random.nextInt(factsList.size)]
            factText = randomFact.text
            factId = randomFact.id
        } else if (key == "favFacts" && favFactsList.isNotEmpty()) {
            val randomFact = favFactsList[Random.nextInt(favFactsList.size)]
            factText = randomFact.fact
            factId = randomFact.id
        }
        binding.textViewFact.text = factText
        binding.texFactId.text = factId.toString()

        lifecycleScope.launch {
            isFavorite = favoriteViewModel.isFavorite(factText)
            updateFavoriteIcon()
        }
        setFavIcon()
    }

    private fun shareText(text: String?) {
        text?.let {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, it)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun changeStatusBarColor(colorResId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, colorResId)
        }
    }
}
