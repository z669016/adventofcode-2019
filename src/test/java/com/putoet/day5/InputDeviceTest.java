package com.putoet.day5;

import org.junit.Test;

import java.util.List;

import static com.putoet.day7.ExceptionTester.ae;
import static com.putoet.day7.ExceptionTester.is;
import static org.junit.Assert.*;

public class InputDeviceTest {
    @Test
    public void testInputDevice() {
        AssertionError ae = ae(() -> new InputDevice(null));
        assertNotNull(ae);

        IllegalStateException is = is (() -> {
            final InputDevice id = new InputDevice(List.of());
            return id.get().get();
        });
        assertNotNull(is);
        assertEquals("No input available", is.getMessage());

        final InputDevice id = new InputDevice(List.of(1, 2, 3));
        assertEquals("[1, 2, 3]", id.toString());

        assertEquals(Integer.valueOf(1), id.get().get());
        assertEquals(Integer.valueOf(2), id.get().get());
        assertEquals(Integer.valueOf(3), id.get().get());

        is = is(() -> id.get());
        assertEquals("No input available", is.getMessage());

        assertEquals("[]", id.toString());
    }

}
