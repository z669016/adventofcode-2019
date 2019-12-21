package com.putoet.day5;

public class InstructionFactory {
    public static Instruction of(int opcode) {
        int operantModes = opcode / 100;
        opcode = opcode % 100;

        final Operation operation = Operation.of(opcode);
        switch (operation) {
            case INPUT: return new InputInstruction(operation);
            case OUTPUT: return new OutputInstruction(operation, modes(operantModes));
            case SUM: return new SumInstruction(operation, modes(operantModes));
            case PRODUCT: return new ProductInstruction(operation, modes(operantModes));
            case JUMP_IF_TRUE: return new JumpIfTrueInstruction(operation, modes(operantModes));
            case JUMP_IF_FALSE: return new JumpIfFalseInstruction(operation, modes(operantModes));
            case LESS_THAN: return new LessThanInstruction(operation, modes(operantModes));
            case EQUAL: return new EqualInstruction(operation, modes(operantModes));
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