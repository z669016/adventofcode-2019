package com.putoet.day4;

class GeneratorB extends GeneratorA {

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
