/*
 * Copyright (c) 2025-26 by Fred George
 * @author Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.chanceai.engine

import kotlin.math.absoluteValue
import kotlin.math.roundToLong

// Understands the likelihood of something occurring
class Chance(fraction: Number) {
    private val fraction = fraction.toDouble()

    init {
        require(this.fraction in IMPOSSIBLE_FRACTION..CERTAIN_FRACTION) {
            "Chance must be between $IMPOSSIBLE_FRACTION and $CERTAIN_FRACTION, inclusive, but was ${this.fraction}"
        }
    }

    operator fun not() = Chance(CERTAIN_FRACTION - fraction)

    infix fun and(other: Chance) = Chance(this.fraction * other.fraction)

    infix fun or(other: Chance) = !(!this and !other)

    override fun equals(other: Any?) = this === other || other is Chance && this.equals(other)

    private fun equals(other: Chance) = (this.fraction - other.fraction).absoluteValue < EPSILON

    // Buckets far coarser than EPSILON so that values equal within EPSILON share a bucket
    override fun hashCode() = (fraction / HASH_GRANULARITY).roundToLong().hashCode()

    override fun toString() = "Chance($fraction)"

    companion object {
        private const val IMPOSSIBLE_FRACTION = 0.0
        private const val CERTAIN_FRACTION = 1.0
        private const val EPSILON = 1e-9
        private const val HASH_GRANULARITY = 1e-6

        val IMPOSSIBLE = Chance(IMPOSSIBLE_FRACTION)
        val CERTAIN = Chance(CERTAIN_FRACTION)
    }
}
