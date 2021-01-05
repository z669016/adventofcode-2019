package com.putoet.intcode;

import java.io.PrintStream;

public class Interpreter {
    private static PrintStream printStream;

    public static Instruction interpret(Address address, Memory memory) {
        final Instruction instruction = switch ((int) memory.peek(address)) {
            case 1 -> add(address, memory);
            case 2 -> mul(address, memory);
            case 99 -> exit(address);
            default -> throw new InvalidInstructionOpcode(address, memory.peek(address));
        };

        if (printStream != null)
            printStream.println(instruction);

        return instruction;
    }

    private static Instruction exit(Address address) {
        return new Instruction() {
            @Override
            public int size() {
                return 1;
            }

            @Override
            public int opcode() {
                return Instruction.EXIT;
            }

            @Override
            public void run() {}

            @Override
            public String toString() {
                return String.format("%08d - exit", address.intValue());
            }
        };
    }

    private static Instruction add(Address address, Memory memory) {
        return new Instruction() {
            final Address fromA = new Address(memory.peek(address.increase(1)));
            final Address fromB = new Address(memory.peek(address.increase(2)));
            final Address to = new Address(memory.peek(address.increase(3)));

            @Override
            public int size() {
                return 4;
            }

            @Override
            public int opcode() {
                return 1;
            }

            @Override
            public void run() {
                memory.poke(to, memory.peek(fromA) + memory.peek(fromB));
            }

            @Override
            public String toString() {
                return String.format("%08d - add [%s] [%s] [%s]", address.intValue(), fromA, fromB, to);
            }
        };
    }

    private static Instruction mul(Address address, Memory memory) {
        return new Instruction() {
            final Address fromA = new Address(memory.peek(address.increase(1)));
            final Address fromB = new Address(memory.peek(address.increase(2)));
            final Address to = new Address(memory.peek(address.increase(3)));

            @Override
            public int size() {
                return 4;
            }

            @Override
            public int opcode() {
                return 1;
            }

            @Override
            public void run() {
                memory.poke(to, memory.peek(fromA) * memory.peek(fromB));
            }

            @Override
            public String toString() {
                return String.format("%08d - mul [%s] [%s] [%s]", address.intValue(), fromA, fromB, to);
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

    private Interpreter() {}
}


