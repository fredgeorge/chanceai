package com.nrkei.project.chanceai

import kotlin.math.abs
import kotlin.math.roundToLong

class Chance(private val probability: Double) {
    companion object {
        private const val EPSILON = 1e-9
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Chance) return false
        return abs(this.probability - other.probability) < EPSILON
    }

    override fun hashCode(): Int {
        return (probability / EPSILON).roundToLong().hashCode()
    }
}
