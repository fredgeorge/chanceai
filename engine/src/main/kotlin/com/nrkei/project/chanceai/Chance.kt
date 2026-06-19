package com.nrkei.project.chanceai

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.round

data class Chance(val probability: Double) {

    init {
        require(probability in 0.0..1.0) { "Probability must be between 0.0 and 1.0, inclusive" }
    }

    operator fun not(): Chance = Chance(1.0 - probability)

    infix fun and(other: Chance): Chance = Chance(this.probability * other.probability)

    infix fun or(other: Chance): Chance = !(this.not() and other.not())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Chance) return false
        return abs(probability - other.probability) < EPSILON
    }

    override fun hashCode(): Int {
        val adjusted = roundToPrecision(PRECISION)
        return adjusted.hashCode()
    }

    companion object {
        val IMPOSSIBLE = Chance(0.0)
        val CERTAIN = Chance(1.0)

        private const val EPSILON = 1e-9
        private const val PRECISION = 9
    }

    private fun roundToPrecision(precision: Int): Double {
        return round(this.probability * kpow(10, precision)) / kpow(10, precision)
    }

    private fun kpow(base: Int, exp: Int): Double {
        return base.toDouble().pow(exp.toDouble())
    }
}
