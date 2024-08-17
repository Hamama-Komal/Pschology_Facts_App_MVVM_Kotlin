package com.example.pschologyfactsmvvmkotlin.model

data class CategoryWithFacts(
    val category: Category,
    val facts: List<Fact>
)