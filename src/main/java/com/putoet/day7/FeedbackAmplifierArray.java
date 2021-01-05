package com.putoet.day7;

import com.putoet.day5.Processor;

import java.util.List;

public class FeedbackAmplifierArray extends AmplifierArray {
    private final Computer[] computers;

    private FeedbackAmplifierArray(List<Integer> sequence, List<Integer> intCode, Integer inputSignal) {
        super(sequence, intCode, inputSignal);

        computers = new Computer[ARRAY_SIZE];
        for (int idx = 0; idx < ARRAY_SIZE; idx++) {
            log("Build resumable computer for amplifier " + idx + " using phase setting " + sequence.get(idx));
            computers[idx] = Computer.resumableBuilder()
                    .memory(intCode)
                    .input(List.of(sequence.get(idx)))
                    .build();
        }
        computers[0].provideInpute(0);
    }

    public Integer run() {
        log("Run the amplifier array of size " + ARRAY_SIZE);

        while (computers[ARRAY_SIZE - 1].state() != Processor.State.EXITED) {
            for (int idx = 0; idx < ARRAY_SIZE; idx++) {
                computers[idx].run();
                feedback(idx);
            }
        }

        final Integer result = computers[ARRAY_SIZE - 1].lastOutput();
        return result;
    }

    private void feedback(int idx) {
        if (computers[idx].state() != Processor.State.EXITED)
            log("Amplifier feedback " + computers[idx].lastOutput() + " from " + idx + " to " + next(idx));
        computers[next(idx)].provideInpute(computers[idx].lastOutput());
    }

    private int next(int idx) {
        return idx != ARRAY_SIZE - 1 ? idx + 1 : 0;
    }

    public static FeedbackAmplifierArrayBuilder builder() {
        return new FeedbackAmplifierArrayBuilder();
    }

    static class FeedbackAmplifierArrayBuilder extends AmplifierArrayBuilder {
        @Override
        public AmplifierArray build() {
            if (sequence == null || intCode == null || inputSignal == null)
                throw new IllegalStateException("Missing build parameters");

            return new FeedbackAmplifierArray(sequence, intCode, inputSignal);
        }

        @Override
        public AmplifierArrayBuilder sequence(List<Integer> sequence) {
            if (this.sequence != null)
                throw new IllegalStateException("Sequence already set");
            if (sequence == null || sequence.size() != 5)
                throw new IllegalArgumentException("Invalid sequence");
            if (!sequence.containsAll(List.of(5, 6, 7, 8, 9)))
                throw new IllegalArgumentException("Sequence should contain 5 unique values of 0..4");

            this.sequence = sequence;
            return this;
        }


    }
}
