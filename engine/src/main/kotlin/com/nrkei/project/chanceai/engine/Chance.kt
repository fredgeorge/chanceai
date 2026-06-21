package com.nrkei.project.chanceai.engine

import kotlin.math.abs
import kotlin.math.roundToLong

class Chance(fraction: Double) {
    private val fraction: Double

    init {
        require(fraction in 0.0..1.0) { "Chance must be between 0.0 and 1.0, inclusive" }
        this.fraction = fraction
    }

    override fun equals(other: Any?): Boolean =
        this === other || other is Chance && this.equals(other)

    private fun equals(other: Chance): Boolean =
        abs(this.fraction - other.fraction) < EPSILON

    override fun hashCode(): Int {
        val scale = 1.0 / EPSILON
        val quantized = kotlin.math.round(fraction * scale)
        return quantized.toInt()
    }

    infix fun and(other: Chance): Chance =
        Chance(fraction * other.fraction)

    operator fun not(): Chance =
        Chance(1 - fraction)

    infix fun or(other: Chance): Chance =
        !(!this and !other)

    companion object {
        const val EPSILON: Double = 1e-10

        val IMPOSSIBLE: Chance = Chance(0.0)
        val CERTAIN: Chance = Chance(1.0)
    }
}
