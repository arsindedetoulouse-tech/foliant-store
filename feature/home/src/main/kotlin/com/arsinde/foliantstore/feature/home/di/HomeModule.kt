package com.arsinde.foliantstore.feature.home.di

import com.arsinde.foliantstore.data.home.HomeDataSource
import com.arsinde.foliantstore.data.home.HomeDataSourceImpl
import com.arsinde.foliantstore.data.home.HomeRepositoryImpl
import com.arsinde.foliantstore.domain.home.HomeRepository
import com.arsinde.foliantstore.domain.home.HomeUseCase
import com.arsinde.foliantstore.feature.home.presentation.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * DI registrations of the whole home chain: data source, repository, use case and
 * ViewModel. Registering the use case rather than the repository in the ViewModel keeps the layer
 * boundary enforced by the constructor.
 *
 * The implementations live in the shared domain and data modules; this module binds them for this
 * feature, because only the feature knows which types belong to it.
 */
val homeModule = module {
    single<HomeDataSource> { HomeDataSourceImpl(get(), androidContext()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
    single { HomeUseCase(get()) }
    viewModel { HomeViewModel(get()) }
}