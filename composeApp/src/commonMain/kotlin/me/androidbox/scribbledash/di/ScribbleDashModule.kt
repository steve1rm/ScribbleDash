package me.androidbox.scribbledash.di

import me.androidbox.scribbledash.gamemode.presentation.DrawingViewModel
import me.androidbox.scribbledash.gamemode.presentation.EndlessModeViewModel
import me.androidbox.scribbledash.gamemode.presentation.FeedbackViewModel
import me.androidbox.scribbledash.gamemode.presentation.SpeedDrawViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val scribbleDashModule = module {

    viewModelOf(::DrawingViewModel)
    viewModelOf(::FeedbackViewModel)
    viewModelOf(::SpeedDrawViewModel)
    viewModelOf(::EndlessModeViewModel)
}