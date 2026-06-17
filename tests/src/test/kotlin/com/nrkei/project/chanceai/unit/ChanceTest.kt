package com.nrkei.project.chanceai.unit

import com.nrkei.project.chanceai.Chance
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ChanceTest {
    @Test
    fun `test equality with very close probabilities`() {
        val c1 = Chance(0.5)
        val c2 = Chance(0.5 + 1e-10)
        assertEquals(c1, c2)
    }

    @Test
    fun `test inequality with distant probabilities`() {
        val c1 = Chance(0.5)
        val c2 = Chance(0.6)
        assertNotEquals(c1, c2)
    }

    @Test
    fun `test hashcode consistency`() {
        val c1 = Chance(0.5)
        val c2 = Chance(0.5 + 1e-11)
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
}
