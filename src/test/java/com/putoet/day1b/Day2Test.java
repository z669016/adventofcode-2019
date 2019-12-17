package com.putoet.day1b;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day2Test
{
    @Test
    public void testCalculateFuelForMass() {
        assertEquals("2", new Mass("12").requiredFuel().toString());
        assertEquals("2", new Mass("14").requiredFuel().toString());
        assertEquals("966", new Mass("1969").requiredFuel().toString());
        assertEquals("50346", new Mass("100756").requiredFuel().toString());
    }

    @Test
    public void testAdd() {
        assertEquals ("5", new Fuel(2).add(new Fuel(3)).toString());
    }
}
