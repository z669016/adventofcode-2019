package com.putoet.day2;

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
        }
    }

    Memory memory() {
        return memory;
    }
}
