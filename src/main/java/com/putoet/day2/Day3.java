package com.putoet.day2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Day3 {
    public static void main(String[] args) throws IOException {
        final List<Integer> list = List.of(1,12,2,3,1,1,2,3,1,3,4,3,1,5,0,3,2,10,1,19,1,19,6,23,2,13,23,27,1,27,13,31,
                1,9,31,35,1,35,9,39,1,39,5,43,2,6,43,47,1,47,6,51,2,51,9,55,2,55,13,59,1,59,6,63,1,10,63,67,2,67,9,71,
                2,6,71,75,1,75,5,79,2,79,10,83,1,5,83,87,2,9,87,91,1,5,91,95,2,13,95,99,1,99,10,103,1,103,2,107,1,107,
                6,0,99,2,14,0,0);

        // Part one
        Memory memory = Memory.of(list);
        Processor processor = new Processor(memory);
        processor.run();
        System.out.println(processor.memory().dump());

        // Brute force solution on part two
        final Address nounAddress = Address.of(1);
        final Address verbAddress = Address.of(2);
        for (int noun = 0; noun < list.size(); noun++) {
            for (int verb = 0; verb < list.size(); verb++) {
                memory = Memory.of(list);
                memory.poke(nounAddress, noun);
                memory.poke(verbAddress, verb);

                processor = new Processor(memory);
                processor.run();

                int value = processor.memory().peek(Address.START_ADDRESS);
                if (value == 19690720) {
                    System.out.println("noun=" + noun + " and verb=" + verb);
                    System.out.println("100 * noun + verb = " + (100 * noun + verb));
                    break;
                }
            }
        }
    }
}

enum Operation {
    SUM(1, 4),
    PRODUCT(2, 4),
    EXIT(99, 1);

    private final int opcode;
    private final int size;

    Operation(int opcode, int size) {
        this.opcode = opcode;
        this.size = size;
    }

    static Operation of(int opcode) {
        switch (opcode) {
            case 1: return SUM;
            case 2: return PRODUCT;
            case 99: return EXIT;
            default: throw new IllegalArgumentException("Illegal opcode " + opcode);
        }
    }

    BiFunction<Integer, Integer, Integer> biFunction() {
        switch (this) {
            case SUM: return (o1, o2) -> o1 + o2;
            case PRODUCT: return (o1, o2) -> o1 * o2;
            default: throw new IllegalArgumentException("NOP");
        }
    }

    int size() {
        return this.size;
    }
}

class Address {
    static final Address START_ADDRESS = Address.of(0);

    private final int address;

    private Address(int address) {
        if (address < 0) throw new IllegalArgumentException("Address cannot be negative " + address);
        this.address = address;
    }

    static Address of(int address) {
        return new Address(address);
    }

    Address increase(int instructionSize) {
        return new Address(address + instructionSize);
    }

    int toInt() {
        return address;
    }
}

class Memory {
    private final List<Integer> memory;

    private Memory(List<Integer> memory) {
        this.memory = new ArrayList<Integer>();
        this.memory.addAll(memory);
    }

    static Memory of(List<Integer> memory) {
        if ((memory == null) || memory.size() == 0) throw new IllegalArgumentException("No memory");
        return new Memory(memory);
    }

    Integer peek(Address address) {
        checkAddress(address);
        return memory.get(address.toInt());
    }

    void poke(Address address, Integer value) {
        if (value == null) throw new IllegalArgumentException("Invalid memory write (null)");

        checkAddress(address);
        memory.set(address.toInt(), value);
    }

    int size() {
        return memory.size();
    }

    public String dump() {
        return memory.stream().map(i -> String.valueOf(i)).collect(Collectors.joining(","));
    }

    private void checkAddress(Address address) {
        if (address.toInt() > memory.size() - 1) throw new IllegalArgumentException("Invalid memory address " + address.toInt());
    }
}

class Processor {
    private final Memory memory;
    private Address ip = Address.START_ADDRESS;

    Processor(Memory memory) {
        this.memory = memory;
    }

    void run() {
        Operation operation = Operation.of(memory.peek(ip));
        while (operation != Operation.EXIT) {
            execute(operation);

            ip = ip.increase(operation.size());
            operation = Operation.of(memory.peek(ip));
        }
    }

    private void execute(Operation operation) {
        if (operation.size() == 4) {
            final Address operant1Address = Address.of(memory.peek(ip.increase(1)));
            final int operant1 = memory.peek(operant1Address);

            final Address operant2Address = Address.of(memory.peek(ip.increase(2)));
            final int operant2 = memory.peek(operant2Address);

            final Address destination = Address.of(memory.peek(ip.increase(3)));

            memory.poke(destination, operation.biFunction().apply(operant1, operant2));
            return;
        }
    }

    Memory memory() {
        return memory;
    }
}




