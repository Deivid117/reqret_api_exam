package com.dwh.regres_evaluation.domain.model

data class ErrorMessage(
    val emailMsg: Int = 0,
    val passwordMsg: Int = 0,
    val passwordConfirmationMsg: Int = 0,
    val nameMsg: Int = 0,
    val jobMsg: Int = 0
)
