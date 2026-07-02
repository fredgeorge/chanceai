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

    @Test fun equals() {
        assertEquals(Chance(0.1), Chance(0.1))
        assertEquals(Chance(0.3), Chance(0.3))
        assertEquals(Chance(0.9), Chance(0.9))
        assertEquals(Chance(0.0), Chance(0.0))
        assertEquals(Chance(1.0), Chance(1.0))
    }

    @Test fun equalsWithCloseValues() {
        assertEquals(Chance(0.1), Chance(0.1 + 0.0000001))
        assertEquals(Chance(0.3), Chance(0.3 - 0.0000001))
        assertEquals(Chance(0.9), Chance(0.9 + 0.00000005))
    }

    @Test fun notEqual() {
        assertNotEquals(Chance(0.1), Chance(0.2))
        assertNotEquals(Chance(0.3), Chance(0.4))
        assertNotEquals(Chance(0.0), Chance(1.0))
    }

    @Test fun equalsWithNullAndOtherTypes() {
        assertNotEquals(Chance(0.1), null)
        assertNotEquals(Chance(0.1), "not a chance")
        assertNotEquals(Chance(0.1), 42)
    }

    @Test fun hashCodeConsistency() {
        val c1 = Chance(0.1)
        val c2 = Chance(0.1 + 0.0000001)
        assertEquals(c1.hashCode(), c2.hashCode())

        val c3 = Chance(0.3)
        val c4 = Chance(0.3 - 0.0000001)
        assertEquals(c3.hashCode(), c4.hashCode())

        assertEquals(Chance(0.0).hashCode(), Chance(0.0).hashCode())
        assertEquals(Chance(1.0).hashCode(), Chance(1.0).hashCode())
    }

    @Test fun notHashCodeConsistency() {
        val c1 = Chance(0.2)
        val c2 = Chance(0.2 + 0.00000005)
        assertEquals(c1.hashCode(), c2.hashCode())
    }

    @Test fun validation() {
        assertThrows<IllegalArgumentException> { Chance(-0.1) }
        assertThrows<IllegalArgumentException> { Chance(1.1) }
        assertThrows<IllegalArgumentException> { Chance(-1.0) }
        assertThrows<IllegalArgumentException> { Chance(2.0) }
    }

    @Test fun not() {
        assertEquals(Chance(0.9), !Chance(0.1))
        assertEquals(Chance(0.7), !Chance(0.3))
        assertEquals(Chance(1.0), !Chance(0.0))
        assertEquals(Chance(0.0), !Chance(1.0))
    }

    @Test fun notWithFunctionCall() {
        assertEquals(Chance(0.9), Chance(0.1).not())
        assertEquals(Chance(0.7), Chance(0.3).not())
        assertEquals(Chance(1.0), Chance(0.0).not())
        assertEquals(Chance(0.0), Chance(1.0).not())
    }

    @Test fun and() {
        assertEquals(Chance(0.06), Chance(0.3) and Chance(0.2))
        assertEquals(Chance(0.42), Chance(0.7) and Chance(0.6))
        assertEquals(Chance(1.0), Chance(1.0) and Chance(1.0))
        assertEquals(Chance(0.0), Chance(0.0) and Chance(0.8))
        assertEquals(Chance(0.0), Chance(0.8) and Chance(0.0))
        assertEquals(Chance(0.8), Chance(1.0) and Chance(0.8))
        assertEquals(Chance(0.8), Chance(0.8) and Chance(1.0))
    }

    @Test fun or() {
        assertEquals(Chance(0.44), Chance(0.2) or Chance(0.3))
        assertEquals(Chance(0.78125), Chance(0.75) or Chance(0.125))
        assertEquals(Chance(1.0), Chance(1.0) or Chance(0.6))
        assertEquals(Chance(1.0), Chance(0.6) or Chance(1.0))
        assertEquals(Chance(0.0), Chance(0.0) or Chance(0.0))
    }

    @Test fun orWithFunctionCall() {
        assertEquals(Chance(0.44), Chance(0.2).or(Chance(0.3)))
        assertEquals(Chance(0.78125), Chance(0.75).or(Chance(0.125)))
        assertEquals(Chance(1.0), Chance(1.0).or(Chance(0.6)))
    }

    @Test fun constants() {
        assertEquals(Chance(0.0), Chance.IMPOSSIBLE)
        assertEquals(Chance(1.0), Chance.CERTAIN)
    }

    @Test fun impossibleAndCertain() {
        assertEquals(Chance(0.0), Chance.IMPOSSIBLE and Chance.CERTAIN)
        assertEquals(Chance(0.0), Chance.CERTAIN and Chance.IMPOSSIBLE)
        assertEquals(Chance(1.0), Chance.IMPOSSIBLE or Chance.CERTAIN)
        assertEquals(Chance(1.0), Chance.CERTAIN or Chance.IMPOSSIBLE)
    }

    @Test fun notNot() {
        assertEquals(Chance(0.3), !!Chance(0.3))
        assertEquals(Chance(0.7), !!Chance(0.7))
    }

    @Test fun andAssociativity() {
        val a = Chance(0.2)
        val b = Chance(0.3)
        val c = Chance(0.4)
        assertEquals(a and (b and c), (a and b) and c)
    }

    @Test fun orAssociativity() {
        val a = Chance(0.2)
        val b = Chance(0.3)
        val c = Chance(0.4)
        assertEquals(a or (b or c), (a or b) or c)
    }

    @Test fun deMorganLaw() {
        val a = Chance(0.2)
        val b = Chance(0.3)
        assertEquals(!(a and b), !a or !b)
        assertEquals(!(a or b), !a and !b)
    }

    @Test fun constructionWithInt() {
        assertEquals(Chance(1.0), Chance(1))
        assertEquals(Chance(0.0), Chance(0))
    }
}
