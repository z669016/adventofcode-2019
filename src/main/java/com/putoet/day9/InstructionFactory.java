package com.putoet.day9;

public class InstructionFactory {
    public static Instruction of(Registers regs, IMemory memory, IInputDevice inputDevice, IOutputDevice outputDevice) {
        final int opcode = memory.peek(regs.ip()).intValue();
        final Operation operation = Operation.of(opcode);

        switch (operation) {
            case SUM:
                return new SumInstruction(regs,
                        MemoryAccessorFactory.of(regs, memory, 1),
                        MemoryAccessorFactory.of(regs, memory, 2),
                        MemoryAccessorFactory.of(regs, memory, 3)
                );
            case PRODUCT:
                return new ProductInstruction(regs,
                        MemoryAccessorFactory.of(regs, memory, 1),
                        MemoryAccessorFactory.of(regs, memory, 2),
                        MemoryAccessorFactory.of(regs, memory, 3)
                );
            case INPUT:
                return new InputInstruction(regs,
                        MemoryAccessorFactory.of(regs, memory, 1),
                        inputDevice
                );
            case OUTPUT:
                return new OutputInstruction(regs,
                        MemoryAccessorFactory.of(regs, memory, 1),
                        outputDevice
                );
            case JUMP_IF_TRUE:
                return new JumpIfTrueInstruction(regs,
                        MemoryAccessorFactory.of(regs, memory, 1),
                        MemoryAccessorFactory.of(regs, memory, 2)
                );
            case JUMP_IF_FALSE:
                return new JumpIfFalseInstruction(regs,
                        MemoryAccessorFactory.of(regs, memory, 1),
                        MemoryAccessorFactory.of(regs, memory, 2)
                );
            case LESS_THAN:
                return new LessThanInstruction(regs,
                        MemoryAccessorFactory.of(regs, memory, 1),
                        MemoryAccessorFactory.of(regs, memory, 2),
                        MemoryAccessorFactory.of(regs, memory, 3)
                );
            case EQUAL:
                return new EqualInstruction(regs,
                        MemoryAccessorFactory.of(regs, memory, 1),
                        MemoryAccessorFactory.of(regs, memory, 2),
                        MemoryAccessorFactory.of(regs, memory, 3)
                );
            case ADJUST_RELATIVE_BASE:
                return new AdjustRelativeBaseInstruction(regs,
                        MemoryAccessorFactory.of(regs, memory, 1)
                );
            case EXIT:
                return new ExitInstruction(regs);
            default:
                throw new IllegalStateException("Operation not yet implemented: " + operation);
        }
    }
}
