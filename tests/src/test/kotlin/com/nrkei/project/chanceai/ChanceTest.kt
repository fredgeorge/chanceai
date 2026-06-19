package com.nrkei.project.chanceai

import org.junit.jupiter.api.Test
import kotlin.math.abs
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.assertThrows

internal class ChanceTest {

    @Test fun equality() {
        assertEquals(Chance(0.3), Chance(0.3))
        assertEquals(Chance(0.123456789), Chance(0.123456789 + 1e-10))
        assertEquals(Chance(0.75), Chance(0.75 + 1e-10))
        assertNotEquals(Chance(0.3), Chance(0.7))
    }

    @Test fun hashCodeConsistent() {
        val c1 = Chance(0.25)
        val c2 = Chance(0.25)
        assertEquals(c1.hashCode(), c2.hashCode())
        
        val c3 = Chance(0.75)
        val c4 = Chance(0.75 + 1e-10)
        assertEquals(c3.hashCode(), c4.hashCode())
        
        val c5 = Chance(0.0)
        val c6 = Chance(0.0)
        assertEquals(c5.hashCode(), c6.hashCode())
        
        val c7 = Chance(1.0)
        val c8 = Chance(1.0)
        assertEquals(c7.hashCode(), c8.hashCode())
    }

    @Test fun invalidProbability() {
        assertThrows<IllegalArgumentException> { Chance(-0.1) }
        assertThrows<IllegalArgumentException> { Chance(1.1) }
    }

    @Test fun not() {
        assertEquals(Chance(0.7), !Chance(0.3))
        assertEquals(Chance(0.6), !Chance(0.4))
        assertEquals(Chance(1.0), !Chance(0.0))
        assertEquals(Chance(0.0), !Chance(1.0))
    }

    @Test fun and() {
        assertEquals(Chance(0.25), Chance(0.5) and Chance(0.5))
        assertEquals(Chance(0.56), Chance(0.8) and Chance(0.7))
        assertEquals(Chance(0.8), Chance(0.8) and Chance(1.0))
        assertEquals(Chance(0.7), Chance(1.0) and Chance(0.7))
        assertEquals(Chance(0.0), Chance(0.8) and Chance(0.0))
        assertEquals(Chance(0.0), Chance(0.0) and Chance(0.7))
    }

    @Test fun or() {
        val c1 = Chance(0.4)
        val c2 = Chance(0.5)
        val expected = !(c1.not() and c2.not())
        assertEquals(expected, c1 or c2)

        assertEquals(Chance(0.3 + 0.4 - (0.3 * 0.4)), Chance(0.3) or Chance(0.4))
        assertEquals(Chance(0.6), Chance(0.0) or Chance(0.6))
        assertEquals(Chance(1.0), Chance(1.0) or Chance(0.5))
    }

    @Test fun constants() {
        assertEquals(Chance.IMPOSSIBLE, Chance(0.0))
        assertEquals(Chance.CERTAIN, Chance(1.0))
    }
}
