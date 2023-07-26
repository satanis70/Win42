package com.example.win42.services

import com.example.win42.model.BodyModel
import com.example.win42.model.PostModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiCs {
    @POST("splash.php")
    suspend fun postQuery(@Body postModel: PostModel): Response<BodyModel>
}