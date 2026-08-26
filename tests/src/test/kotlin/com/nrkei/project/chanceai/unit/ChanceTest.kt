/*
 * Copyright (c) 2025-26 by Fred George
 * @author Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

package com.nrkei.project.chanceai.unit

import com.nrkei.project.chanceai.engine.Chance
import com.nrkei.project.chanceai.engine.Chance.Companion.CERTAIN
import com.nrkei.project.chanceai.engine.Chance.Companion.IMPOSSIBLE
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

// Ensures Chance correctly captures the likelihood of something occurring
internal class ChanceTest {

    @Test
    fun equality() {
        assertEquals(Chance(0.3), Chance(0.3))
        assertEquals(Chance(0.0), Chance(0.0))
        assertEquals(Chance(1.0), Chance(1.0))
        assertEquals(Chance(0.0), Chance(0))
        assertEquals(Chance(1.0), Chance(1))
        assertNotEquals(Chance(0.3), Chance(0.7))
        assertNotEquals(Chance(0.0), Chance(1.0))
        assertNotEquals(Chance(0.0), Chance(0.001))
        assertNotEquals(Chance(1.0), Chance(0.999))
        // Neither null nor foreign types may blow up equals
        assertNotEquals(Chance(0.3), null)
        assertNotEquals(Chance(0.3), "not a Chance")
    }

    @Test
    fun `nearly identical chances are equal`() {
        assertEquals(Chance(0.3), Chance(0.1 + 0.2))
        assertEquals(Chance(0.3), Chance(0.3 + 1e-12))
        assertEquals(Chance(0.0), Chance(1e-12))
        assertEquals(Chance(1.0), Chance(1.0 - 1e-12))
        assertEquals(Chance(0.7), Chance(0.7 - 1e-11))
        // Differences large enough to matter are still detected
        assertNotEquals(Chance(0.3), Chance(0.3 + 1e-6))
    }

    @Test
    fun `equal chances share hash codes`() {
        assertEquals(Chance(0.3).hashCode(), Chance(0.3).hashCode())
        assertEquals(Chance(0.3).hashCode(), Chance(0.1 + 0.2).hashCode())
        assertEquals(Chance(0.3).hashCode(), Chance(0.3 + 1e-12).hashCode())
        assertEquals(Chance(0.0).hashCode(), Chance(1e-12).hashCode())
        assertEquals(Chance(1.0).hashCode(), Chance(1.0 - 1e-12).hashCode())
        assertEquals(Chance(0.0).hashCode(), Chance(0).hashCode())
        assertEquals(Chance(1.0).hashCode(), Chance(1).hashCode())
        // Chances behave in hashed collections
        assertEquals(2, setOf(Chance(0.3), Chance(0.1 + 0.2), Chance(0.7)).size)
    }

    @Test
    fun negation() {
        assertEquals(Chance(0.7), !Chance(0.3))
        assertEquals(Chance(0.3), !Chance(0.7))
        assertEquals(Chance(0.0), !Chance(1.0))
        assertEquals(Chance(1.0), !Chance(0.0))
        assertEquals(Chance(0.3), !(!Chance(0.3)))
    }

    @Test
    fun `negation without the operator`() {
        assertEquals(Chance(0.7), Chance(0.3).not())
        assertEquals(Chance(0.3), Chance(0.7).not())
        assertEquals(Chance(0.0), Chance(1.0).not())
        assertEquals(Chance(1.0), Chance(0.0).not())
        assertEquals(Chance(0.3), Chance(0.3).not().not())
    }

    @Test
    fun conjunction() {
        assertEquals(Chance(0.56), Chance(0.8) and Chance(0.7))
        assertEquals(Chance(0.06), Chance(0.2) and Chance(0.3))
        assertEquals(Chance(0.09), Chance(0.3) and Chance(0.3))
        assertEquals(Chance(0.8), Chance(0.8) and Chance(1.0))
        assertEquals(Chance(0.7), Chance(1.0) and Chance(0.7))
        assertEquals(Chance(0.0), Chance(0.8) and Chance(0.0))
        assertEquals(Chance(0.0), Chance(0.0) and Chance(0.7))
        assertEquals(Chance(0.56), Chance(0.8).and(Chance(0.7)))
    }

    @Test
    fun disjunction() {
        assertEquals(Chance(0.94), Chance(0.8) or Chance(0.7))
        assertEquals(Chance(0.44), Chance(0.2) or Chance(0.3))
        assertEquals(Chance(0.51), Chance(0.3) or Chance(0.3))
        assertEquals(Chance(1.0), Chance(0.8) or Chance(1.0))
        assertEquals(Chance(1.0), Chance(1.0) or Chance(0.7))
        assertEquals(Chance(0.8), Chance(0.8) or Chance(0.0))
        assertEquals(Chance(0.7), Chance(0.0) or Chance(0.7))
        assertEquals(Chance(0.94), Chance(0.8).or(Chance(0.7)))
    }

    @Test
    fun `deMorgan's law holds`() {
        assertEquals(!(Chance(0.2) and Chance(0.3)), !Chance(0.2) or !Chance(0.3))
        assertEquals(!(Chance(0.2) or Chance(0.3)), !Chance(0.2) and !Chance(0.3))
    }

    @Test
    fun constants() {
        assertEquals(Chance(0.0), IMPOSSIBLE)
        assertEquals(Chance(1.0), CERTAIN)
        assertEquals(CERTAIN, !IMPOSSIBLE)
        assertEquals(IMPOSSIBLE, !CERTAIN)
        assertEquals(IMPOSSIBLE, IMPOSSIBLE and CERTAIN)
        assertEquals(CERTAIN, IMPOSSIBLE or CERTAIN)
        assertEquals(Chance(0.3), CERTAIN and Chance(0.3))
        assertEquals(Chance(0.3), IMPOSSIBLE or Chance(0.3))
    }

    @Test
    fun `fractions outside zero to one are rejected`() {
        assertThrows<IllegalArgumentException> { Chance(-0.001) }
        assertThrows<IllegalArgumentException> { Chance(1.001) }
        assertThrows<IllegalArgumentException> { Chance(-1) }
        assertThrows<IllegalArgumentException> { Chance(2) }
        assertThrows<IllegalArgumentException> { Chance(Double.NaN) }
        assertThrows<IllegalArgumentException> { Chance(Double.POSITIVE_INFINITY) }
        assertThrows<IllegalArgumentException> { Chance(Double.NEGATIVE_INFINITY) }
        // Boundaries themselves are legal
        assertDoesNotThrow { Chance(0.0) }
        assertDoesNotThrow { Chance(1.0) }
    }
}
