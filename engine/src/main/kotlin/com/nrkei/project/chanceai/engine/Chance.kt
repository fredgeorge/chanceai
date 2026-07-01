package com.nrkei.project.chanceai.engine

import kotlin.math.absoluteValue
import kotlin.math.roundToLong

class Chance(private val fraction: Double) {

    companion object {
        val IMPOSSIBLE = Chance(0.0)
        val CERTAIN = Chance(1.0)
        private const val EPSILON = 1e-9
    }

    init {
        require(fraction in 0.0..1.0) { "Fraction must be between 0.0 and 1.0, inclusive" }
    }

    override fun equals(other: Any?) =
        this === other || other is Chance && this.equals(other)

    private fun equals(other: Chance) =
        (this.fraction - other.fraction).absoluteValue < EPSILON

    override fun hashCode() = (fraction / EPSILON).roundToLong().hashCode()

    infix fun and(other: Chance) = Chance(this.fraction * other.fraction)

    operator fun not() = Chance(1 - fraction)

    infix fun or(other: Chance) = !(this.not() and other.not())
}
