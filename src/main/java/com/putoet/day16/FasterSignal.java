package com.putoet.day16;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

record FasterSignal(int[] data) {
    private static int applyPattern(int nth, int[] data) {
        var sign = 1;
        var sum = 0;

        for (var i = nth - 1; i < data.length; i += (nth * 2)) {
            var sub = 0;
            for (var offset = 0; offset < nth && i + offset < data.length; offset++) {
                sub += data[i + offset];
            }

            if (sign == 1)
                sum += sub;
            else
                sum -= sub;

            sign = -1 * sign;
        }

        return Math.abs(sum % 10);
    }

    public static int[] asIntArray(@NotNull String input) {
        return input.chars().map(i -> i - '0').toArray();
    }

    public FasterSignal fft(int count) {
        final var newData = Arrays.copyOf(data, data.length);

        while (count > 0) {
            for (var i = 1; i <= newData.length; i++)
                newData[i - 1] = applyPattern(i, newData);

            count--;
        }
        return new FasterSignal(newData);

    }

    public String signature() {
        return toString().substring(0, 8);
    }

    public int size() {
        return data.length;
    }

    @Override
    public String toString() {
        return Arrays.stream(data).mapToObj(Integer::toString).reduce("", String::concat);
    }
}
