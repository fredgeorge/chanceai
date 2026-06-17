package com.nrkei.project.chanceai

import kotlin.math.abs
import kotlin.math.roundToLong

class Chance(probability: Double) {
    init {
        require(probability in 0.0..1.0) { "Probability must be between 0.0 and 1.0 inclusive" }
    }

    private val probability = probability

    companion object {
        private const val EPSILON = 1e-9
        val IMPOSSIBLE = Chance(0.0)
        val CERTAIN = Chance(1.0)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Chance) return false
        return abs(this.probability - other.probability) < EPSILON
    }

    override fun hashCode(): Int {
        return (probability / EPSILON).roundToLong().hashCode()
    }

    operator fun not(): Chance = Chance(1.0 - probability)

    infix fun and(other: Chance): Chance = Chance(this.probability * other.probability)

    infix fun or(other: Chance): Chance = !((!this) and (!other))
}
