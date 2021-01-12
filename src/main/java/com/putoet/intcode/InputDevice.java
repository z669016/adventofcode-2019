package com.putoet.intcode;

import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;

public interface InputDevice {
    OptionalLong poll();
    OptionalLong poll(int timeout, TimeUnit timeUnit);
    int size();
}
