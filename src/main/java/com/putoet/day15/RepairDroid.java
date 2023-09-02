package com.putoet.day15;

import com.putoet.grid.Point;
import com.putoet.intcode.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalLong;

class RepairDroid implements Runnable {
    private final IntCodeDevice device;
    private final Point location;
    private long result = -1;

    public RepairDroid(@NotNull IntCodeDevice device, @NotNull Point location) {
        this.device = device;
        this.location = location;
    }

    public static RepairDroid init(@NotNull List<Long> intCode, @NotNull Point location) {
        final var memory = new ExpandableMemory(intCode);
        final var device = IntCodeComputer.builder()
                .memory(memory)
                .input(new IntCodeInputOutputDevice())
                .output(new IntCodeInputOutputDevice())
                .resumable()
                .build();
        return new RepairDroid(device, location);
    }

    public static List<RepairDroid> successorsWalls(@NotNull RepairDroid repairDroid) {
        if (repairDroid.result() != MovementSensor.MOVED)
            return List.of();

        final var successors = new ArrayList<RepairDroid>();
        for (var move : List.of(1, 2, 3, 4)) {
            final var successor = forMove(repairDroid, move);
            successors.add(successor);
        }

        return successors;
    }

    private static RepairDroid forMove(@NotNull RepairDroid repairDroid, int move) {
        final var newLocation = repairDroid.location().add(switch (move) {
            case 1 -> Point.NORTH;
            case 2 -> Point.SOUTH;
            case 3 -> Point.WEST;
            default -> Point.EAST;
        });

        final var copy = intCodeDeviceCopy(repairDroid, move);
        return new RepairDroid(copy, newLocation);
    }

    private static IntCodeDevice intCodeDeviceCopy(@NotNull RepairDroid repairDroid, int move) {
        final var copy = repairDroid.device.copy();
        final var input = new IntCodeInputOutputDevice();
        input.offer(move);

        copy.input(new IntCodeInputOutputDevice());
        copy.output(new IntCodeInputOutputDevice());
        ((IntCodeInputOutputDevice) copy.input()).offer(move);
        return copy;
    }

    public static boolean wallOrOxygenSystemFound(@NotNull RepairDroid repairDroid) {
        return repairDroid.result() == MovementSensor.WALL_HIT
                || repairDroid.result() == MovementSensor.OXYGEN_SYSTEM_FOUND;
    }

    @Override
    public void run() {
        device.run();
    }

    @Override
    public String toString() {
        return "RepairDroid at " + location + " last result is " + result();
    }

    public Point location() {
        return location;
    }

    public MovementSensor result() {
        if (result == -1) {
            if (location.equals(Point.ORIGIN))
                return MovementSensor.MOVED;

            run();

            final OptionalLong movementSensor = ((IntCodeInputOutputDevice) device.output()).poll();
            if (movementSensor.isEmpty()) {
                System.out.print('E');
                return MovementSensor.MOVED;
            }

            result = movementSensor.getAsLong();
            if (result < 0 || result > 2)
                throw new IllegalStateException("Invalid movement sensor output value '" + result + "'");
        }
        return MovementSensor.values()[(int) result];
    }

    @Override
    public boolean equals(Object o) {
        if (result() == MovementSensor.WALL_HIT)
            return false;

        if (this == o) return true;
        if (!(o instanceof RepairDroid that)) return false;
        return Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    public enum MovementSensor {
        WALL_HIT,
        MOVED,
        OXYGEN_SYSTEM_FOUND
    }
}
