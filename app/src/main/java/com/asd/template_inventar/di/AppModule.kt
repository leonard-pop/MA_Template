package com.asd.template_inventar.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.asd.template_inventar.MainActivity
import com.asd.template_inventar.model.repo.BaseProductRepository
import com.asd.template_inventar.model.repo.ProductRepository
import com.asd.template_inventar.model.repo.localrepo.EntityDao
import com.asd.template_inventar.model.repo.localrepo.LocalDatabase
import com.asd.template_inventar.model.service.ProductService
import com.asd.template_inventar.model.usecase.*

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MainActivity.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesService(retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }

    @Provides
    @Singleton
    fun providesDAO(): EntityDao {
        return LocalDatabase.getDatabase(MainActivity.bcontext).entityDao()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface AppModuleInt {
        @Binds
        @Singleton
        fun provideRepository(repo: BaseProductRepository): ProductRepository

        @Binds
        @Singleton
        fun provideGetAllUseCase(uc: BaseGetProductsUseCase): GetProductsUseCase

        @Binds
        @Singleton
        fun provideAddUseCase(uc: BaseAddProductsUseCase): AddProductsUseCase

        @Binds
        @Singleton
        fun provideSyncUseCase(uc: BaseSyncUseCase): SyncUseCase
    }

}