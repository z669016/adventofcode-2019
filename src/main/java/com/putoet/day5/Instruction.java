package com.putoet.day5;

public abstract class Instruction {
    protected final Operation operation;
    protected final InstructionMode[] modes;

    protected Instruction(Operation operation) {
        this.operation = operation;
        this.modes = new InstructionMode[]{};
    }

    protected Instruction(Operation operation, InstructionMode... instructionModes) {
        this.operation = operation;
        this.modes = instructionModes;
    }

    public int size() {
        return operation.size();
    }

    public Operation operation() {
        return operation;
    }

    public abstract boolean execute(Memory memory, InputDevice inputDevice, OutputDevice outputDevice, Integer operants[]);

    public void report(int value, Integer operants[]) {
        switch (operants.length) {
            case 0:
                System.out.println(operation());
                break;

            case 1:
                System.out.println(operation() + " " + value + " <-- " + operants[0]);
                break;

            case 3:
                System.out.println(operation() + " " +
                        (modes[0] == InstructionMode.POSITION_MODE ? " (<-- " + operants[0] + ")" : operants[0]) +
                        " and "  +
                        (modes[1] == InstructionMode.POSITION_MODE ? " (<-- " + operants[1] + ")" : operants[1]) +
                        " --> " + operants[2]);
                break;
        }
    }
}

enum InstructionMode {
    POSITION_MODE,
    IMMEDIATE_MODE;

    public static InstructionMode of(int mode) {
        switch (mode) {
            case 0:
                return POSITION_MODE;
            case 1:
                return IMMEDIATE_MODE;
            default:
                throw new IllegalArgumentException("Invalid mode '" + mode + "'");
        }
    }

    public Integer peek(Memory memory, int operant) {
        return this == IMMEDIATE_MODE ? operant : memory.peek( Address.of(operant));
    }
}

class InputInstruction extends Instruction {
    protected InputInstruction(Operation operation) {
        super(operation);
    }

    @Override
    public boolean execute(Memory memory, InputDevice inputDevice, OutputDevice outputDevice, Integer operants[]) {
        Integer value = inputDevice.get();
        memory.poke(Address.of(operants[0]), value);
        report(value, operants);

        return true;
    }
}

class OutputInstruction extends Instruction {
    protected OutputInstruction(Operation operation) {
        super(operation);
    }

    @Override
    public boolean execute(Memory memory, InputDevice inputDevice, OutputDevice outputDevice, Integer operants[]) {
        Integer value = memory.peek(Address.of(operants[0]));
        outputDevice.put(value);
        report(value, operants);
        return true;
    }
}

class SumInstruction extends Instruction {
    protected SumInstruction(Operation operation, InstructionMode[] instructionModes) {
        super(operation, instructionModes);
    }

    @Override
    public boolean execute(Memory memory, InputDevice inputDevice, OutputDevice outputDevice, Integer operants[]) {
        final int operant1 = modes[0].peek(memory, operants[0]);
        final int operant2 = modes[1].peek(memory, operants[1]);
        final int value = operant1 + operant2;

        memory.poke(Address.of(operants[2]), value);
        report(value, operants);
        return true;
    }
}

class ProductInstruction extends Instruction {
    protected ProductInstruction(Operation operation, InstructionMode[] instructionModes) {
        super(operation, instructionModes);
    }

    @Override
    public boolean execute(Memory memory, InputDevice inputDevice, OutputDevice outputDevice, Integer operants[]) {
        final int operant1 = modes[0].peek(memory, operants[0]);
        final int operant2 = modes[1].peek(memory, operants[1]);
        final int value = operant1 * operant2;

        memory.poke(Address.of(operants[2]), value);
        report(value, operants);
        return true;
    }
}

class ExitInstruction extends Instruction {
    protected ExitInstruction(Operation operation) {
        super(operation);
    }

    @Override
    public boolean execute(Memory memory, InputDevice inputDevice, OutputDevice outputDevice, Integer operants[]) {
        return false;
    }
}