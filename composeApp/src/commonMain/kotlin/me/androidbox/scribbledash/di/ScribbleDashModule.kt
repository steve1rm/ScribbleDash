package me.androidbox.scribbledash.di

import me.androidbox.scribbledash.draw.presentation.DrawingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val scribbleDashModule = module {

    viewModelOf(::DrawingViewModel)
}