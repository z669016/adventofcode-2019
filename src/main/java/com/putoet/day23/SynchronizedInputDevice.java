package com.putoet.day23;

import com.putoet.intcode.InputDevice;

import java.util.LinkedList;
import java.util.OptionalLong;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedInputDevice implements InputDevice {
    public static final long EMPTY_VALUE = -1L;

    private final Lock lock = new ReentrantLock();
    private final Queue<Long> queue = new LinkedList<>();

    private final int address;

    public SynchronizedInputDevice(int address) {
        this.address = address;
    }

    public int address() {
        return address;
    }

    @Override
    public OptionalLong poll() {
        lock.lock();
        final Long result = queue.poll();
        lock.unlock();
        return result == null ? OptionalLong.of(EMPTY_VALUE) : OptionalLong.of(result);
    }

    @Override
    public OptionalLong poll(int timeout, TimeUnit timeUnit) {
        return poll();
    }

    public void offer(Packet packet) {
        lock.lock();
        queue.offer(packet.x);
        queue.offer(packet.y);
        lock.unlock();
    }

    public void offer(long address) {
        lock.lock();
        queue.offer(address);
        lock.unlock();
    }

    @Override
    public int size() {
        lock.lock();
        final int size = queue.size();
        lock.unlock();
        return size;
    }

    @Override
    public String toString() {
        return "SYNC_IN(" + address + ")";
    }
}
