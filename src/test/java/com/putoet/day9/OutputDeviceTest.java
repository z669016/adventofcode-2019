package com.putoet.day9;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputDeviceTest {
    @Test
    public void testOutputDevice() {
        final OutputDevice out = new OutputDevice();
        assertEquals(List.of(), out.asList());

        out.put(1L);
        out.put(2L);
        out.put(3L);
        assertEquals(List.of(1L, 2L, 3L), out.asList());
    }
}
