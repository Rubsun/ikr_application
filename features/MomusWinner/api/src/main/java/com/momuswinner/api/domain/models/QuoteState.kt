package com.momuswinner.api.domain.models

sealed class QuoteState {
    object Idle : QuoteState()
    object Loading : QuoteState()
    data class Success(val quote: Quote) : QuoteState()
    data class Error(val message: String) : QuoteState()
}