package com.putoet.day16;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class PatternTest {
    @Test
    public void testIterator() {
        final Pattern pattern = new PatternImpl(List.of(1, 2, 3)).nextPhasePattern();
        final Iterator<Integer> iter = pattern.iterator();

        assertTrue(iter.hasNext());
        assertEquals(1, iter.next().intValue());
        assertTrue(iter.hasNext());
        assertEquals(2, iter.next().intValue());
        assertTrue(iter.hasNext());
        assertEquals(2, iter.next().intValue());
        assertTrue(iter.hasNext());
        assertEquals(3, iter.next().intValue());
        assertTrue(iter.hasNext());
        assertEquals(3, iter.next().intValue());
        assertTrue(iter.hasNext());
        assertEquals(1, iter.next().intValue());
        assertTrue(iter.hasNext());
        assertEquals(1, iter.next().intValue());
        assertTrue(iter.hasNext());
        assertEquals(2, iter.next().intValue());
        assertTrue(iter.hasNext());
    }
}