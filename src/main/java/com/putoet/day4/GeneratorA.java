package com.putoet.day4;

import java.util.Iterator;
import java.util.function.Consumer;

class GeneratorA implements Iterator<Password> {
    protected int i1 = 1;
    protected int i2 = i1;
    protected int i3 = i2;
    protected int i4 = i3;
    protected int i5 = i4;
    protected int i6 = i5;

    private final int min;
    private final int max;

    public GeneratorA() {
        min = 111111;
        max = 999999;
    }

    public GeneratorA(int initialValue) {
        i1 = i2 = i3 = i4 = i5 = i6 = initialValue;
        min = (((((i1 * 10 + i2) * 10 + i3) * 10 + i4) * 10 + i5) * 10 + i6);
        max = 999999;
    }

    public GeneratorA(int min, int max) {
        assert (min >= 111111);
        assert (max >= min && max <= 999999);

        final String start = String.format("%06d", min);
        i1 = start.charAt(0) - '0';
        i2 = start.charAt(1) - '0';
        i3 = start.charAt(2) - '0';
        i4 = start.charAt(3) - '0';
        i5 = start.charAt(4) - '0';
        i6 = start.charAt(5) - '0';

        this.min = min;
        this.max = max;
        if (!isValid()) {
            increase();
        }
    }

    @Override
    public boolean hasNext() {
        return (i1 != 10) && (Integer.parseInt(combine()) <= max);
    }

    @Override
    public Password next() {
        final String result = combine();
        increase();
        return new Password(result);
    }

    private void increase() {
        do {
            increaseByOne();
        } while (!isValid() && hasNext());
    }

    private void increaseByOne() {
        i6++;
        if (i6 == 10) {
            i5++;
            if (i5 == 10) {
                i4++;
                if (i4 == 10) {
                    i3++;
                    if (i3 == 10) {
                        i2++;
                        if (i2 == 10) {
                            i1++;
                            if (i1 == 10) return;
                            i2 = i1;
                        }
                        i3 = i2;
                    }
                    i4 = i3;
                }
                i5 = i4;
            }
            i6 = i5;
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forEachRemaining(Consumer<? super Password> action) {
        while (hasNext()) {
            action.accept(next());
        }
    }

    private String combine() {
        return "" + i1 + i2 + i3 + i4 + i5 + i6;
    }

    private boolean isValid() {
        return areGreaterEqual() && hasDuplicate();
    }

    private boolean areGreaterEqual() {
        return i2 >= i1 &&
                i3 >= i2 &&
                i4 >= i3 &&
                i5 >= i4 &&
                i6 >= i5;
    }

    protected boolean hasDuplicate() {
        return i1 == i2 ||
                i2 == i3 ||
                i3 == i4 ||
                i4 == i5 ||
                i5 == i6;
    }
}
