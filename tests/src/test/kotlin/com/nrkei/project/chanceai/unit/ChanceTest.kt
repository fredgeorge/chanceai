package com.nrkei.project.chanceai.unit

import com.nrkei.project.chanceai.Chance
import com.nrkei.project.chanceai.Chance.Companion.chance
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.Assertions.*

class ChanceTest {
    @Test fun construction() {
        assertEquals(Chance.IMPOSSIBLE, 0.0.chance)
        assertEquals(Chance.CERTAIN, 1.0.chance)
        assertThrows(IllegalArgumentException::class.java) { (-0.1).chance }
        assertThrows(IllegalArgumentException::class.java) { (1.1).chance }
    }

    @Test fun constants() {
        assertAll(
            { assertEquals(0.0.chance, Chance.IMPOSSIBLE) },
            { assertEquals(1.0.chance, Chance.CERTAIN) }
        )
    }

    @Test fun equality() {
        assertTrue(0.0.chance == 0.0.chance)
        assertTrue(1.0.chance == 1.0.chance)
        assertTrue(0.25.chance == 0.25.chance)
        assertTrue(0.75.chance == 0.75.chance)
        assertFalse(0.0.chance == (1.0).chance)
        assertEquals(0.25.chance.hashCode(), 0.25.chance.hashCode())
        val veryClose = 0.3333333333333333
        val veryClose2 = 0.3333333333333334
        assertEquals(veryClose.chance, veryClose2.chance)
    }

    @Test fun `not operator`() {
        assertEquals(Chance.CERTAIN, !Chance.IMPOSSIBLE)
        assertEquals(Chance.IMPOSSIBLE, !Chance.CERTAIN)
        assertAll(
            { assertEquals(0.75.chance, !(0.25).chance) },
            { assertEquals(0.25.chance, !(0.75).chance) }
        )
    }

    @Test fun `not method`() {
        assertAll(
            { assertEquals(1.0.chance, 0.0.chance.not()) },
            { assertEquals(0.0.chance, 1.0.chance.not()) },
            { assertEquals(0.8.chance, 0.2.chance.not()) }
        )
    }

    @Test fun `and`(){
        assertEquals(0.25.chance, 0.5.chance and 0.5.chance)
        assertEquals(0.56.chance, 0.8.chance and 0.7.chance)
        assertEquals(0.8.chance, 0.8.chance and Chance.CERTAIN)
        assertEquals(0.8.chance, Chance.CERTAIN and 0.8.chance)
        assertEquals(Chance.IMPOSSIBLE, 0.3.chance and Chance.IMPOSSIBLE)
        assertEquals(Chance.IMPOSSIBLE, Chance.IMPOSSIBLE and 0.9.chance)
    }

    @Test fun `or`() {
        assertEquals(Chance.CERTAIN, Chance.CERTAIN or 0.5.chance)
        assertEquals(Chance.CERTAIN, 0.3.chance or Chance.CERTAIN)
        assertEquals(Chance.IMPOSSIBLE, Chance.IMPOSSIBLE or Chance.IMPOSSIBLE)
        assertEquals(0.3.chance, Chance.IMPOSSIBLE or 0.3.chance)
        assertEquals(0.8.chance, 0.8.chance or Chance.IMPOSSIBLE)
    }
}
