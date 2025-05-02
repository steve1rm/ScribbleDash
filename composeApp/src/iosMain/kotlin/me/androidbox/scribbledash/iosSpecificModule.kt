package me.androidbox.scribbledash

import me.androidbox.scribbledash.gamemode.presentation.utils.ParseXmlDrawable
import org.koin.dsl.module

val iosSpecificModule = module {

    factory<ParseXmlDrawable> {
        ParseXmlDrawableImp()
    }
}
