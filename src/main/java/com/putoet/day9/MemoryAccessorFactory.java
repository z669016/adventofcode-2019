package com.putoet.day9;

public class MemoryAccessorFactory {
    public static IMemoryAccessor of(Registers regs, IMemory memory, int offset) {
        final MemoryAccessMode mode = ofInstructionCode(memory.peek(regs.ip()).intValue(), offset);
        switch (mode) {
            case POSITION_MODE:
                return new PositionMemoryAccessor(regs.ip().increase(offset), memory);
            case IMMEDIATE_MODE:
                return new ImmediateMemoryAccessor(regs.ip().increase(offset), memory);
            case RELATIVE_BASE_MODE:
                return new RelativeBaseMemoryAccessor(regs.rb(), regs.ip().increase(offset), memory);
            default:
                throw new IllegalArgumentException("Invalid mode parameter " + mode);
        }
    }

    private static MemoryAccessMode ofInstructionCode(int instructionCode, int offset) {
        final int modes = instructionCode / 100;
        final int code = (modes / ((int) Math.pow(10, offset - 1))) % 10;
        switch (code) {
            case 0:
                return MemoryAccessMode.POSITION_MODE;
            case 1:
                return MemoryAccessMode.IMMEDIATE_MODE;
            case 2:
                return MemoryAccessMode.RELATIVE_BASE_MODE;
            default:
                throw new IllegalArgumentException("Only code 0, 1, and 2 are supported.");
        }
    }

    private enum MemoryAccessMode {
        POSITION_MODE,
        IMMEDIATE_MODE,
        RELATIVE_BASE_MODE
    }
}
