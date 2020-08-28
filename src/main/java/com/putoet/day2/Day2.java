package com.putoet.day2;

import com.putoet.resources.CSV;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Day2 {
    public static void main(String[] args) throws IOException {
        final List<Integer> intCode = CSV.list("/day2.txt");

        // Part one
        Memory memory = Memory.of(intCode);
        memory.poke(Address.of(1), 12);
        memory.poke(Address.of(2), 2);

        Processor processor = new Processor(memory);
        processor.run();
        System.out.println("The valeua at position 0 after running the program is " + memory.peek(Address.of(0)));

        // Brute force solution on part two
        final Address nounAddress = Address.of(1);
        final Address verbAddress = Address.of(2);
        for (int noun = 0; noun < intCode.size(); noun++) {
            for (int verb = 0; verb < intCode.size(); verb++) {
                memory = Memory.of(intCode);
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
        return switch (opcode) {
            case 1 -> SUM;
            case 2 -> PRODUCT;
            case 99 -> EXIT;
            default -> throw new IllegalArgumentException("Illegal opcode " + opcode);
        };
    }

    BiFunction<Integer, Integer, Integer> biFunction() {
        return switch (this) {
            case SUM -> Integer::sum;
            case PRODUCT -> (o1, o2) -> o1 * o2;
            default -> throw new IllegalArgumentException("NOP");
        };
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
        return memory.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    private void checkAddress(Address address) {
        if (address.toInt() > memory.size() - 1)
            throw new IllegalArgumentException("Invalid memory address " + address.toInt());
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




