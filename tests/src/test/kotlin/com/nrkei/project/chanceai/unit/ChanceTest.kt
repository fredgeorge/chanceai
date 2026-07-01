/*
 * Copyright (c) 2025-26 by Fred George
 * @author Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */
package com.nrkei.project.chanceai.unit

import com.nrkei.project.chanceai.engine.Chance
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals

class ChanceTest {

    @Test
    fun `equals with close values`() {
        assertEquals(Chance(0.1), Chance(0.1 + 1e-10))
        assertEquals(Chance(0.3), Chance(0.3 - 1e-10))
        assertEquals(Chance(0.9), Chance(0.9 + 1e-10))
    }

    @Test
    fun `equals with different values`() {
        assertNotEquals(Chance(0.1), Chance(0.2))
        assertNotEquals(Chance(0.3), Chance(0.4))
        assertNotEquals(Chance(0.7), Chance(0.8))
    }

    @Test
    fun `equals with boundary values`() {
        assertEquals(Chance(0.0), Chance(0.0))
        assertEquals(Chance(1.0), Chance(1.0))
        assertNotEquals(Chance(0.0), Chance(1.0))
    }

    @Test
    fun `equals handles null and wrong type`() {
        assertNotEquals(Chance(0.1), null)
        assertNotEquals(Chance(0.1), "not a Chance")
        assertNotEquals(Chance(0.1), 42)
    }

    @Test
    fun `hashCode consistency with equals`() {
        assertEquals(Chance(0.1).hashCode(), Chance(0.1 + 1e-10).hashCode())
        assertEquals(Chance(0.3).hashCode(), Chance(0.3 - 1e-10).hashCode())
        assertEquals(Chance(0.0).hashCode(), Chance(0.0).hashCode())
        assertEquals(Chance(1.0).hashCode(), Chance(1.0).hashCode())
    }

    @Test
    fun `not operator`() {
        assertEquals(Chance(0.5), !Chance(0.5))
        assertEquals(Chance(0.3), !Chance(0.7))
        assertEquals(Chance(0.0), !Chance(1.0))
        assertEquals(Chance(1.0), !Chance(0.0))
    }

    @Test
    fun `not without operator syntax`() {
        val chance = Chance(0.7)
        assertEquals(Chance(0.3), chance.not())
    }

    @Test
    fun `and operation`() {
        assertEquals(Chance(0.56), Chance(0.8) and Chance(0.7))
        assertEquals(Chance(0.0), Chance(0.8) and Chance(0.0))
        assertEquals(Chance(0.0), Chance(0.0) and Chance(0.7))
        assertEquals(Chance(0.8), Chance(0.8) and Chance(1.0))
        assertEquals(Chance(0.7), Chance(1.0) and Chance(0.7))
    }

    @Test
    fun `or operation using DeMorgan's law`() {
        assertEquals(Chance(0.94), Chance(0.7) or Chance(0.8))
        assertEquals(Chance(0.86), Chance(0.8) or Chance(0.3))
        assertEquals(Chance(0.37), Chance(0.3) or Chance(0.1))
        assertEquals(Chance(1.0), Chance(1.0) or Chance(0.5))
        assertEquals(Chance(0.0), Chance(0.0) or Chance(0.0))
    }

    @Test
    fun `constructor validation rejects invalid values`() {
        assertThrows<IllegalArgumentException> { Chance(-0.1) }
        assertThrows<IllegalArgumentException> { Chance(1.1) }
        assertThrows<IllegalArgumentException> { Chance(-1.0) }
        assertThrows<IllegalArgumentException> { Chance(2.0) }
    }

    @Test
    fun `constructor accepts valid range`() {
        Chance(0.0)
        Chance(1.0)
        Chance(0.5)
        Chance(0.1)
        Chance(0.99)
    }

    @Test
    fun `IMPOSSIBLE and CERTAIN constants`() {
        assertEquals(Chance(0.0), Chance.IMPOSSIBLE)
        assertEquals(Chance(1.0), Chance.CERTAIN)
        assertNotEquals(Chance.IMPOSSIBLE, Chance.CERTAIN)
    }

    @Test
    fun `complex probability calculations`() {
        val a = Chance(0.5)
        val b = Chance(0.6)
        val c = Chance(0.7)

        assertEquals(Chance(0.21), a and b and c)
        assertEquals(Chance(0.94), a or b or c)
    }
}
