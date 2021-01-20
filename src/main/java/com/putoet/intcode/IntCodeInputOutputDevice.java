package com.putoet.intcode;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class IntCodeInputOutputDevice implements InputDevice, OutputDevice {
    private final Queue<Long> queue = new LinkedList<>();

    @Override
    public OptionalLong poll() {
        final Long value = queue.poll();
        return value != null ? OptionalLong.of(value) : OptionalLong.empty();
    }

    @Override
    public OptionalLong poll(int timeout, TimeUnit timeUnit) {
        return poll();
    }

    @Override
    public void offer(long value) {
        queue.offer(value);
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public List<Long> asList() {
        return new ArrayList<>(queue);
    }
}
