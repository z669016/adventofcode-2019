package com.putoet.day16;

public class FasterSignal {
    // the pattern used will be 0, 1, 0, -1

    protected final int[] data;

    public FasterSignal(int[] data) {
        assert data != null;

        this.data = data;
    }

    private static int applyPattern(int nth, int[] data) {
        int sign = 1;
        int sum = 0;

        for (int i = nth - 1; i < data.length; i += (nth * 2)) {
            int sub = 0;
            for (int offset = 0; offset < nth && i + offset < data.length; offset++) {
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

    public static int[] asIntArray(String input) {
        return input.chars().map(i -> i - '0').toArray();
    }

    public FasterSignal fft(int count) {
        final int[] newData = new int[data.length];
        System.arraycopy(data, 0, newData, 0, data.length);

        while (count > 0) {
            for (int i = 1; i <= newData.length; i++)
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
        final StringBuilder sb = new StringBuilder();
        for (int i : data)
            sb.append(i);

        return sb.toString();
    }
}
