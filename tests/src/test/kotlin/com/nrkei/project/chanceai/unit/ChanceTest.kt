package com.nrkei.project.chanceai.unit

import com.nrkei.project.chanceai.Chance
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ChanceTest {
    @Test
    fun equalsAndHashCode() {
        assertEquals(Chance(0.2), Chance(0.2 + 1e-10))
        assertNotEquals(Chance(0.2), Chance(0.3))
        assertEquals(Chance(0.7), Chance(0.7 + 1e-11))
        assertEquals(Chance(0.7).hashCode(), Chance(0.7 + 1e-11).hashCode())
        assertEquals(Chance(0.0), Chance(0.0 + 1e-11))
        assertEquals(Chance(0.0).hashCode(), Chance(0.0 + 1e-11).hashCode())
        assertEquals(Chance(1.0), Chance(1.0 - 1e-11))
        assertEquals(Chance(1.0).hashCode(), Chance(1.0 - 1e-11).hashCode())
    }

    @Test
    fun not() {
        val c = Chance(0.3)
        assertEquals(Chance(0.7), !c)
        assertEquals(Chance(0.7), c.not())
    }

    @Test
    fun and() {
        assertEquals(Chance(0.12), Chance(0.4) and Chance(0.3))
    }

    @Test
    fun or() {
        assertEquals(Chance(0.58), Chance(0.4) or Chance(0.3))
    }

    @Test
    fun validation() {
        assertThrows<IllegalArgumentException> { Chance(-0.1) }
        assertThrows<IllegalArgumentException> { Chance(1.1) }
        Chance(0.0)
        Chance(1.0)
    }

    @Test
    fun constants() {
        assertEquals(Chance(0.0), Chance.IMPOSSIBLE)
        assertEquals(Chance(1.0), Chance.CERTAIN)
    }
}
