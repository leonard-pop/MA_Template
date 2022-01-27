package com.asd.template_inventar.model.service

import retrofit2.http.*
import com.asd.template_inventar.model.domain.Product

interface ProductService {
    @GET("/products")
    suspend fun getAll(): List<Product>

    @POST("/product")
    suspend fun add(@Body entity: Product): Product
}