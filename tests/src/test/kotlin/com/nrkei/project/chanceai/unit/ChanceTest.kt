package com.nrkei.project.chanceai.unit

import com.nrkei.project.chanceai.engine.Chance
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

/*
 * Copyright (c) 2025-26 by Fred George
 * @author Fred George  fredgeorge@acm.org
 * Licensed under the MIT License; see LICENSE file in root.
 */

class ChanceTest {

    @Test fun equals() {
        assertEquals(Chance(0.1), Chance(0.1))
        assertEquals(Chance(0.1), Chance(0.1 + 1e-10))
        assertNotEquals(Chance(0.1), Chance(0.2))
        assertNotEquals(Chance(0.1), Chance(0.9))
        assertEquals(Chance(0.0), Chance(0.0))
        assertEquals(Chance(1.0), Chance(1.0))
        assertNotEquals(Chance(0.0), Chance(1.0))
        assertNotEquals(Chance(0.1), null)
        assertNotEquals(Chance(0.1), "not a Chance")
        assertEquals(Chance.IMPOSSIBLE, Chance(0.0))
        assertEquals(Chance.CERTAIN, Chance(1.0))
    }

    @Test fun `equal chances have equal hash codes`() {
        assertEquals(Chance(0.1).hashCode(), Chance(0.1).hashCode())
        assertEquals(Chance(0.1).hashCode(), Chance(0.1 + 1e-10).hashCode())
        assertNotEquals(Chance(0.1).hashCode(), Chance(0.2).hashCode())
        assertNotEquals(Chance(0.0).hashCode(), Chance(1.0).hashCode())
        assertEquals(Chance.IMPOSSIBLE.hashCode(), Chance(0.0).hashCode())
        assertEquals(Chance.CERTAIN.hashCode(), Chance(1.0).hashCode())
    }

    @Test fun not() {
        assertEquals(Chance(0.0), Chance(1.0).not())
        assertEquals(Chance(1.0), Chance(0.0).not())
        assertEquals(Chance(0.3), !Chance(0.7))
        assertEquals(Chance(0.8), !Chance(0.2))
        assertEquals(Chance(0.4), !Chance(0.6))
    }

    @Test fun and() {
        assertEquals(Chance(0.56), Chance(0.8) and Chance(0.7))
        assertEquals(Chance(0.42), Chance(0.6) and Chance(0.7))
        assertEquals(Chance(0.8), Chance(0.8) and Chance(1.0))
        assertEquals(Chance(0.7), Chance(1.0) and Chance(0.7))
        assertEquals(Chance(0.0), Chance(0.8) and Chance(0.0))
        assertEquals(Chance(0.0), Chance(0.0) and Chance(0.7))
        assertEquals(Chance(1.0), Chance(1.0) and Chance(1.0))
    }

    @Test fun or() {
        assertEquals(Chance(0.94), Chance(0.8) or Chance(0.7))
        assertEquals(Chance(0.713), Chance(0.3) or Chance(0.59))
        assertEquals(Chance(1.0), Chance(1.0) or Chance(0.7))
        assertEquals(Chance(0.7), Chance(0.0) or Chance(0.7))
        assertEquals(Chance(0.0), Chance(0.0) or Chance(0.0))
        assertEquals(Chance(0.84), Chance(0.6) or Chance(0.6))
    }

    @Test fun `fraction must be between zero and one inclusive`() {
        assertThrows<IllegalArgumentException> { Chance(-0.1) }
        assertThrows<IllegalArgumentException> { Chance(1.1) }
        assertDoesNotThrow { Chance(0.0) }
        assertDoesNotThrow { Chance(1.0) }
        assertEquals(Chance(0), Chance.IMPOSSIBLE)
        assertEquals(Chance(1), Chance.CERTAIN)
    }
}
