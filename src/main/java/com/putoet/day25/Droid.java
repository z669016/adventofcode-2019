package com.putoet.day25;

import com.putoet.intcode.ExpandableMemory;
import com.putoet.intcode.IntCodeComputer;
import com.putoet.intcode.IntCodeDevice;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

class Droid implements Runnable {
    private final IntCodeDevice device;
    private final SyncCommandInputDevice input = new SyncCommandInputDevice();
    private final SyncCommandOutputDevice output = new SyncCommandOutputDevice();
    private final AtomicBoolean running = new AtomicBoolean(true);
    private Thread worker;

    public Droid(List<Long> intCode) {
        final var memory = new ExpandableMemory(intCode);
        device = IntCodeComputer.builder().memory(memory).input(input).output(output).resumable().build();
    }

    public void start() {
        if (worker == null) {
            worker = new Thread(this);
            worker.start();
        }

        delay();
    }

    public void delay() {
        try {
            Thread.sleep(30);
        } catch (InterruptedException ignored) {
        }
    }

    public void stop() {
        if (worker != null) {
            running.set(false);
        }
    }

    @Override
    public void run() {
        while (running.get()) {
            device.run();
            delay();
        }
    }

    public void offer(String command) {
        input.offer(command);
    }

    public List<String> poll() {
        return output.poll();
    }
}
