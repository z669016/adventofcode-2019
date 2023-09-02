package com.putoet.day16;

import java.util.Arrays;

record LongSignal(int[] data) {
    private static final int EXTENSION = 10_000;

    public static LongSignal ofRaw(int[] data) {
        final var offset = offset(data);

        var length = data.length * EXTENSION - offset;
        final var newData = new int[length];
        while (length > 0) {
            length -= data.length;
            final var fromStart = length > 0 ? 0 : Math.abs(length);
            final var toStart = Math.max(length, 0);
            final var toCopy = fromStart == 0 ? data.length : data.length + length;
            System.arraycopy(data, fromStart, newData, toStart, toCopy);
        }

        return new LongSignal(newData);
    }

    public static int offset(int[] data) {
        assert data.length >= 7;
        var offset = 0;

        for (int i = 0; i < 7; i++)
            offset = offset * 10 + data[i];

        return offset;
    }

    public LongSignal fft(int count) {
        final var newData = Arrays.copyOf(data, data.length);

        while (count-- > 0) {
            var sum = 0;
            for (var i = newData.length - 1; i >= 0; i--) {
                sum += newData[i];
                newData[i] = sum % 10;
            }
        }
        return new LongSignal(newData);
    }

    public String signature() {
        return Arrays.stream(data).limit(8).mapToObj(Integer::toString).reduce("", String::concat);
    }

    public int size() {
        return data.length;
    }
}
