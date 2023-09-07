package com.putoet.intcode;

import java.util.Iterator;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;

public class Interpreter implements Iterator<Instruction> {
    private final IntCodeDevice device;

    public Interpreter(IntCodeDevice device) {
        this.device = device;
    }



    @Override
    public boolean hasNext() {
        final Memory memory = device.memory();
        final Address address = device.ip();
        final Opcode opcode = new Opcode(memory.peek(address));

        return opcode.opcode() != Instruction.EXIT;
    }

    @Override
    public Instruction next() {
        final Memory memory = device.memory();
        final Address address = device.ip();

        final Opcode opcode = new Opcode(memory.peek(address));
        final Instruction instruction = switch (opcode.opcode()) {
            case Instruction.ADD -> add(opcode);
            case Instruction.MUL -> mul(opcode);
            case Instruction.IN -> in(opcode);
            case Instruction.OUT -> out(opcode);
            case Instruction.JIT -> jit(opcode);
            case Instruction.JIF -> jif(opcode);
            case Instruction.LT -> lt(opcode);
            case Instruction.EQ -> eq(opcode);
            case Instruction.RB -> rb(opcode);
            case Instruction.EXIT -> ex(opcode);
            default -> throw new InvalidInstructionOpcodeException(address, opcode);
        };

        if (device.printStream() != null)
            device.printStream().println(instruction);

        return instruction;
    }

    private Instruction ex(Opcode opcode) {
        final Address address = device.ip();

        return new AbstractInstruction(opcode, device.relativeBase()) {
            @Override
            public int size() {
                return 1;
            }

            @Override
            public void run() {
                next(device);
            }

            @Override
            public String toString() {
                return String.format("%08d - exit", address.intValue());
            }
        };
    }


