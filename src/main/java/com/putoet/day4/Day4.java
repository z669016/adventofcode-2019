package com.putoet.day4;

import java.util.Iterator;
import java.util.function.Consumer;

public class Day4 {

    public static final int MIN = 152085;
    public static final int MAX = 670283;

    public static void main(String[] args) {
        GeneratorA generatorA = new GeneratorA(MIN, MAX);
        GeneratorB generatorB = new GeneratorB(MIN, MAX);

        Counter counterA = new Counter();
        generatorA.forEachRemaining(counterA);
        System.out.println("Counter for puzzle A is " + counterA.counter());

        Counter counterB = new Counter();
        generatorB.forEachRemaining(counterB);
        System.out.println("Counter for puzzle B is " + counterB.counter());
    }
}

class Counter implements Consumer<String> {
    private int counter = 0;

    @Override
    public void accept(String s) {
        counter++;
    }

    public int counter() {
        return counter;
    }
}

class GeneratorA implements Iterator<String> {
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
        assert( min >= 111111);
        assert( max >= min && max <= 999999);

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
        return (i1 != 10) && (Integer.valueOf(combine()) <= max);
    }

    @Override
    public String next() {
        final String result = combine();
        increase();
        return result;
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
    public void forEachRemaining(Consumer<? super String> action) {
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

class GeneratorB extends GeneratorA {
    public GeneratorB() {
        super();
    }

    public GeneratorB(int initialValue) {
        super(initialValue);
    }

    public GeneratorB(int min, int max) {
        super(min, max);
    }

    protected boolean hasDuplicate() {
        return (i1 == i2 && i2 != i3) ||
                (i2 == i3 && i1 != i2 && i3 != i4) ||
                (i3 == i4 && i2 != i3 && i4 != i5) ||
                (i4 == i5 && i3 != i4 && i5 != i6) ||
                (i5 == i6 && i4 != i5);
    }

}