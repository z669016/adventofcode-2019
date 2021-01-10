package com.putoet.day7;

import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;

public interface AmplifierArray extends Runnable {
    @Override
    void run();

    void input(long input);

    OptionalLong output(int timeout, TimeUnit timeUnit);
}
