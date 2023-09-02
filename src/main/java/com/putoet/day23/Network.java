package com.putoet.day23;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Network implements Runnable {
    public static final int SIZE = 50;

    private final NetworkInterfaceController[] nics;
    private final SynchronizedInputDevice[] inputs;
    private final SynchronizedOutputDevice[] outputs;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private Thread[] workerNics;
    private AddressedPacket invalid;
    private NotAlwaysTransmitting nat;

    public Network(List<Long> intCode) {
        nics = new NetworkInterfaceController[SIZE];
        inputs = new SynchronizedInputDevice[SIZE];
        outputs = new SynchronizedOutputDevice[SIZE];

        for (int address = 0; address < SIZE; address++) {
            inputs[address] = new SynchronizedInputDevice(address);
            inputs[address].offer(address);
            outputs[address] = new SynchronizedOutputDevice(address);
            nics[address] = new NetworkInterfaceController(address, intCode, inputs[address], outputs[address]);
        }
    }

    public void nat(NotAlwaysTransmitting nat) {
        this.nat = nat;
    }

    public SynchronizedInputDevice input(int address) {
        assert address >= 0 && address < SIZE;

        return inputs[address];
    }

    public void start() {
        Thread worker = new Thread(this);
        workerNics = new Thread[SIZE];

        for (NetworkInterfaceController nic : nics) {
            workerNics[nic.address()] = new Thread(nic);
            workerNics[nic.address()].start();
        }

        worker.start();
    }

    public void stop() {
        running.set(false);
    }

    @Override
    public void run() {
        running.set(true);

        while (running.get()) {
            for (SynchronizedOutputDevice output : outputs) {
                final Optional<AddressedPacket> packet = output.poll();
                packet.ifPresent(addressedPacket -> {
                    if (addressedPacket.address() >= 0 && addressedPacket.address() < SIZE)
                        inputs[addressedPacket.address()].offer(addressedPacket);
                    else {
                        if (invalid == null) {
                            lock.writeLock().lock();
                            invalid = addressedPacket;
                            lock.writeLock().unlock();
                        }
                        if (nat != null)
                            nat.offer(addressedPacket);
                    }
                });
            }
        }

        for (Thread t : workerNics)
            t.interrupt();
    }

    public Optional<AddressedPacket> invalid() {
        lock.readLock().lock();
        final Optional<AddressedPacket> result = Optional.ofNullable(invalid);
        lock.readLock().unlock();

        return result;
    }

    public boolean idle() {
        return Arrays.stream(inputs).allMatch(in -> in.size() == 0);
    }
}
