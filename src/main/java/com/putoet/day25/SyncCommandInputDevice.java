package com.putoet.day25;

import com.putoet.intcode.InputDevice;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SyncCommandInputDevice implements InputDevice {
    private final Lock lock = new ReentrantLock();
    private final Queue<Long> command = new LinkedList<>();

    @Override
    public OptionalLong poll() {
        lock.lock();
        final var result = command.isEmpty() ? OptionalLong.empty() : OptionalLong.of(command.poll());
        lock.unlock();

        return result;
    }

    @Override
    public OptionalLong poll(int timeout, TimeUnit timeUnit) {
        return poll();
    }

    @Override
    public int size() {
        return command.size();
    }

    public void offer(String command) {
        lock.lock();
        command.chars().forEach(i -> this.command.add((long) i));
        this.command.add(10L);
        lock.unlock();
    }
}
