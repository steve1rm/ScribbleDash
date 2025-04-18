package me.androidbox.scribbledash

import me.androidbox.scribbledash.draw.presentation.utils.ParseXmlDrawable
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val androidSpecificModule = module {

    factory<ParseXmlDrawable> {
        ParseXmlDrawableImp(androidApplication())
    }

}
