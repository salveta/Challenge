package com.salvaperez.challenge.data.di

import android.app.Application
import com.salvaperez.challenge.BuildConfig
import com.salvaperez.challenge.data.api.ChallengeApi
import com.salvaperez.challenge.data.database.ProductDatabase
import com.salvaperez.challenge.data.database.RoomProductsDataSource
import com.salvaperez.challenge.data.datasource.LocalShoppingDataSource
import com.salvaperez.challenge.data.datasource.RemoteProductsDataSource
import com.salvaperez.challenge.data.manager.CalculateFinalPriceManager
import com.salvaperez.challenge.data.repository.ProductDataRepository
import com.salvaperez.challenge.data.repository.ShoppingCartDataRepository
import com.salvaperez.challenge.domain.repository.ProductRepository
import com.salvaperez.challenge.domain.repository.ShoppingCartRepository
import com.salvaperez.challenge.domain.usecase.AddProductInShoppingCart
import com.salvaperez.challenge.domain.usecase.ClearShoppingCart
import com.salvaperez.challenge.domain.usecase.GetProducts
import com.salvaperez.challenge.domain.usecase.GetShoppingCart
import com.salvaperez.challenge.presentation.payment.PaymentActivity
import com.salvaperez.challenge.presentation.payment.PaymentViewModel
import com.salvaperez.challenge.presentation.products.ProductsActivity
import com.salvaperez.challenge.presentation.products.ProductsViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit


fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(
            listOf(
                dataModule,
                products,
                payments
            )
        )
    }
}

val products = module(override = true) {

    scope(named<ProductsActivity>()) {
        viewModel {
            ProductsViewModel(
                getProducts = get(),
                addProductInShoppingCart = get(),
                getShoppingCart = get()
            )
        }
        scoped { GetProducts(get()) }
        scoped { AddProductInShoppingCart(get()) }
        scoped { ClearShoppingCart(get()) }
        scoped { GetShoppingCart(get()) }
    }

    single { RoomProductsDataSource(get()) }
    single { RemoteProductsDataSource(get()) }
    factory<ProductRepository> {
        ProductDataRepository(
            remoteProductsDataSource = get(),
            roomProductsDataSource = get(),
            context = get()
        )
    }
    single<ShoppingCartRepository> {
        ShoppingCartDataRepository(get())
    }
}

val payments = module(override = true) {
    scope(named<PaymentActivity>()) {
        viewModel {
            PaymentViewModel(
                getShoppingCart = get(),
                clearShoppingCart = get()
            )
        }
        scoped { GetShoppingCart(get()) }
        scoped { ClearShoppingCart(get()) }
    }

    single { LocalShoppingDataSource(get()) }
    single { CalculateFinalPriceManager() }
}

val dataModule = module(override = true) {
    single { ProductDatabase.build(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
    single<ChallengeApi> { get<Retrofit>().create() }

    val apiTimeOutInSeconds = 60L

    single {
        Retrofit.Builder()
            .apply {
                addConverterFactory(GsonConverterFactory.create())
                baseUrl(BuildConfig.API_URL)
                client(get())
            }
            .build()
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(apiTimeOutInSeconds, TimeUnit.SECONDS)
            .readTimeout(apiTimeOutInSeconds, TimeUnit.SECONDS)
            .writeTimeout(apiTimeOutInSeconds, TimeUnit.SECONDS)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    factory {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }
        return@factory interceptor
    }
}
