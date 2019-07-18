package com.example.coroutineskotlin.network

import com.example.coroutineskotlin.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("/posts")
    suspend fun getPosts(): Response<List<Post>>
}