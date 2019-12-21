package com.putoet.day5;

public abstract class Instruction {
    public static final int FIRST_OPERANT_INDEX = 0;
    public static final int SECOND_OPERANT_INDEX = FIRST_OPERANT_INDEX + 1;
    public static final int THIRD_OPERANT_INDEX = SECOND_OPERANT_INDEX + 1;

    private final Operation operation;

    protected Instruction(Operation operation) {
        this.operation = operation;
    }

    public int size() {
        return operation.size();
    }

    public Operation operation() {
        return operation;
    }

    public abstract Address execute(Address ip, Memory memory, InputDevice inputDevice, OutputDevice outputDevice, Integer operants[]);

    public String toString(Integer operants[]) {
        StringBuilder sb = new StringBuilder().append(operation());
        for (int idx = 0; idx < operants.length; idx++) {
            sb.append(idx == operants.length ? " --> " : " <-- ").append(operants[idx]);
        }

        return sb.toString();
    }

    public String toString(int value, Integer operants[]) {
        StringBuilder sb = new StringBuilder().append(toString(operants));
        sb.append(" (= ").append(value).append(")");

        return sb.toString();
    }
}

abstract class ModedInstruction extends Instruction {
    private final InstructionMode[] modes;

    protected ModedInstruction(Operation operation, InstructionMode... instructionModes) {
        super(operation);
        this.modes = instructionModes;
    }

    public InstructionMode instructionMode(int index) {
        if (modes.length == 0 || index > modes.length)
            throw new IllegalStateException("No instruction modes defined " + operation());

        return modes[index];
    }

