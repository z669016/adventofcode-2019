package com.putoet.day16;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FasterSignalTest {

    @Test
    void fft() {
        final var signal = new FasterSignal(FasterSignal.asIntArray("12345678"));

        assertEquals("48226158", signal.fft(1).toString());
        assertEquals("34040438", signal.fft(2).toString());
        assertEquals("03415518", signal.fft(3).toString());
        assertEquals("01029498", signal.fft(4).toString());
    }
}