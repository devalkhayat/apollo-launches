package com.example.apollo_launches.ui.theme

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThemeViewModel : ViewModel(){

    private val _theme = MutableStateFlow(AppTheme.LIGHT)
    val theme: StateFlow<AppTheme> = _theme

    fun toggleTheme() {
        _theme.value = if (_theme.value == AppTheme.LIGHT) AppTheme.DARK else AppTheme.LIGHT
    }

    fun setTheme(appTheme: AppTheme) {
        _theme.value = appTheme
    }
}