package com.putoet.intcode;

import java.io.PrintStream;

public class Interpreter {
    private static PrintStream printStream;

    private Interpreter() {
    }

    public static Instruction interpret(Address address, Memory memory) {
        final Opcode opcode = new Opcode(memory.peek(address));
        final Instruction instruction = switch (opcode.opcode()) {
            case 1 -> add(opcode, address, memory);
            case 2 -> mul(opcode, address, memory);
            case 99 -> exit(opcode, address);
            default -> throw new InvalidInstructionOpcode(address, memory.peek(address));
        };

        if (printStream != null)
            printStream.println(instruction);

        return instruction;
    }

    private static Instruction exit(Opcode opcode, Address address) {
        return new AbstractInstruction(opcode) {
            @Override
            public int size() {
                return 1;
            }

            @Override
            public void run() {
            }

            @Override
            public String toString() {
                return String.format("%08d - exit", address.intValue());
            }
        };
    }

    private static Instruction add(Opcode opcode, Address address, Memory memory) {
        return new AbstractInstruction(opcode) {
            final long p1 = memory.peek(address.increase(1));
            final long p2 = memory.peek(address.increase(2));
            final long p3 = memory.peek(address.increase(3));

            @Override
            public int size() {
                return 4;
            }

            @Override
            public void run() {
                setValue3(new Address(p3), memory, getValue1(p1, memory) + getValue2(p2, memory));
            }

            @Override
            public String toString() {
                return String.format("%08d - add %s %s %s",
                        address.intValue(),
                        parameter(opcode.mode1(), p1),
                        parameter(opcode.mode2(), p2),
                        p3
                );
            }
        };
    }

    private static Instruction mul(Opcode opcode, Address address, Memory memory) {
        return new AbstractInstruction(opcode) {
            final long p1 = memory.peek(address.increase(1));
            final long p2 = memory.peek(address.increase(2));
            final long p3 = memory.peek(address.increase(3));

            @Override
            public int size() {
                return 4;
            }

            @Override
            public void run() {
                setValue3(new Address(p3), memory, getValue1(p1, memory) * getValue2(p2, memory));
            }

            @Override
            public String toString() {
                return String.format("%08d - mul %s %s %s",
                        address.intValue(),
                        parameter(opcode.mode1(), p1),
                        parameter(opcode.mode2(), p2),
                        p3
                );
            }
        };
    }

    public static void output() {
        printStream = null;
    }

    public static void output(PrintStream out) {
        printStream = out;
    }

    static class InvalidInstructionOpcode extends IllegalArgumentException {
        public InvalidInstructionOpcode(Address offset, long opcode) {
            super("Invalid opcode " + opcode + " at offset " + offset);
        }
    }
}

abstract class AbstractInstruction implements Instruction {
    protected final Opcode opcode;

    protected AbstractInstruction(Opcode opcode) {
        this.opcode = opcode;
    }

    protected static String parameter(Mode mode, long value) {
        return mode == Mode.POSITION ? String.valueOf(value) : "[" + value + "]";
    }

    protected long getValue1(long value, Memory memory) {
        return getValue(opcode.mode1(), value, memory);
    }

    protected long getValue2(long value, Memory memory) {
        return getValue(opcode.mode2(), value, memory);
    }

    protected long getValue3(long value, Memory memory) {
        return getValue(opcode.mode3(), value, memory);
    }

    protected void setValue1(Address address, Memory memory, long newValue) {
        setValue(address, memory, newValue);
    }

    protected void setValue2(Address address, Memory memory, long newValue) {
        setValue(address, memory, newValue);
    }

    protected void setValue3(Address address, Memory memory, long newValue) {
        setValue(address, memory, newValue);
    }

    private long getValue(Mode mode, long value, Memory memory) {
        return mode == Mode.IMMEDIATE ? value : memory.peek(new Address(value));
    }

    protected void setValue(Address address, Memory memory, long newValue) {
        memory.poke(address, newValue);
    }

    @Override
    public Opcode opcode() {
        return opcode;
    }

    @Override
    public String toString() {
        return opcode.toString();
    }
}

