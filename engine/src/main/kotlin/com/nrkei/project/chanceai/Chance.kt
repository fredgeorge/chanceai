/*
 * Copyright (c) 2025-26 by Fred George
 * @author Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.chanceai

import kotlin.math.abs
import kotlin.math.round

class Chance(private val fraction: Double) {
    companion object {
        private const val EPSILON = 1e-9
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Chance) return false
        return round(this.fraction / EPSILON).toLong() == round(other.fraction / EPSILON).toLong()
    }

    override fun hashCode(): Int {
        return round(fraction / EPSILON).toLong().hashCode()
    }
}
