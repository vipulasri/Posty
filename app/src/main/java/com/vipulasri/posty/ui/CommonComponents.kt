package com.vipulasri.posty.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vipulasri.posty.R

/**
 * Created by Vipul Asri on 09/12/21.
 */

val DEFAULT_PADDING = 10.dp

@Composable
fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun Error(message: String?, onRetry: (() -> Unit)? = null) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = message ?: stringResource(R.string.error_unknown))
        Spacer(modifier = Modifier.height(DEFAULT_PADDING))
        Button(onClick = { onRetry?.invoke() }) {
            Text(text = stringResource(R.string.retry))
        }
    }
}