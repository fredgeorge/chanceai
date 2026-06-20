package com.nrkei.unit

import com.nrkei.engine.Chance
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ChanceTest {

    @Test fun equality() {
        assertTrue(Chance(0.0) == Chance(0.0))
        assertTrue(Chance(1.0) == Chance(1.0))
        assertTrue(Chance(0.25) == Chance(0.25))
        assertTrue(Chance(0.001) == Chance(0.001))
        val veryClose1 = Chance(0.123456789123)
        val veryClose2 = Chance(0.123456789124)
        assertTrue(veryClose1 == veryClose2)
    }

    @Test fun inequality() {
        assertTrue(Chance(0.0) != Chance(1.0))
        assertTrue(Chance(0.1) != Chance(0.2))
        assertTrue(Chance(0.0) != Chance(0.001))
        val farEnough1 = Chance(0.123456789)
        val farEnough2 = Chance(0.123456792)
        assertTrue(farEnough1 != farEnough2)
    }

    @Test fun hashCodeConsistency() {
        assertEquals(Chance(0.0).hashCode(), Chance(0.0).hashCode())
        assertEquals(Chance(1.0).hashCode(), Chance(1.0).hashCode())
        assertEquals(Chance(0.25).hashCode(), Chance(0.25).hashCode())
        val veryClose1 = Chance(0.123456789123)
        val veryClose2 = Chance(0.123456789124)
        assertEquals(veryClose1.hashCode(), veryClose2.hashCode())
    }

    @Test fun notOperator() {
        assertEquals(Chance(1.0), !Chance(0.0))
        assertEquals(Chance(0.0), !Chance(1.0))
        assertEquals(Chance(0.6), !Chance(0.4))
        assertEquals(Chance(0.25), !Chance(0.75))
    }

    @Test fun notExplicitly() {
        assertEquals(Chance(1.0), Chance(0.0).not())
        assertEquals(Chance(0.0), Chance(1.0).not())
    }

    @Test fun and() {
        assertEquals(Chance(0.25), Chance(0.5) and Chance(0.5))
        assertEquals(Chance(0.56), Chance(0.8) and Chance(0.7))
        assertEquals(Chance(0.8), Chance(0.8) and Chance(1.0))
        assertEquals(Chance(0.0), Chance(0.0) and Chance(0.7))
    }

    @Test fun orUsingOperator() {
        assertEquals(Chance(0.75), Chance(0.5) or Chance(0.5))
        assertEquals(Chance(0.94), Chance(0.8) or Chance(0.7))
        assertEquals(Chance(1.0), Chance(1.0) or Chance(0.5))
    }

    @Test fun boundaryConstruction() {
        assertEquals(Chance(0.0), Chance.IMPOSSIBLE)
        assertEquals(Chance(1.0), Chance.CERTAIN)
    }

    @Test fun invalidConstruction() {
        assertThrows<IllegalArgumentException> { Chance(-0.1) }
        assertThrows<IllegalArgumentException> { Chance(1.1) }
    }
}
