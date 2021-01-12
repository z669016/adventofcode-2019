package com.putoet.intcode;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntCodeInputOutputDevice implements InputDevice, OutputDevice {
    private final BlockingDeque<Long> queue = new LinkedBlockingDeque<>();

    @Override
    public OptionalLong poll() {
        final Long value = queue.poll();
        return value != null ? OptionalLong.of(value) : OptionalLong.empty();
    }

    @Override
    public OptionalLong poll(int timeout, TimeUnit timeUnit) {
        try {
            final Long value = queue.poll(timeout, timeUnit);
            return value != null ? OptionalLong.of(value) : OptionalLong.empty();
        } catch (InterruptedException ignored) {}

        return OptionalLong.empty();
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
