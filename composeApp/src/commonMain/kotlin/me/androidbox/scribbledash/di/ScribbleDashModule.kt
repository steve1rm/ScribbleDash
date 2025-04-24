package me.androidbox.scribbledash.di

import me.androidbox.scribbledash.draw.presentation.DrawingViewModel
import me.androidbox.scribbledash.draw.presentation.FeedbackViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val scribbleDashModule = module {

    viewModelOf(::DrawingViewModel)
    viewModelOf(::FeedbackViewModel)
}