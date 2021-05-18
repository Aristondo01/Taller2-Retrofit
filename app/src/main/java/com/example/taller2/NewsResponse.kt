package com.example.taller2

import com.google.gson.annotations.SerializedName

data class NewsResponse (
    @SerializedName("status") val status:String,
    @SerializedName("totalResult") val totalResult:String,
    @SerializedName("articles") val articles:List<Articles>,

        )

