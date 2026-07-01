package com.nrkei.project.chanceai.unit

import com.nrkei.project.chanceai.engine.Chance
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertThrows

class ChanceTest {

    @Test fun equality() {
        assertEquals(Chance(0.1), Chance(0.1))
        assertEquals(Chance(0.3), Chance(0.3 + 1e-10))
        assertEquals(Chance(0.7), Chance(0.7 - 1e-10))
        assertNotEquals(Chance(0.1), Chance(0.2))
        assertNotEquals(Chance(0.3), Chance(0.8))
        assertNotEquals(Chance(0.9), Chance(0.1))
    }

    @Test fun equalityEdgeCases() {
        assertEquals(Chance(0.0), Chance(0.0))
        assertEquals(Chance(1.0), Chance(1.0))
        assertNotEquals(Chance(0.0), Chance(1.0))
        assertNotEquals(Chance(0.0), null)
        assertNotEquals(Chance(0.1), "not a Chance")
    }

    @Test fun hashCodeConsistentWithEquality() {
        assertEquals(Chance(0.1).hashCode(), Chance(0.1).hashCode())
        assertEquals(Chance(0.3).hashCode(), Chance(0.3 + 1e-10).hashCode())
        assertNotEquals(Chance(0.1).hashCode(), Chance(0.2).hashCode())
        assertEquals(Chance(0.0).hashCode(), Chance(0.0).hashCode())
        assertEquals(Chance(1.0).hashCode(), Chance(1.0).hashCode())
    }

    @Test fun not() {
        assertEquals(Chance(0.9), !Chance(0.1))
        assertEquals(Chance(0.7), !Chance(0.3))
        assertEquals(Chance(0.3), !Chance(0.7))
        assertEquals(Chance(1.0), !Chance(0.0))
        assertEquals(Chance(0.0), !Chance(1.0))
    }

    @Test fun notExplicit() {
        assertEquals(Chance(0.9), Chance(0.1).not())
        assertEquals(Chance(0.7), Chance(0.3).not())
        assertEquals(Chance(1.0), Chance(0.0).not())
        assertEquals(Chance(0.0), Chance(1.0).not())
    }

    @Test fun and() {
        assertEquals(Chance(0.0), Chance(0.0) and Chance(0.7))
        assertEquals(Chance(0.0), Chance(0.8) and Chance(0.0))
        assertEquals(Chance(1.0), Chance(1.0) and Chance(1.0))
        assertEquals(Chance(0.8), Chance(0.8) and Chance(1.0))
        assertEquals(Chance(0.7), Chance(1.0) and Chance(0.7))
        assertEquals(Chance(0.56), Chance(0.8) and Chance(0.7))
        assertEquals(Chance(0.12), Chance(0.4) and Chance(0.3))
    }

    @Test fun or() {
        assertEquals(Chance(0.0), Chance(0.0) or Chance(0.0))
        assertEquals(Chance(1.0), Chance(1.0) or Chance(1.0))
        assertEquals(Chance(1.0), Chance(1.0) or Chance(0.0))
        assertEquals(Chance(1.0), Chance(0.0) or Chance(1.0))
        assertEquals(Chance(0.94), Chance(0.8) or Chance(0.7))
        assertEquals(Chance(0.3), Chance(0.3) or Chance(0.0))
        assertEquals(Chance(0.8), Chance(0.0) or Chance(0.8))
    }

    @Test fun invalidConstruction() {
        assertThrows(IllegalArgumentException::class.java) { Chance(-0.1) }
        assertThrows(IllegalArgumentException::class.java) { Chance(1.1) }
        assertThrows(IllegalArgumentException::class.java) { Chance(Double.NaN) }
        assertThrows(IllegalArgumentException::class.java) { Chance(Double.POSITIVE_INFINITY) }
    }

    @Test fun constants() {
        assertEquals(Chance(0.0), Chance.IMPOSSIBLE)
        assertEquals(Chance(1.0), Chance.CERTAIN)
        assertEquals(Chance.IMPOSSIBLE, Chance(0.0))
        assertEquals(Chance.CERTAIN, Chance(1.0))
    }
}
