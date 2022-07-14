package com.ayizor.afeme.api.main

import com.ayizor.afeme.model.User
import com.ayizor.afeme.model.post.Post
import com.ayizor.afeme.model.response.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    //Post Interface
    @GET("post/{id}")
    fun getSinglePost(@Path("id") id: Int): Call<PostResponse>

    @POST("filter")
    fun getPostsByCategory(@Query("htype") id: Int): Call<GetPostResponse>

    @POST("filter")
    fun searchPosts(
        @Query("htype") building_type: Int?,
        @Query("sale_id") post_type: Int?,
        @Query("room") room: Int?,
        @Query("keyword") keyword: String?,
        @Query("perpage") perpage: Int?,
        @Query("region_id") region_id: Int?,
        @Query("city_id") city_id: Int?,
        @Query("from") from_price: Int?,
        @Query("to") to_rice: Int?,
        @Query("price_type") currency: String?
    ): Call<GetPostResponse>

    @GET("post")
    fun getAllPosts(): Call<GetPostResponse>


    @GET("popular/{id}")
    fun getPopularPosts(@Path("id") page: Int): Call<GetPostResponse>


    @POST("post")
    fun createPost(@Body post: Post): Call<MainResponse>


    @Multipart
    @POST("service")
    fun uploadFile(
        @Part file: MultipartBody.Part,
        @Part("sub_id") subId: String?,
        @Part("key") key: String?
    ): Call<MainResponse>

    @PUT("post/{id}")
    fun updatePost(@Path("id") id: String): Call<List<Post>>

    @DELETE("post/{id}")
    fun deletePost(@Path("id") id: String): Call<List<Post>>

    //
    //auth
    @POST("register")
    fun register(@Body user: User): Call<MainResponse>

    @POST("login")
    fun login(): Call<User>

    @POST("logout")
    fun logout(): Call<User>


    @POST("sms")
    fun sendPhoneNumber(@Query("phone") phone: String): Call<MainResponse>

    //
    //user
    @GET("user")
    fun getAllUsers(): Call<UserResponse>

    @GET("user/{id}")
    fun getSingleUser(@Path("id") id: Int): Call<UserResponse>


    @GET("getuser")
    fun getCurrentUser(): Call<UserResponse>

    @PUT("user/{id}")
    fun updateSingleUser(@Path("id") id: String, @Body user: User): Call<MainResponse>

    @DELETE("user/{id}")
    fun deleteSingleUser(@Path("id") id: String): Call<UserResponse>

    //
    //categores
    @GET("htype")
    fun getAllCategory(): Call<CategoryResponse>

    //selltypes
    @GET("sales")
    fun getAllSellTypes(): Call<SellResponse>

    //building materials
    @GET("materials")
    fun getAllBuildingMaterials(): Call<BuildingMaterialResponse>

    //regions
    @GET("regions")
    fun getRegions(): Call<RegionsResponse>


//filter

}