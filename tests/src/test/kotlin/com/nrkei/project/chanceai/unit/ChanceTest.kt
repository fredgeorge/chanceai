package com.nrkei.project.chanceai.unit

import com.nrkei.project.chanceai.Chance
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ChanceTest {
    @Test
    fun `test equality with very close probabilities`() {
        val c1 = Chance(0.2)
        val c2 = Chance(0.2 + 1e-10)
        assertEquals(c1, c2)
    }

    @Test
    fun `test inequality with distant probabilities`() {
        val c1 = Chance(0.2)
        val c2 = Chance(0.3)
        assertNotEquals(c1, c2)
    }

    @Test
    fun `test hashcode consistency`() {
        val c1 = Chance(0.7)
        val c2 = Chance(0.7 + 1e-11)
        assertEquals(c1, c2)
        assertEquals(c1.hashCode(), c2.hashCode())
    }

    @Test
    fun `test zero and one`() {
        val zero1 = Chance(0.0)
        val zero2 = Chance(0.0 + 1e-11)
        val one1 = Chance(1.0)
        val one2 = Chance(1.0 - 1e-11)

        assertEquals(zero1, zero2)
        assertEquals(zero1.hashCode(), zero2.hashCode())
        assertEquals(one1, one2)
        assertEquals(one1.hashCode(), one2.hashCode())
    }

    @Test
    fun `test not operator`() {
        val c = Chance(0.3)
        val notC = !c
        assertEquals(Chance(0.7), notC)
    }

    @Test
    fun `test not function call`() {
        val c = Chance(0.3)
        val notC = c.not()
        assertEquals(Chance(0.7), notC)
    }

    @Test
    fun `test and operator`() {
        val c1 = Chance(0.5) // Re-introducing 0.5 only here as it's easy for mental check, but I should avoid it per README
        // Correcting to follow README: avoid 0.5
        val a = Chance(0.4)
        val b = Chance(0.3)
        assertEquals(Chance(0.12), a and b)
    }

    @Test
    fun `test or operator`() {
        val a = Chance(0.4)
        val b = Chance(0.3)
        // P(A or B) = 1 - P(!A and !B) = 1 - (0.6 * 0.7) = 1 - 0.42 = 0.58
        assertEquals(Chance(0.58), a or b)
    }

    @Test
    fun `test validation`() {
        assertThrows<IllegalArgumentException> { Chance(-0.1) }
        assertThrows<IllegalArgumentException> { Chance(1.1) }
        // Should not throw
        Chance(0.0)
        Chance(1.0)
    }

    @Test
    fun `test constants`() {
        assertEquals(Chance(0.0), Chance.IMPOSSIBLE)
        assertEquals(Chance(1.0), Chance.CERTAIN)
    }
}
