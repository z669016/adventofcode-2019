package com.putoet.day23;

import com.putoet.intcode.OutputDevice;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SynchronizedOutputDevice implements OutputDevice {
    private final Lock lock = new ReentrantLock();
    private final Queue<Long> queue = new LinkedList<>();

    private final int address;

    public SynchronizedOutputDevice(int address) {
        this.address = address;
    }

    public int address() {
        return address;
    }

    public Optional<AddressedPacket> poll() {
        if (size() > 2) {
            lock.lock();
            final var address = queue.poll();
            final var x = queue.poll();
            final var y = queue.poll();
            lock.unlock();

            if (address == null || x == null || y == null)
                throw new IllegalStateException("Missing value on queue (" + address + ", " + x + ", " + y + ")");

            return Optional.of(new AddressedPacket(address, x, y));
        }

        return Optional.empty();
    }

    @Override
    public void offer(long value) {
        lock.lock();
        queue.offer(value);
        lock.unlock();
    }

    @Override
    public int size() {
        lock.lock();
        final var size = queue.size();
        lock.unlock();
        return size;
    }

    @Override
    public List<Long> asList() {
        lock.lock();
        final var list = new ArrayList<>(queue);
        lock.unlock();
        return list;
    }

    @Override
    public String toString() {
        return "SYNC_OUT(" + address + ")";
    }
}
