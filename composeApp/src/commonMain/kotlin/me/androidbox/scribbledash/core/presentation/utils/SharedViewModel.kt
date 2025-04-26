package me.androidbox.scribbledash.core.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel

@Composable
inline fun <reified VM: ViewModel> NavBackStackEntry.getSharedViewModel(navController: NavController): VM {
    val navGraphRoute = this.destination.parent?.route

    val viewModel = if(navGraphRoute == null) {
        koinViewModel<VM>()
    }
    else {
        val parentEntry = remember(this) {
            navController.getBackStackEntry(navGraphRoute)
        }

        koinViewModel(viewModelStoreOwner = parentEntry)
    }

    return viewModel
}