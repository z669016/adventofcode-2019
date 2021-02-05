package com.putoet.day23;

import com.putoet.intcode.*;

import java.util.List;

public class NetworkInterfaceController implements Runnable {
    private final int address;
    private final IntCodeDevice device;

    public NetworkInterfaceController(int address, List<Long> intCode, InputDevice input, OutputDevice output) {
        this.address = address;

        final Memory memory = new ExpandableMemory(intCode);
        device = IntCodeComputer
                .builder()
                .memory(memory)
                .input(input)
                .output(output)
                .build();
    }

    public int address() {
        return address;
    }

    @Override
    public void run() {
        device.run();
    }

    @Override
    public String toString() {
        return "NIC(" + address + ")";
    }
}
