package com.putoet.day1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MassTest
{
    @Test
    public void testCalculateFuelForMass() {
        assertEquals("2", new Mass("12").requiredFuel().toString());
        assertEquals("2", new Mass("14").requiredFuel().toString());
        assertEquals("966", new Mass("1969").requiredFuel().toString());
        assertEquals("50346", new Mass("100756").requiredFuel().toString());
    }
}
