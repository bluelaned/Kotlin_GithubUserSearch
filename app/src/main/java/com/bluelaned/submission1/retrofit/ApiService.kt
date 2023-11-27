package com.bluelaned.submission1.retrofit

import com.bluelaned.submission1.response.DetailsResponse
import com.bluelaned.submission1.response.GithubResponse
import com.bluelaned.submission1.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getItemsItem(
        @Query("q") id: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(@Path("username") username: String): Call<DetailsResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}