package com.example.retrofitcouroutinelivedatanewformat.model

data class RespositoryResponse(
    val incomplete_results: Boolean,
    val total_count: Int,
    val items: List<Repository>
)