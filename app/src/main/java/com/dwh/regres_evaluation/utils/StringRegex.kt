package com.dwh.regres_evaluation.utils

import java.util.regex.Pattern

object StringRegex {
    fun String.isNumber():Boolean {
        return this.matches(Regex("[0-9]{1,64}"))
    }
    fun String.isSpecialCharacters():Boolean {
        return  this.matches(Regex("^[<>{}@#&*-+=()%_:;/,.£€¥¢©®™¿¡?! ¦¬×§¶°~|]+\$"))
    }
    fun String.isLetter():Boolean{
        return this.matches(Regex("^[ÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÑÕÄËÏÖÜŸA-Z][a-záéíóúàèìòùâêîôûãñõäëïöüÿ]+(\\s?)+(\\s+[ÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÑÕÄËÏÖÜŸA-Z]?[a-záéíóúàèìòùâêîôûãñõäëïöüÿ]+(\\s?)+)*\$"))
    }
    fun String.isEmail():Boolean{
        return this.matches(Regex("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}+(\\s?)"))
    }
    fun String.containsEmoji(): Boolean {
        return Pattern
            .compile("\\p{So}+", Pattern.CASE_INSENSITIVE)
            .matcher(this).find()
    }
}