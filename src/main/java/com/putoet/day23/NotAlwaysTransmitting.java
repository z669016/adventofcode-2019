package com.putoet.day23;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class NotAlwaysTransmitting implements Runnable {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final Network network;

    private AddressedPacket last;
    private AddressedPacket offered;
    private AddressedPacket twice;

    public NotAlwaysTransmitting(Network network) {
        this.network = network;
    }

    public void start() {
        Thread worker = new Thread(this);
        worker.start();
    }

    public void stop() {
        running.set(false);
    }

    @Override
    public void run() {
        running.set(true);

        try {
            while (running.get()) {
                if (network.idle() && last().isPresent()) {
                    lock.writeLock().lock();
                    if (offered != null && twice == null) {
                        if (offered.y == last.y)
                            twice = offered;
                    }
                    offered = last;
                    network.input(0).offer(offered);
                    last = null;
                    lock.writeLock().unlock();
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException ignored) {}
    }

    public void offer(AddressedPacket packet) {
        lock.writeLock().lock();
        last = packet;
        lock.writeLock().unlock();
    }

    public Optional<AddressedPacket> last() {
        lock.readLock().lock();
        final Optional<AddressedPacket> result = Optional.ofNullable(last);
        lock.readLock().unlock();

        return result;
    }

    public Optional<AddressedPacket> twice() {
        lock.readLock().lock();
        final Optional<AddressedPacket> result = Optional.ofNullable(twice);
        lock.readLock().unlock();

        return result;
    }
}
