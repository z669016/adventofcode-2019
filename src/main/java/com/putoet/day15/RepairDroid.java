package com.putoet.day15;

import com.putoet.grid.Point;
import com.putoet.intcode.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalLong;

public class RepairDroid implements Runnable {
    private final IntCodeDevice device;
    private final Point location;
    private long result = -1;

    public RepairDroid(IntCodeDevice device, Point location) {
        this.device = device;
        this.location = location;
    }

    public static RepairDroid init(List<Long> intCode, Point location) {
        final Memory memory = new ExpandableMemory(intCode);
        final IntCodeDevice device = IntCodeComputer.builder()
                .memory(memory)
                .input(new IntCodeInputOutputDevice())
                .output(new IntCodeInputOutputDevice())
                .resumable()
                .build();
        return new RepairDroid(device, location);
    }

    public static List<RepairDroid> successorsWalls(RepairDroid repairDroid) {
        if (repairDroid.result() != MovementSensor.MOVED)
            return List.of();

        final List<RepairDroid> successors = new ArrayList<>();
        for (int move : List.of(1, 2, 3, 4)) {
            final RepairDroid successor = forMove(repairDroid, move);
            successors.add(successor);
        }

        return successors;
    }

    private static RepairDroid forMove(RepairDroid repairDroid, int move) {
        final Point newLocation = repairDroid.location().add(switch (move) {
            case 1 -> Point.NORTH;
            case 2 -> Point.SOUTH;
            case 3 -> Point.WEST;
            default -> Point.EAST;
        });

        final IntCodeDevice copy = intCodeDeviceCopy(repairDroid, move);
        return new RepairDroid(copy, newLocation);
    }

    private static IntCodeDevice intCodeDeviceCopy(RepairDroid repairDroid, int move) {
        final IntCodeDevice copy = repairDroid.device.copy();
        final IntCodeInputOutputDevice input = new IntCodeInputOutputDevice();
        input.offer(move);

        copy.input(new IntCodeInputOutputDevice());
        copy.output(new IntCodeInputOutputDevice());
        ((IntCodeInputOutputDevice) copy.input()).offer(move);
        return copy;
    }

    public static boolean wallOrOxygenSystemFound(RepairDroid repairDroid) {
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
        if (!(o instanceof RepairDroid)) return false;
        RepairDroid that = (RepairDroid) o;
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
