/*
 * Copyright (c) 2025-26 by Fred George
 * @author Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ChanceTest {
    @Test fun construction() {
        assertEquals(Chance(0.0), Chance.IMPOSSIBLE)
        assertEquals(Chance(1.0), Chance.CERTAIN)
        assertEquals(Chance(0.25), Chance(0.25))
        assertEquals(Chance(0.75), Chance(0.75))
    }

    @Test fun constructionBounds() {
        assertThrows<IllegalArgumentException> { Chance(-0.1) }
        assertThrows<IllegalArgumentException> { Chance(1.1) }
        assertThrows<IllegalArgumentException> { Chance(Double.NaN) }
    }

    @Test fun equality() {
        assertTrue(Chance(0.0) == Chance(0.0))
        assertTrue(Chance(1.0) == Chance(1.0))
        assertTrue(Chance(0.25) == Chance(0.25))
        assertTrue(Chance(0.75) == Chance(0.75))
        assertFalse(Chance(0.0) == Chance(1.0))
        assertFalse(Chance(0.25) == Chance(0.75))
        val veryClose1 = 0.123456789012345
        val veryClose2 = 0.123456789012346
        assertTrue(Chance(veryClose1) == Chance(veryClose2))
    }

    @Test fun hashCodeConsistency() {
        val c1 = Chance(0.25)
        val c2 = Chance(0.25)
        assertTrue(c1 == c2)
        assertEquals(c1.hashCode(), c2.hashCode())
        assertEquals(Chance.IMPOSSIBLE.hashCode(), Chance(0.0).hashCode())
        assertEquals(Chance.CERTAIN.hashCode(), Chance(1.0).hashCode())
    }

    @Test fun not() {
        assertEquals(Chance(1.0), !Chance(0.0))
        assertEquals(Chance(0.0), !Chance(1.0))
        assertEquals(Chance(0.75), !Chance(0.25))
        assertEquals(Chance(0.25), !Chance(0.75))
    }

    @Test fun notOperator() {
        val c = Chance(0.3)
        val negated: Chance = !c
        assertEquals(Chance(0.7), negated)
    }

    @Test fun and() {
        assertEquals(Chance(0.0), Chance(0.0) and Chance(0.0))
        assertEquals(Chance(1.0), Chance(1.0) and Chance(1.0))
        assertEquals(Chance(0.0), Chance(0.0) and Chance(1.0))
        assertEquals(Chance(0.0), Chance(1.0) and Chance(0.0))
        assertEquals(Chance(0.25), Chance(0.5) and Chance(0.5))
        assertEquals(Chance(0.56), Chance(0.8) and Chance(0.7))
        assertEquals(Chance(0.3), Chance(1.0) and Chance(0.3))
        assertEquals(Chance(0.0), Chance(0.0) and Chance(0.9))
    }

    @Test fun or() {
        assertEquals(Chance(0.0), Chance(0.0) or Chance(0.0))
        assertEquals(Chance(1.0), Chance(1.0) or Chance(1.0))
        assertEquals(Chance(1.0), Chance(0.0) or Chance(1.0))
        assertEquals(Chance(1.0), Chance(1.0) or Chance(0.0))
        assertEquals(Chance(0.75), Chance(0.5) or Chance(0.5))
        assertEquals(Chance(0.94), Chance(0.8) or Chance(0.7))
    }

    @Test fun demorganLaw() {
        val a = Chance(0.3)
        val b = Chance(0.6)
        assertEquals(!(!a and !b), a or b)
    }

    @Test fun constants() {
        val impossible: Chance = Chance.IMPOSSIBLE
        val certain: Chance = Chance.CERTAIN
        assertEquals(Chance(0.0), impossible)
        assertEquals(Chance(1.0), certain)
    }
}
