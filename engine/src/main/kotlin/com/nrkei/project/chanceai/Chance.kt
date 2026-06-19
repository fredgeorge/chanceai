package com.nrkei.project.chanceai

class Chance private constructor(private val fraction: Double) {
    companion object {
        val IMPOSSIBLE = Chance(0.0)
        val CERTAIN = Chance(1.0)

        val Double.chance get() = Chance(this)
    }

    init {
        require(fraction in 0.0..1.0) { "Probability must be between 0.0 and 1.0, was $fraction" }
    }

    infix fun and(other: Chance) = Chance(clamp(fraction * other.fraction))

    operator fun not() = Chance(clamp(1.0 - fraction))

    infix fun or(other: Chance) = !(!this and !other)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Chance) return false
        return kotlin.math.abs(fraction - other.fraction) < 1e-15
    }

    override fun hashCode() = fraction.toULong().hashCode()

    private fun clamp(value: Double) = value.coerceIn(0.0, 1.0)
}
