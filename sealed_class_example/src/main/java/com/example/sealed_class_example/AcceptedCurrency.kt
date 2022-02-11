package com.example.sealed_class_example

sealed class AcceptedCurrency {

    abstract val valueInRubels: Double
    var amount: Double = 0.0

    val name: String
        get() = when (this) {
            is Rubel -> "Рубль"
            is Dollar -> "Доллар"
            is Euro -> "Евро"
            is Tugrik -> "Tugrik"
        }

    fun totalValueInRubles(): Double {
        return amount * valueInRubels
    }

}

class Rubel : AcceptedCurrency() {
    override val valueInRubels = 1.0
}

class Dollar : AcceptedCurrency() {
    override val valueInRubels = 75.0
}

class Euro : AcceptedCurrency() {
    override val valueInRubels = 90.0
}