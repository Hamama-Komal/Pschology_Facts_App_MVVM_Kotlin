package com.example.pschologyfactsmvvmkotlin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pschologyfactsmvvmkotlin.model.Fact
import org.json.JSONArray
import java.io.IOException

class FactViewModel (application: Application) : AndroidViewModel(application) {

    private val _facts = MutableLiveData<List<Fact>>()
    val facts: LiveData<List<Fact>> get() = _facts

    fun loadFactsFromJson(categoryName: String) {
        val factsList = mutableListOf<Fact>()
        try {
            val inputStream = getApplication<Application>().assets.open("facts_category.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer, Charsets.UTF_8)
            val jsonArray = JSONArray(json)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val category = jsonObject.getJSONObject("category").getString("name")
                if (category == categoryName) {
                    val facts = jsonObject.getJSONArray("facts")
                    for (j in 0 until facts.length()) {
                        val factObject = facts.getJSONObject(j)
                        val fact = Fact(
                            id = factObject.getInt("id"),
                            category = category,
                            text = factObject.getString("text")
                        )
                        factsList.add(fact)
                    }
                    break
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        _facts.value = factsList
    }

}