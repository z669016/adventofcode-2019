package com.putoet.day1a;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Day1Test
{
    @Test
    public void testCalculateFuelForMass() {
        assertEquals("2", new Mass("12").requiredFuel().toString());
        assertEquals("2", new Mass("14").requiredFuel().toString());
        assertEquals("654", new Mass("1969").requiredFuel().toString());
        assertEquals("33583", new Mass("100756").requiredFuel().toString());
    }

    @Test
    public void testAdd() {
        assertEquals ("5", new Fuel(2).add(new Fuel(3)).toString());
    }
}
