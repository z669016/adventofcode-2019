package com.putoet.day5;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class InputDeviceTest {
    @Test
    public void testInputDevice() {
        try {
            new InputDevice(null);
            fail("Inputdevice cannot be created from an empty list");
        } catch (AssertionError as) {}

        try {
            final InputDevice id = new InputDevice(List.of());
            id.get();
        } catch (IllegalStateException exc) {
            assertEquals("No input available", exc.getMessage());
        }

        final InputDevice id = new InputDevice(List.of(1, 2, 3));
        assertEquals("[1, 2, 3]", id.toString());

        assertEquals(Integer.valueOf(1), id.get());
        assertEquals(Integer.valueOf(2), id.get());
        assertEquals(Integer.valueOf(3), id.get());

        try {
            id.get();
        } catch (IllegalStateException exc) {
            assertEquals("No input available", exc.getMessage());
        }

        assertEquals("[]", id.toString());
    }

}
