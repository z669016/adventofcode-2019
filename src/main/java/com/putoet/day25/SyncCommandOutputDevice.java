package com.putoet.day25;

import com.putoet.intcode.OutputDevice;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SyncCommandOutputDevice implements OutputDevice {
    private final Lock lock = new ReentrantLock();
    private final Queue<Long> command = new LinkedList<>();
    private final AtomicLong lastUpdate = new AtomicLong(0);

    @Override
    public void offer(long value) {
        lock.lock();
        command.offer(value);
        lastUpdate.set(System.currentTimeMillis());
        lock.unlock();
    }

    @Override
    public int size() {
        lock.lock();
        final var size = command.size();
        lock.unlock();
        return size;
    }

    @Override
    public List<Long> asList() {
        lock.lock();
        final var list = new ArrayList<>(command);
        lock.unlock();
        return list;
    }

    public List<String> poll() {
        if (System.currentTimeMillis() - lastUpdate.get() < 10)
            return List.of();

        final var list = asList();
        var sb = new StringBuilder();
        final var result = new ArrayList<String>();
        for (var l : list) {
            if (l == 10L) {
                result.add(sb.toString());
                sb = new StringBuilder();
            } else {
                sb.append((char) l.intValue());
            }
        }
        if (!sb.isEmpty())
            result.add(sb.toString());

        return result;
    }
}
