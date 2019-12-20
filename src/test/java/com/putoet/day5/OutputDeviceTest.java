package com.putoet.day5;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OutputDeviceTest {
    @Test
    public void testOutputDevice() {
        final OutputDevice out = new OutputDevice();
        assertEquals("[]", out.toString());

        out.put(1);
        out.put(2);
        out.put(3);
        assertEquals("[1, 2, 3]", out.toString());
    }
}
