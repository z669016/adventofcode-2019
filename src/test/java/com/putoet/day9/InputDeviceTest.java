package com.putoet.day9;

import org.junit.Test;

import java.util.List;

import static com.putoet.day7.ExceptionTester.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InputDeviceTest {
    @Test
    public void testInputDevice() {
        IllegalArgumentException ia = ia(() -> new InputDevice(null));
        assertNotNull(ia);

        IllegalStateException is = is (() -> {
            final IInputDevice id = new InputDevice(List.of());
            return id.get().get();
        });
        assertNotNull(is);
        assertEquals("No input available", is.getMessage());

        final List<Long> inputList = List.of(1L, 2L, 3L);
        final InputDevice id = new InputDevice(inputList);
        assertEquals(inputList, id.asList());

        assertEquals(Long.valueOf(1), id.get().get());
        assertEquals(Long.valueOf(2), id.get().get());
        assertEquals(Long.valueOf(3), id.get().get());

        is = is(id::get);
        assertEquals("No input available", is.getMessage());

        assertEquals(List.of(), id.asList());
    }
}
