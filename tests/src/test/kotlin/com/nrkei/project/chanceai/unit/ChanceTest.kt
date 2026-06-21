package com.nrkei.project.chanceai.unit

import com.nrkei.project.chanceai.engine.Chance
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ChanceTest {

    @Test
    fun `equality and hashCode with very close probabilities`() {
        val base = 0.123456789
        val p1 = Chance(base)
        val p2 = Chance(base + Chance.EPSILON / 2)
        val p3 = Chance(base + Chance.EPSILON * 2)

        assert(p1 == p2) { "p1 should equal p2 (within EPSILON)" }
        assert(p1.hashCode() == p2.hashCode()) { "Equal objects must have same hash code" }
        assert(p1 != p3) { "p1 should not equal p3 (beyond EPSILON)" }
    }

    @Test
    fun `zero and one values`() {
        val impossible = Chance(0.0)
        val certain = Chance(1.0)

        assert(impossible == Chance.IMPOSSIBLE)
        assert(certain == Chance.CERTAIN)
        assert(impossible != certain)
        assert(impossible.hashCode() != certain.hashCode())
    }

    @Test
    fun `not operator syntax variants`() {
        val p = Chance(0.3)

        assert(!p == Chance(0.7))
        assert(p.not() == Chance(0.7))
    }

    @Test
    fun `and operator`() {
        val p1 = Chance(0.4)
        val p2 = Chance(0.5)
        val p3 = Chance(0.0)

        assert(p1 and p2 == Chance(0.2))
        assert(p1 and p3 == Chance(0.0))
        assert(p3 and p3 == Chance(0.0))
    }

    @Test
    fun `or operator via DeMorgan`() {
        val p1 = Chance(0.4)
        val p2 = Chance(0.5)

        val orResult = p1 or p2
        val demorganResult = !(!p1 and !p2)

        assert(orResult == demorganResult)
        assert(orResult == Chance(0.7))
    }

    @Test
    fun `construction validation`() {
        assertThrows<IllegalArgumentException> { Chance(-0.1) }
        assertThrows<IllegalArgumentException> { Chance(1.1) }
        Chance(0.0)
        Chance(1.0)
    }

    @Test
    fun `IMPOSSIBLE and CERTAIN constants`() {
        assert(Chance.IMPOSSIBLE == Chance(0.0))
        assert(Chance.CERTAIN == Chance(1.0))
        assert(Chance.IMPOSSIBLE != Chance.CERTAIN)
        assert(!Chance.IMPOSSIBLE == Chance.CERTAIN)
        assert(!Chance.CERTAIN == Chance.IMPOSSIBLE)
    }

    @Test
    fun `edge case probabilities`() {
        val p1 = Chance(0.25)
        val p2 = Chance(0.75)
        val p3 = Chance(0.0)
        val p4 = Chance(1.0)

        assert((p1 and p2) == Chance(0.1875))
        assert((p1 or p2) == Chance(0.8125))
        assert((p1 or p3) == p1)
        assert((p1 and p4) == p1)
    }
}
