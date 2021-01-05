package com.putoet.day5;

import java.util.Optional;

public abstract class Instruction implements IInstruction {
    public static final int FIRST_OPERANT_INDEX = 0;
    public static final int SECOND_OPERANT_INDEX = FIRST_OPERANT_INDEX + 1;
    public static final int THIRD_OPERANT_INDEX = SECOND_OPERANT_INDEX + 1;

    private static Boolean logEnabled = false;

    public static void enableLog() {
        logEnabled = true;
    }

    public static void disableLog() {
        logEnabled = false;
    }

    private final Operation operation;

    protected Instruction(Operation operation) {
        this.operation = operation;
    }

    @Override
    public int size() {
        return operation.size();
    }

    @Override
    public Operation operation() {
        return operation;
    }

    public String toString(Integer[] operants) {
        StringBuilder sb = new StringBuilder().append(operation());
        for (int idx = 0; idx < operants.length; idx++) {
            sb.append(idx == (operants.length - 1) ? " --> " : " <-- ").append(operants[idx]);
        }

        return sb.toString();
    }

    public String toString(int value, Integer[] operants) {
        StringBuilder sb = new StringBuilder().append(toString(operants));
        sb.append(" (= ").append(value).append(")");

        return sb.toString();
    }

    public void log(String line) {
        if (logEnabled)
            System.out.println(line);
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
    public String toString(int value, Integer[] operants) {
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
        return this == IMMEDIATE_MODE ? operant : memory.peek(Address.of(operant));
    }
}

class SumInstruction extends ModedInstruction {
    protected SumInstruction(Operation operation, InstructionMode[] instructionModes) {
        super(operation, instructionModes);
    }

    @Override
    public Optional<Address> execute(Address ip, Memory memory, IInputDevice inputDevice, OutputDevice outputDevice, Integer[] operants) {
        final int firstOperantValue = instructionMode(FIRST_OPERANT_INDEX).peek(memory, operants[FIRST_OPERANT_INDEX]);
        final int secondOperantValue = instructionMode(SECOND_OPERANT_INDEX).peek(memory, operants[SECOND_OPERANT_INDEX]);
        final int thirdOperantValue = operants[THIRD_OPERANT_INDEX];
        final int result = firstOperantValue + secondOperantValue;

        memory.poke(Address.of(thirdOperantValue), result);
        log(toString(result, operants));

        return Optional.of(ip.increase(size()));
    }
}

class ProductInstruction extends ModedInstruction {
    protected ProductInstruction(Operation operation, InstructionMode[] instructionModes) {
        super(operation, instructionModes);
    }

    @Override
    public Optional<Address> execute(Address ip, Memory memory, IInputDevice inputDevice, OutputDevice outputDevice, Integer[] operants) {
        final int firstOperantValue = instructionMode(FIRST_OPERANT_INDEX).peek(memory, operants[FIRST_OPERANT_INDEX]);
        final int secondOperantValue = instructionMode(SECOND_OPERANT_INDEX).peek(memory, operants[SECOND_OPERANT_INDEX]);
        final int thirdOperantValue = operants[THIRD_OPERANT_INDEX];
        final int result = firstOperantValue * secondOperantValue;

        memory.poke(Address.of(thirdOperantValue), result);
        log(toString(result, operants));

        return Optional.of(ip.increase(size()));
    }
}

class ExitInstruction extends Instruction {
    protected ExitInstruction(Operation operation) {
        super(operation);
    }

    @Override
    public Optional<Address> execute(Address ip, Memory memory, IInputDevice inputDevice, OutputDevice outputDevice, Integer[] operants) {
        return Optional.of(ip);
    }
}

class InputInstruction extends Instruction {
    protected InputInstruction(Operation operation) {
        super(operation);
    }

    @Override
    public Optional<Address> execute(Address ip, Memory memory, IInputDevice inputDevice, OutputDevice outputDevice, Integer[] operants) {
        final int firstOperantValue = operants[FIRST_OPERANT_INDEX];
        Optional<Integer> result = inputDevice.get();
        if (result.isEmpty())
            return Optional.empty();

        memory.poke(Address.of(firstOperantValue), result.get());
        log(toString(result.get(), operants));

        return Optional.of(ip.increase(size()));
    }
}

class OutputInstruction extends ModedInstruction {
    protected OutputInstruction(Operation operation, InstructionMode[] instructionModes) {
        super(operation, instructionModes);
    }

