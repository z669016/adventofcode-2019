package com.putoet.day4;

import java.util.function.Consumer;

class Counter implements Consumer<Password> {
    private int counter = 0;

    @Override
    public void accept(Password password) {
        counter++;
    }

    public int counter() {
        return counter;
    }
}
