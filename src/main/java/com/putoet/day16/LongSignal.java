package com.putoet.day16;

public class LongSignal {
    private static final int EXTENSION = 10_000;

    private final int[] data;
    private final int offset;

    public LongSignal(int[] data) {
        this.offset = offset(data);

        int length = data.length * EXTENSION - offset;
        final int[] newData = new int[length];
        while (length > 0) {
            length -= data.length;
            final int fromStart = length > 0 ? 0 : Math.abs(length);
            final int toStart = Math.max(length, 0);
            final int toCopy = fromStart == 0 ? data.length : data.length + length;
            System.arraycopy(data, fromStart, newData, toStart, toCopy);
        }
        this.data = newData;
    }

    public LongSignal(int offset, int[] data) {
        this.data = data;
        this.offset = offset;
    }

    public static int offset(int[] data) {
        assert data != null && data.length >= 7;
        int offset = 0;

        for (int i = 0; i < 7; i++)
            offset = offset * 10 + data[i];

        return offset;
    }

    public LongSignal fft(int count) {
        final int[] newData = new int[data.length];
        System.arraycopy(data, 0, newData, 0, data.length);

        while (count-- > 0) {
            int sum = 0;
            for (int i = newData.length - 1; i >= 0; i--) {
                sum += newData[i];
                newData[i] = sum % 10;
            }
        }
        return new LongSignal(offset, newData);
    }

    public String signature() {
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 8; i++)
            sb.append(data[i]);

        return sb.toString();
    }

    public int size() {
        return data.length;
    }
}
