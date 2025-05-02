package me.androidbox.scribbledash.di

import me.androidbox.scribbledash.gamemode.presentation.DrawingViewModel
import me.androidbox.scribbledash.gamemode.presentation.FeedbackViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val scribbleDashModule = module {

    viewModelOf(::DrawingViewModel)
    viewModelOf(::FeedbackViewModel)
}