package com.complexparking.ui.wizard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.complexparking.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseWizardViewModel : ViewModel() {
    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled get() = _isButtonEnabled.asStateFlow()

    private val _currentIndex = mutableStateOf(0)
    val currentIndex get() = _currentIndex

    private val _buttonText = mutableStateOf(0)
    val buttonText get() = _buttonText

    private val _isButtonPreviousVisible = mutableStateOf(false)
    val isButtonPreviousVisible get() = _isButtonPreviousVisible

    private lateinit var _onIndexChangeAction: () -> Unit

    fun setIndexChangeAction(action: () -> Unit) {
        _onIndexChangeAction = action
    }

    init {
        _buttonText.value = R.string.wizard_complex_configuration_next_button
    }

    fun setButtonEnabled(enabled: Boolean) {
        _isButtonEnabled.value = enabled
    }

    fun onNextIndex(): Int {
        if (_currentIndex.value < 2) {
            _currentIndex.value = _currentIndex.value + 1
            _isButtonPreviousVisible.value = true
            if (_currentIndex.value == 2) {
                _buttonText.value = R.string.wizard_complex_configuration_finish_button
            }
        }
        _onIndexChangeAction.invoke()
        return _currentIndex.value
    }

    fun onPreviousIndex(): Int {
        if (_currentIndex.value > 0) {
            _currentIndex.value = _currentIndex.value - 1
            if (_currentIndex.value == 0) {
                _isButtonPreviousVisible.value = false
            } else {
                _isButtonPreviousVisible.value = true
            }

            _buttonText.value = R.string.wizard_complex_configuration_next_button
        }
        _onIndexChangeAction.invoke()
        return _currentIndex.value
    }
}