    private Instruction add(Opcode opcode) {
        final Address address = device.ip();
        final Memory memory = device.memory();

        return new AbstractInstruction(opcode, device.relativeBase()) {
            final long p1 = memory.peek(address.increase(1));
            final long p2 = memory.peek(address.increase(2));
            final long p3 = memory.peek(address.increase(3));

            @Override
            public int size() {
                return 4;
            }

            @Override
            public void run() {
                setValue3(p3, memory, getValue1(p1, memory) + getValue2(p2, memory));
                next(device);
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

    private Instruction mul(Opcode opcode) {
        final Address address = device.ip();
        final Memory memory = device.memory();

        return new AbstractInstruction(opcode, device.relativeBase()) {
            final long p1 = memory.peek(address.increase(1));
            final long p2 = memory.peek(address.increase(2));
            final long p3 = memory.peek(address.increase(3));

            @Override
            public int size() {
                return 4;
            }

            @Override
            public void run() {
                setValue3(p3, memory, getValue1(p1, memory) * getValue2(p2, memory));
                next(device);
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

    private Instruction in(Opcode opcode) {
        final Address address = device.ip();
        final Memory memory = device.memory();
        final InputDevice input = device.input();

        return new AbstractInstruction(opcode, device.relativeBase()) {
            final long p1 = memory.peek(address.increase(1));

            @Override
            public int size() {
                return 2;
            }

            @Override
            public void run() {
                if (input == null)
                    throw new NoInputAvailableException(address);

                final OptionalLong value = input.poll(device.timeout(), device.timeUnit());
                if (value.isPresent()) {
                    setValue1(p1, memory, value.getAsLong());
                    next(device);
                    return;
                }

                if (device.resumable()) {
                    device.blockForInput();
                    return;
                }

                throw new NoInputSignalAvailableException(address, device.timeout(), device.timeUnit());
            }

            @Override
            public String toString() {
                return String.format("%08d - in %s",
                        address.intValue(),
                        parameter(opcode.mode1(), p1)
                );
            }
        };
    }

    private Instruction out(Opcode opcode) {
        final Address address = device.ip();
        final Memory memory = device.memory();
        final OutputDevice output = device.output();

        return new AbstractInstruction(opcode, device.relativeBase()) {
            final long p1 = memory.peek(address.increase(1));

            @Override
            public int size() {
                return 2;
            }

            @Override
            public void run() {
                if (output == null)
                    throw new NoOutputAvailableException(address);

                output.offer(getValue1(p1, memory));
                next(device);
            }

            @Override
            public String toString() {
                return String.format("%08d - out %s",
                        address.intValue(),
                        parameter(opcode.mode1(), p1)
                );
            }
        };
    }

    private Instruction jit(Opcode opcode) {
        final Address address = device.ip();
        final Memory memory = device.memory();

        return new AbstractInstruction(opcode, device.relativeBase()) {
            final long p1 = memory.peek(address.increase(1));
            final long p2 = memory.peek(address.increase(2));

            @Override
            public int size() {
                return 3;
            }

            @Override
            public void run() {
                if (getValue1(p1, memory) != 0L)
                    device.ip(getValue2(p2, memory));
                else
                    next(device);
            }

            @Override
            public String toString() {
                return String.format("%08d - jit %s %s",
                        address.intValue(),
                        parameter(opcode.mode1(), p1),
                        parameter(opcode.mode2(), p2)
                );
            }
        };
    }

    private Instruction jif(Opcode opcode) {
        final Address address = device.ip();
        final Memory memory = device.memory();

        return new AbstractInstruction(opcode, device.relativeBase()) {
            final long p1 = memory.peek(address.increase(1));
            final long p2 = memory.peek(address.increase(2));

            @Override
            public int size() {
                return 3;
            }

            @Override
            public void run() {
                if (getValue1(p1, memory) == 0L)
                    device.ip(getValue2(p2, memory));
                else
                    next(device);
            }

            @Override
            public String toString() {
                return String.format("%08d - jif %s %s",
                        address.intValue(),
                        parameter(opcode.mode1(), p1),
                        parameter(opcode.mode2(), p2)
                );
            }
        };
    }


    private Instruction lt(Opcode opcode) {
        final Address address = device.ip();
        final Memory memory = device.memory();

        return new AbstractInstruction(opcode, device.relativeBase()) {
            final long p1 = memory.peek(address.increase(1));
            final long p2 = memory.peek(address.increase(2));
            final long p3 = memory.peek(address.increase(3));

            @Override
            public int size() {
                return 4;
            }

            @Override
            public void run() {
                setValue3(p3, memory, getValue1(p1, memory) < getValue2(p2, memory) ? 1 : 0);
                next(device);
            }

            @Override
            public String toString() {
                return String.format("%08d - lt %s %s %s",
                        address.intValue(),
                        parameter(opcode.mode1(), p1),
                        parameter(opcode.mode2(), p2),
                        p3
                );
            }
        };
    }

    private Instruction eq(Opcode opcode) {
        final Address address = device.ip();
        final Memory memory = device.memory();

        return new AbstractInstruction(opcode, device.relativeBase()) {
            final long p1 = memory.peek(address.increase(1));
            final long p2 = memory.peek(address.increase(2));
            final long p3 = memory.peek(address.increase(3));

            @Override
            public int size() {
                return 4;
            }

            @Override
            public void run() {
                setValue3(p3, memory, getValue1(p1, memory) == getValue2(p2, memory) ? 1 : 0);
                next(device);
            }

            @Override
            public String toString() {
                return String.format("%08d - eq %s %s %s",
                        address.intValue(),
                        parameter(opcode.mode1(), p1),
                        parameter(opcode.mode2(), p2),
                        p3
                );
            }
        };
    }

    private Instruction rb(Opcode opcode) {
        final Address address = device.ip();
        final Memory memory = device.memory();

        return new AbstractInstruction(opcode, device.relativeBase()) {
            final long p1 = memory.peek(address.increase(1));

            @Override
            public int size() {
                return 2;
            }

            @Override
            public void run() {
                device.relativeBase(getValue1(p1, memory));
                next(device);
            }

            @Override
            public String toString() {
                return String.format("%08d - rb %s",
                        address.intValue(),
                        parameter(opcode.mode1(), p1)
                );
            }
        };
    }

    static class InvalidInstructionOpcodeException extends IllegalArgumentException {
        public InvalidInstructionOpcodeException(Address offset, Opcode opcode) {
            super("Invalid opcode " + opcode + " at offset " + offset);
        }
    }

    static class NoInputSignalAvailableException extends IllegalStateException {
        public NoInputSignalAvailableException(Address ip, int timeout, TimeUnit timeUnit) {
            super("No input available for IN at address " + ip + " after " + timeout + " " + timeUnit);
        }
    }

    static class NoInputAvailableException extends IllegalStateException {
        public NoInputAvailableException(Address ip) {
            super("No input channel available for this device for IN at address " + ip);
        }
    }

    static class NoOutputAvailableException extends IllegalStateException {
        public NoOutputAvailableException(Address ip) {
            super("No output channel available for this device for OUT at address " + ip);
        }
    }
}

abstract class AbstractInstruction implements Instruction {
    protected final Opcode opcode;
    protected final Address relativeBase;

    protected AbstractInstruction(Opcode opcode, Address relativeBase) {
        this.opcode = opcode;
        this.relativeBase = relativeBase;
    }

    protected static String parameter(Mode mode, long value) {
        return mode == Mode.POSITION ? String.valueOf(value) : "[" + value + "]";
    }

    @Override
    public void next(IntCodeDevice device) {
        device.next(size());
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

    protected void setValue1(long value, Memory memory, long newValue) {
        setValue(opcode.mode1(), value, memory, newValue);
    }

    protected void setValue2(long value, Memory memory, long newValue) {
        setValue(opcode.mode2(), value, memory, newValue);
    }

    protected void setValue3(long value, Memory memory, long newValue) {
        setValue(opcode.mode3(), value, memory, newValue);
    }

    private long getValue(Mode mode, long value, Memory memory) {
        return switch (mode) {
            case IMMEDIATE -> value;
            case POSITION -> memory.peek(new Address(value));
            case RELATIVE -> memory.peek(relativeBase.increase((int) value));
        };
    }

    protected void setValue(Mode mode, long value, Memory memory, long newValue) {
        switch (mode) {
            case IMMEDIATE -> throw new IllegalStateException("Cannot set memory position in immediate mode.");
            case POSITION -> memory.poke(new Address(value), newValue);
            case RELATIVE -> memory.poke(relativeBase.increase((int) value), newValue);
        }
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

