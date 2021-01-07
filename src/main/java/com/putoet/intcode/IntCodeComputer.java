package com.putoet.intcode;

import java.util.Queue;

public class IntCodeComputer implements IntCodeDevice {
    private final Queue<Long> input;
    private final Queue<Long> output;
    private final Memory memory;
    private Address ip = Address.START;

    public IntCodeComputer(Memory memory) {
        this.memory = memory;
        input = null;
        output = null;
    }

    public IntCodeComputer(Memory memory, Queue<Long> input, Queue<Long> output) {
        this.memory = memory;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        Instruction instruction = Interpreter.interpret(this);
        while (instruction.opcode().opcode() != Instruction.EXIT) {
            instruction.run();
            instruction = Interpreter.interpret(this);
        }
    }

    @Override
    public Address ip() { return ip; }

    @Override
    public void ip(long ip) {
        assert ip >= Integer.MIN_VALUE && ip <= Integer.MAX_VALUE;

        this.ip = new Address(ip);
    }

    @Override
    public void next(long offset) {
        assert offset >= Integer.MIN_VALUE && offset <= Integer.MAX_VALUE;

        ip = ip.increase((int) offset);
    }

    @Override
    public Queue<Long> input() { return input; }

    @Override
    public Queue<Long> output() { return output; }

    @Override
    public Memory memory() { return memory; }
}
