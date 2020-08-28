package com.putoet.day4;

import java.util.function.Consumer;

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
