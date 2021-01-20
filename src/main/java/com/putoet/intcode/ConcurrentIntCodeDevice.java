package com.putoet.intcode;

import java.util.concurrent.CountDownLatch;

public interface ConcurrentIntCodeDevice extends IntCodeDevice {
    CountDownLatch latch();
}
