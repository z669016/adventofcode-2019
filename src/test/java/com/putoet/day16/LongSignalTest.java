package com.putoet.day16;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LongSignalTest {
    @Test
    void offset() {
        assertEquals(303673, offsetOf("03036732577212944063491565474664"));
        assertEquals(293510, offsetOf("02935109699940807407585447034323"));
        assertEquals(308177, offsetOf("03081770884921959731165446850517"));
    }

    private int offsetOf(String data) {
        return LongSignal.offset(FasterSignal.asIntArray(data));
    }

    @Test
    void samples() {
        sampleTest("03036732577212944063491565474664", "84462026");
        sampleTest("02935109699940807407585447034323", "78725270");
        sampleTest("03081770884921959731165446850517", "53553731");
    }

    void sampleTest(String input, String signature) {
        final LongSignal signal = new LongSignal(FasterSignal.asIntArray(input));

        assertEquals(signature, signal.fft(100).signature());
    }
}