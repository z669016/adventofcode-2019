package com.putoet.intcode;

public class IntCodeDevice implements Runnable {
    private final Memory memory;
    private Address ip = Address.START;

    public IntCodeDevice(Memory memory) {
        this.memory = memory;
    }

    @Override
    public void run() {
        Instruction instruction = Interpreter.interpret(ip, memory);
        while (instruction.opcode() != Instruction.EXIT) {
            instruction.run();

            ip = ip.increase(instruction.size());
            instruction = Interpreter.interpret(ip, memory);
        }
    }
}
