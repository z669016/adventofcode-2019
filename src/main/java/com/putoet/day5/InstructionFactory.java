package com.putoet.day5;

public class InstructionFactory {
    public static Instruction of(int opcode) {
        int operantModes = opcode / 100;
        opcode = opcode % 100;

        final Operation operation = Operation.of(opcode);
        switch (operation) {
            case INPUT: return new InputInstruction(operation);
            case OUTPUT: return new OutputInstruction(operation);
            case SUM: return new SumInstruction(operation, modes(operantModes));
            case PRODUCT: return new ProductInstruction(operation, modes(operantModes));
            case EXIT:
            default:
                return new ExitInstruction(operation);
        }
    }

    private static InstructionMode[] modes(int operantModes) {
        return new InstructionMode[] {
                InstructionMode.of(operantModes % 10),
                InstructionMode.of((operantModes / 10) % 10)
        };
    }
}