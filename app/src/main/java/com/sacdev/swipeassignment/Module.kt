package com.sacdev.swipeassignment

import com.sacdev.swipeassignment.network.ApiClient
import com.sacdev.swipeassignment.repository.AddProductRepository
import com.sacdev.swipeassignment.repository.ProductRepository
import com.sacdev.swipeassignment.viewmodel.AddProductViewModel
import com.sacdev.swipeassignment.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Module = module {
    single { ApiClient.apiService }

    viewModel { ProductViewModel(get()) }
    viewModel{  AddProductViewModel(get()) }
    single { ProductRepository(get()) }
    single { AddProductRepository(get()) }
}