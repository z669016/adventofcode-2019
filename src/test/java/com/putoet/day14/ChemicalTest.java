package com.putoet.day14;

import org.junit.Test;

import static com.putoet.day7.ExceptionTester.ia;
import static org.junit.Assert.*;

public class ChemicalTest {
    @Test
    public void testChemical() {
        assertEquals("A", new Chemical("A").name());
        assertEquals("XYZ", new Chemical("xyz").name());
    }

    @Test
    public void testNoName() {
        IllegalArgumentException ia = ia(() -> new Chemical(null));
        assertEquals("No name", ia.getMessage());
    }

    @Test
    public void testInvalidName() {
        IllegalArgumentException ia = ia(() -> new Chemical("a1b"));
        assertEquals("Invalid chemical name A1B", ia.getMessage());
    }
}