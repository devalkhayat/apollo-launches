package com.example.apollo_launches.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.apollo_launches.R
import com.example.apollo_launches.ui.theme.ThemeViewModel

@Composable
fun ThemeToggleButton(viewModel: ThemeViewModel) {

    Button(onClick = { viewModel.toggleTheme() }) {
        Text(text = stringResource(R.string.switch_theme))
    }
}
