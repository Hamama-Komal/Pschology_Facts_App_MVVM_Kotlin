package com.example.psychologyfactsmvvmkotlin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pschologyfactsmvvmkotlin.model.CategoryWithFacts
import com.example.pschologyfactsmvvmkotlin.model.ImageFact
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _categoriesWithFacts = MutableLiveData<List<CategoryWithFacts>>()
    val categoriesWithFacts: LiveData<List<CategoryWithFacts>> get() = _categoriesWithFacts

    private val _imagesFacts = MutableLiveData<List<ImageFact>>()
    val imagesFacts: LiveData<List<ImageFact>> get() = _imagesFacts

    init {
        fetchCategoriesWithFacts()
        fetchImageFacts()
    }

    private fun fetchCategoriesWithFacts() {
        val json = getApplication<Application>().assets.open("facts_category.json").bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<CategoryWithFacts>>() {}.type
        val categoriesWithFactsList: List<CategoryWithFacts> = Gson().fromJson(json, type)
        _categoriesWithFacts.value = categoriesWithFactsList
    }

    private fun fetchImageFacts() {
        val json = getApplication<Application>().assets.open("image_facts.json").bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<ImageFact>>() {}.type
        val imageList: List<ImageFact> = Gson().fromJson(json, type)
        _imagesFacts.value = imageList
    }
}