    @Override
    public Optional<Address> execute(Address ip, Memory memory, IInputDevice inputDevice, OutputDevice outputDevice, Integer[] operants) {
        final int result = instructionMode(FIRST_OPERANT_INDEX).peek(memory, operants[FIRST_OPERANT_INDEX]);

        outputDevice.put(result);
        log(toString(result, operants));

        return Optional.of(ip.increase(size()));
    }
}

class JumpIfTrueInstruction extends ModedInstruction {
    protected JumpIfTrueInstruction(Operation operation, InstructionMode[] instructionModes) {
        super(operation, instructionModes);
    }

    @Override
    public Optional<Address> execute(Address ip, Memory memory, IInputDevice inputDevice, OutputDevice outputDevice, Integer[] operants) {
        final int firstOperantValue = instructionMode(FIRST_OPERANT_INDEX).peek(memory, operants[FIRST_OPERANT_INDEX]);
        final int secondOperantValue = instructionMode(SECOND_OPERANT_INDEX).peek(memory, operants[SECOND_OPERANT_INDEX]);

        log(toString(operants));

        return Optional.of(firstOperantValue == 0 ? ip.increase(size()) : Address.of(secondOperantValue));
    }
}

class JumpIfFalseInstruction extends ModedInstruction {
    protected JumpIfFalseInstruction(Operation operation, InstructionMode[] instructionModes) {
        super(operation, instructionModes);
    }

    @Override
    public Optional<Address> execute(Address ip, Memory memory, IInputDevice inputDevice, OutputDevice outputDevice, Integer[] operants) {
        final int firstOperantValue = instructionMode(FIRST_OPERANT_INDEX).peek(memory, operants[FIRST_OPERANT_INDEX]);
        final int secondOperantValue = instructionMode(SECOND_OPERANT_INDEX).peek(memory, operants[SECOND_OPERANT_INDEX]);

        log(toString(operants));

        return Optional.of(firstOperantValue != 0 ? ip.increase(size()) : Address.of(secondOperantValue));
    }
}

class LessThanInstruction extends ModedInstruction {
    protected LessThanInstruction(Operation operation, InstructionMode[] instructionModes) {
        super(operation, instructionModes);
    }

    @Override
    public Optional<Address> execute(Address ip, Memory memory, IInputDevice inputDevice, OutputDevice outputDevice, Integer[] operants) {
        final int firstOperantValue = instructionMode(FIRST_OPERANT_INDEX).peek(memory, operants[FIRST_OPERANT_INDEX]);
        final int secondOperantValue = instructionMode(SECOND_OPERANT_INDEX).peek(memory, operants[SECOND_OPERANT_INDEX]);
        final int thirdOperantValue = operants[THIRD_OPERANT_INDEX];
        final int result = firstOperantValue < secondOperantValue ? 1 : 0;

        memory.poke(Address.of(thirdOperantValue), result);
        log(toString(result, operants));

        return Optional.of(ip.increase(size()));
    }
}

class EqualInstruction extends ModedInstruction {
    protected EqualInstruction(Operation operation, InstructionMode[] instructionModes) {
        super(operation, instructionModes);
    }

    @Override
    public Optional<Address> execute(Address ip, Memory memory, IInputDevice inputDevice, OutputDevice outputDevice, Integer[] operants) {
        final int firstOperantValue = instructionMode(FIRST_OPERANT_INDEX).peek(memory, operants[FIRST_OPERANT_INDEX]);
        final int secondOperantValue = instructionMode(SECOND_OPERANT_INDEX).peek(memory, operants[SECOND_OPERANT_INDEX]);
        final int thirdOperantValue = operants[THIRD_OPERANT_INDEX];
        final int result = firstOperantValue == secondOperantValue ? 1 : 0;

        memory.poke(Address.of(thirdOperantValue), result);
        log(toString(result, operants));

        return Optional.of(ip.increase(size()));
    }
}