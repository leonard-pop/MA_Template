package com.asd.template_inventar.model.usecase

import javax.inject.Inject
import com.asd.template_inventar.model.domain.Product
import com.asd.template_inventar.model.repo.ProductRepository

interface GetProductsUseCase {
    suspend operator fun invoke(): List<Product>
}

class BaseGetProductsUseCase @Inject constructor(
    private val repo: ProductRepository
) : GetProductsUseCase {
    override suspend fun invoke(): List<Product> {
        return repo.getAll()
    }
}