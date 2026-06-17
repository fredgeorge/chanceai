/*
 * Copyright (c) 2025-26 by Fred George
 * @author Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.chanceai.unit

import com.nrkei.project.chanceai.Chance
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ChanceTest {
    @Test
    fun `identical probabilities are equal`() {
        val c1 = Chance(0.5)
        val c2 = Chance(0.5)
        assertEquals(c1, c2)
        assertEquals(c1.hashCode(), c2.hashCode())
    }

    @Test
    fun `very close probabilities are equal`() {
        val c1 = Chance(0.5)
        val c2 = Chance(0.5 + 1e-10)
        assertEquals(c1, c2)
        assertEquals(c1.hashCode(), c2.hashCode())
    }

    @Test
    fun `distant probabilities are not equal`() {
        val c1 = Chance(0.5)
        val c2 = Chance(0.6)
        assertNotEquals(c1, c2)
    }

    @Test
    fun `probabilites on bucket boundary might be unequal but hashcodes must match if equals is true`() {
        // This test ensures the contract: a == b => a.hashCode == b.hashCode
        val p1 = 0.5
        val p2 = 0.5 + 1e-11 // very close
        val c1 = Chance(p1)
        val c2 = Chance(p2)
        if (c1 == c2) {
            assertEquals(c1.hashCode(), c2.hashCode())
        }
    }
}
