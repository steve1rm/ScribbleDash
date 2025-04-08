package me.androidbox.scribbledash

import me.androidbox.scribbledash.di.scribbleDashModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initializeKoin(config: KoinAppDeclaration? = null, vararg platformSpecificModules: Module = emptyArray()) {
    startKoin {
        config?.invoke(this)
        modules(
            scribbleDashModule,
            *platformSpecificModules
        )
    }
}