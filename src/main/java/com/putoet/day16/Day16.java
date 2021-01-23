package com.putoet.day16;

import com.putoet.resources.ResourceLines;

public class Day16 {
    public static void main(String[] args) {
        final String input = ResourceLines.line("/day16.txt");
        final int[] data = FasterSignal.asIntArray(input);
        part1(data);
        part2(data);
    }

    private static void part1(int[] data) {
        final FasterSignal signal = new FasterSignal(data).fft(100);
        System.out.println("Signal size is " + signal.size());
        System.out.println("First 8 characters after 100 transformations are " + signal.signature());
    }

    private static void part2(int[] data) {
        final LongSignal signal = new LongSignal(data).fft(100);

        System.out.println("Offset for the long signal is " + LongSignal.offset(data));
        System.out.println("First 8 characters after 100 transformations are " + signal.signature());
    }
}
