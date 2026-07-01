/*
 * Copyright (c) 2025-26 by Fred George
 * @author Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */
package com.nrkei.project.chanceai.engine

import kotlin.math.absoluteValue

class Chance(fraction: Number) {

    private val fraction: Double
    init {
        this.fraction = fraction.toDouble()
        require(this.fraction in 0.0..1.0) { "Fraction must be between 0 and 1, inclusive" }
    }

    companion object {
        const val EPSILON = 1e-9

        val IMPOSSIBLE = Chance(0.0)
        val CERTAIN = Chance(1.0)
    }

    override fun equals(other: Any?) =
        this === other || other is Chance && this.equals(other)

    private fun equals(other: Chance) =
        (this.fraction - other.fraction).absoluteValue < EPSILON

    override fun hashCode() = Math.round(fraction / EPSILON).hashCode()

    infix fun and(other: Chance) = Chance(this.fraction * other.fraction)

    operator fun not() = Chance(1.0 - fraction)

    infix fun or(other: Chance) = !(!this and !other)
}
