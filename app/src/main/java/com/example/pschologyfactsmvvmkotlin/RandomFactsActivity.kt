package com.example.pschologyfactsmvvmkotlin

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.pschologyfactsmvvmkotlin.adapters.ImageFactPagerAdapter
import com.example.pschologyfactsmvvmkotlin.databinding.ActivityRandomFactsBinding
import com.example.pschologyfactsmvvmkotlin.model.ImageFact
import com.example.psychologyfactsmvvmkotlin.viewModel.MainViewModel
import java.io.File
import java.io.FileOutputStream

class RandomFactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRandomFactsBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var imageFacts: List<ImageFact>
    private var startPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRandomFactsBinding.inflate(layoutInflater)
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

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        imageFacts = intent.getParcelableArrayListExtra<ImageFact>("imageFactsList") ?: emptyList()
        startPosition = intent.getIntExtra("position", 0)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val adapter = ImageFactPagerAdapter(imageFacts)
        viewPager.adapter = adapter

        mainViewModel.imagesFacts.observe(this, Observer { images ->
            val imageFactAdapter = ImageFactPagerAdapter(images)
            binding.viewPager.adapter = imageFactAdapter
            binding.viewPager.setCurrentItem(startPosition, false)
        })

        binding.viewPager.setCurrentItem(startPosition, false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.share_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_share -> {
                shareCurrentImage()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shareCurrentImage() {
        val currentItem = binding.viewPager.currentItem
        val currentImageFact = imageFacts[currentItem]
        downloadAndShareImage(currentImageFact.imageUrl)
    }

    private fun downloadAndShareImage(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    shareImage(resource)
                }
            })
    }

    private fun shareImage(bitmap: Bitmap) {
        try {
            val file = File(externalCacheDir, "shared_image.jpg")
            val fOut = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
            fOut.flush()
            fOut.close()
            file.setReadable(true, false)

            val uri = FileProvider.getUriForFile(this, "$packageName.fileprovider", file)
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/jpeg"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun changeStatusBarColor(colorResId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, colorResId)
        }
    }
}
