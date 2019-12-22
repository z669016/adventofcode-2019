package com.putoet.day7;

import java.util.List;

public final class AmplifierArray {
    public static final int ARRAY_SIZE = 5;

    private static Boolean logEnabled = false;
    public static final void enableLog() {
        logEnabled = true;
    }
    public static final void disableLog() {
        logEnabled = false;
    }

    private final int inputSignal;
    private final List<Integer> sequence;
    private final List<Integer> intCode;

    private AmplifierArray(List<Integer> sequence, List<Integer> intCode, Integer inputSignal) {
        this.sequence = sequence;
        this.intCode = intCode;
        this.inputSignal = inputSignal;
    }

    public Integer run() {
        log("Run the amplifier array of size " + ARRAY_SIZE);

        Integer result = inputSignal;
        for (int idx = 0; idx < ARRAY_SIZE; idx++) {
            log("Build computer for amplifier " + idx + " using phase setting " + sequence.get(idx) + " and input signal " + result);
            final Computer computer = Computer.builder()
                    .memory(intCode)
                    .input(List.of(sequence.get(idx), result))
                    .build();
            computer.run();
            log("Amplifier " + idx + " result is " + computer.outputDump());

            if (computer.outputDump().size() != 1)
                throw new IllegalStateException("Unexpected output from amplifier " + idx + " (" + computer.outputDump());

            result = computer.outputDump().get(0);
        }

        return result;
    }

    private void log(String line) {
        if (logEnabled)
            System.out.println(line);
    }

    public static AmplifierArrayBuilder builder() {
        return new AmplifierArrayBuilder();
    }

    static class AmplifierArrayBuilder {
        private List<Integer> sequence;
        private List<Integer> intCode;
        private Integer inputSignal;

        public AmplifierArrayBuilder sequence(List<Integer> sequence) {
            if (this.sequence != null)
                throw new IllegalStateException("Sequence already set");
            if (sequence == null || sequence.size() != 5)
                throw new IllegalArgumentException("Invalid sequence");
            if (!sequence.containsAll(List.of(0, 1, 2, 3, 4)))
                throw new IllegalArgumentException("Sequence should contain 5 unique values of 0..4");

            this.sequence = sequence;
            return this;
        }

        public AmplifierArrayBuilder intCode(List<Integer> intCode) {
            if (this.intCode != null)
                throw new IllegalStateException("IntCode already set");
            if (intCode == null || intCode.size() == 0)
                throw new IllegalArgumentException("Invalid program code");

            this.intCode = intCode;
            return this;
        }

        public AmplifierArrayBuilder inputSignal(int inputSignal) {
            if (this.inputSignal != null)
                throw new IllegalStateException("Input signal already set");

            this.inputSignal = inputSignal;
            return this;
        }

        public AmplifierArray build() {
            if (sequence == null || intCode == null || inputSignal == null)
                throw new IllegalStateException("Missing build parameters");

            return new AmplifierArray(sequence, intCode, inputSignal);
        }
    }
}

