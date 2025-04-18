package me.androidbox.scribbledash

import me.androidbox.scribbledash.draw.presentation.utils.ParseXmlDrawable
import org.koin.dsl.module

val iosSpecificModule = module {

    factory<ParseXmlDrawable> {
        ParseXmlDrawableImp()
    }
}
