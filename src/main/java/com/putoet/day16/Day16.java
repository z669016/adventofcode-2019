package com.putoet.day16;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day16 {
    public static void main(String[] args) {
        final var data = FasterSignal.asIntArray(ResourceLines.line("/day16.txt"));
        Timer.run(() -> part1(data));
        Timer.run(() -> part2(data));
    }

    private static void part1(int[] data) {
        final var signal = new FasterSignal(data).fft(100);
        System.out.println("First 8 characters after 100 transformations are " + signal.signature());
    }

    private static void part2(int[] data) {
        final var signal = LongSignal.ofRaw(data).fft(100);
        System.out.println("First 8 characters after 100 transformations are " + signal.signature());
    }
}
