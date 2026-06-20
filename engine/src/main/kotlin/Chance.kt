/*
 * Copyright (c) 2025-26 by Fred George
 * @author Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

class Chance private constructor(private val fraction: Double) {
    init {
        require(fraction.isFinite()) { "probability must be finite" }
    }

    companion object {
        const val EPSILON = 1e-15

        val IMPOSSIBLE = Chance(0.0)
        val CERTAIN = Chance(1.0)

        operator fun invoke(probability: Double): Chance {
            require(probability in 0.0..1.0) { "probability $probability out of range [0.0, 1.0]" }
            return Chance(probability.coerceIn(0.0, 1.0))
        }
    }

    infix fun and(other: Chance) =Chance(this.fraction * other.fraction)
    operator fun not() =Chance(1.0 - fraction)
    infix fun or(other: Chance) = !(!this and !other)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Chance) return false
        return kotlin.math.abs(fraction - other.fraction) < 1e-15
    }

    override fun hashCode(): Int {
        val bucket = (fraction * 100_000).toLong()
        return bucket.hashCode()
    }

    override fun toString(): String = "Chance($fraction)"
}
