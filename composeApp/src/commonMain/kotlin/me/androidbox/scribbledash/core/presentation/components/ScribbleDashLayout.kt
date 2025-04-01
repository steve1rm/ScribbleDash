@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.scribbledash.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ScribbleDashLayout(
    modifier: Modifier = Modifier,
    toolBarTitle: String,
    onNavigationClicked: (() -> Unit)? = null,
    content: @Composable (paddingValue: PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier.background(color = MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = toolBarTitle,
                        fontWeight = FontWeight.W400,
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 26.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onNavigationClicked?.invoke()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            content(paddingValues)
        }
    )
}