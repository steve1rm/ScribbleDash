package me.androidbox.scribbledash

import me.androidbox.scribbledash.gamemode.data.SaveBitmapDrawing
import me.androidbox.scribbledash.gamemode.presentation.utils.ParseXmlDrawable
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidSpecificModule = module {

    factory<ParseXmlDrawable> {
        ParseXmlDrawableImp(androidContext())
    }

    factory<SaveBitmapDrawing> {
        SaveBitmapDrawingImp(context = androidContext())
    }
}
