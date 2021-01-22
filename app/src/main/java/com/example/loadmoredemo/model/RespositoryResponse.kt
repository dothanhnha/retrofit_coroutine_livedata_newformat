package com.example.loadmoredemo.model

data class RespositoryResponse(
    val incomplete_results: Boolean,
    val total_count: Int,
    val items: List<Repository>
)