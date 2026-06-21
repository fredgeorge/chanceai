package com.nrkei.engine

import kotlin.math.abs
import kotlin.math.roundToLong

class Chance(fraction: Number) {
    companion object {
        private const val EPSILON = 1e-9
        val IMPOSSIBLE = Chance(0.0)
        val CERTAIN = Chance(1.0)
    }

    private val fraction: Double

    init {
        fraction = fraction.toDouble()
        require(fraction in 0.0..1.0) { "Probability must be between 0.0 and 1.0" }
    }

    override fun equals(other: Any?) =
        this === other || other is Chance && this.equals(other)

    private fun equals(other: Chance) = abs(this.fraction - other.fraction) < EPSILON

    override fun hashCode() = (fraction / EPSILON).roundToLong().hashCode()

    infix fun and(other: Chance) = Chance(this.fraction * other.fraction)

    operator fun not() = Chance(1 - fraction)

    infix fun or(other: Chance) = !(!this and !other)
}