    @Override
    public String toString(int value, Integer operants[]) {
        StringBuilder sb = new StringBuilder().append(operation());
        for (int idx = 0; idx < operants.length; idx++) {
            if (idx < operants.length - 1) {
                sb.append((modes[idx] == InstructionMode.POSITION_MODE ? " (<-- " + operants[idx] + ")" : " " + operants[idx]));
            } else {
                sb.append(" --> ").append(operants[idx]);
            }
        }
        sb.append(" (= ").append(value).append(")");

        return sb.toString();
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

class SumInstruction extends ModedInstruction {
    protected SumInstruction(Operation operation, InstructionMode[] instructionModes) {
        super(operation, instructionModes);
    }

    @Override
    public Address execute(Address ip, Memory memory, InputDevice inputDevice, OutputDevice outputDevice, Integer operants[]) {
        final int firstOperantValue = instructionMode(FIRST_OPERANT_INDEX).peek(memory, operants[FIRST_OPERANT_INDEX]);
        final int secondOperantValue = instructionMode(SECOND_OPERANT_INDEX).peek(memory, operants[SECOND_OPERANT_INDEX]);
        final int thirdOperantValue = operants[THIRD_OPERANT_INDEX];
        final Integer result = firstOperantValue + secondOperantValue;

        memory.poke(Address.of(thirdOperantValue), result);
        System.out.println(toString(result, operants));;

        return ip.increase(size());
    }
}

class ProductInstruction extends ModedInstruction {
    protected ProductInstruction(Operation operation, InstructionMode[] instructionModes) {
        super(operation, instructionModes);
    }

    @Override
    public Address execute(Address ip, Memory memory, InputDevice inputDevice, OutputDevice outputDevice, Integer operants[]) {
        final int firstOperantValue = instructionMode(FIRST_OPERANT_INDEX).peek(memory, operants[FIRST_OPERANT_INDEX]);
        final int secondOperantValue = instructionMode(SECOND_OPERANT_INDEX).peek(memory, operants[SECOND_OPERANT_INDEX]);
        final int thirdOperantValue = operants[THIRD_OPERANT_INDEX];
        final Integer result = firstOperantValue * secondOperantValue;

        memory.poke(Address.of(thirdOperantValue), result);
        System.out.println(toString(result, operants));;

        return ip.increase(size());
    }
}

class ExitInstruction extends Instruction {
    protected ExitInstruction(Operation operation) {
        super(operation);
    }

    @Override
    public Address execute(Address ip, Memory memory, InputDevice inputDevice, OutputDevice outputDevice, Integer operants[]) {
        return ip;
    }
}

class InputInstruction extends Instruction {
    protected InputInstruction(Operation operation) {
        super(operation);
    }

    @Override
    public Address execute(Address ip, Memory memory, InputDevice inputDevice, OutputDevice outputDevice, Integer operants[]) {
        final int firstOperantValue = operants[FIRST_OPERANT_INDEX];
        Integer result = inputDevice.get();

        memory.poke(Address.of(firstOperantValue), result);
        System.out.println(toString(result, operants));;

        return ip.increase(size());
    }
}

class OutputInstruction extends ModedInstruction {
    protected OutputInstruction(Operation operation, InstructionMode[] instructionModes) {
        super(operation, instructionModes);
    }

    @Override
    public Address execute(Address ip, Memory memory, InputDevice inputDevice, OutputDevice outputDevice, Integer operants[]) {
        final Integer result = instructionMode(FIRST_OPERANT_INDEX).peek(memory, operants[FIRST_OPERANT_INDEX]);

        outputDevice.put(result);
        System.out.println(toString(result, operants));;

        return ip.increase(size());
    }
}

class JumpIfTrueInstruction extends ModedInstruction {
    protected JumpIfTrueInstruction(Operation operation, InstructionMode[] instructionModes) {
        super(operation, instructionModes);
    }

    @Override
    public Address execute(Address ip, Memory memory, InputDevice inputDevice, OutputDevice outputDevice, Integer operants[]) {
        final int firstOperantValue = instructionMode(FIRST_OPERANT_INDEX).peek(memory, operants[FIRST_OPERANT_INDEX]);
        final int secondOperantValue = instructionMode(SECOND_OPERANT_INDEX).peek(memory, operants[SECOND_OPERANT_INDEX]);

        System.out.println(toString(operants));;

        return (firstOperantValue == 0 ? ip.increase(size()) : Address.of(secondOperantValue));
    }
}

class JumpIfFalseInstruction extends ModedInstruction {
    protected JumpIfFalseInstruction(Operation operation, InstructionMode[] instructionModes) {
        super(operation, instructionModes);
    }

    @Override
    public Address execute(Address ip, Memory memory, InputDevice inputDevice, OutputDevice outputDevice, Integer operants[]) {
        final int firstOperantValue = instructionMode(FIRST_OPERANT_INDEX).peek(memory, operants[FIRST_OPERANT_INDEX]);
        final int secondOperantValue = instructionMode(SECOND_OPERANT_INDEX).peek(memory, operants[SECOND_OPERANT_INDEX]);

        System.out.println(toString(operants));;

        return (firstOperantValue != 0 ? ip.increase(size()) : Address.of(secondOperantValue));
    }
}

class LessThanInstruction extends ModedInstruction {
    protected LessThanInstruction(Operation operation, InstructionMode[] instructionModes) {
        super(operation, instructionModes);
    }

    @Override
    public Address execute(Address ip, Memory memory, InputDevice inputDevice, OutputDevice outputDevice, Integer operants[]) {
        final int firstOperantValue = instructionMode(FIRST_OPERANT_INDEX).peek(memory, operants[FIRST_OPERANT_INDEX]);
        final int secondOperantValue = instructionMode(SECOND_OPERANT_INDEX).peek(memory, operants[SECOND_OPERANT_INDEX]);
        final int thirdOperantValue = operants[THIRD_OPERANT_INDEX];
        final Integer result = firstOperantValue < secondOperantValue ? 1 : 0;

        memory.poke(Address.of(thirdOperantValue), result);
        System.out.println(toString(result, operants));;

        return ip.increase(size());
    }
}

class EqualInstruction extends ModedInstruction {
    protected EqualInstruction(Operation operation, InstructionMode[] instructionModes) {
        super(operation, instructionModes);
    }

    @Override
    public Address execute(Address ip, Memory memory, InputDevice inputDevice, OutputDevice outputDevice, Integer operants[]) {
        final int firstOperantValue = instructionMode(FIRST_OPERANT_INDEX).peek(memory, operants[FIRST_OPERANT_INDEX]);
        final int secondOperantValue = instructionMode(SECOND_OPERANT_INDEX).peek(memory, operants[SECOND_OPERANT_INDEX]);
        final int thirdOperantValue = operants[THIRD_OPERANT_INDEX];
        final Integer result = firstOperantValue == secondOperantValue ? 1 : 0;

        memory.poke(Address.of(thirdOperantValue), result);
        System.out.println(toString(result, operants));;

        return ip.increase(size());
    }
}