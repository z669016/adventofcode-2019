package com.putoet.day13;

import com.putoet.grid.Point;
import com.putoet.intcode.IntCodeComputer;
import com.putoet.intcode.IntCodeDevice;
import com.putoet.intcode.Memory;
import com.putoet.intcode.OutputDevice;

import java.util.List;

public class Game implements OutputDevice, Runnable {
    private final ExtendableSurface surface = new ExtendableSurface();

    private final Memory memory;
    private final Joystick joystick;

    private IntCodeDevice device;
    private Point ball = null;
    private Point paddle = null;

    private int x;
    private int y;

    private State state = State.X;
    private long score;

    public Game(Memory memory, Joystick joystick) {
        this.memory = memory;
        this.joystick = joystick;
    }

    public long count(Tile tile) {
        return surface.count(tile);
    }

    @Override
    public void offer(long value) {
        assert value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE;

        switch (state) {
            case X -> {
                x = (int) value;
                state = State.Y;
            }
            case Y -> {
                y = (int) value;
                state = State.SYMBOL;
            }
            case SYMBOL -> {
                if (x == -1 && y == 0) {
                    score = value;
                } else {
                    final Tile tile = TileFactory.of((int) value);

                    if (tile == TileFactory.BALL) {
                        if (ball != null)
                            surface.set(ball.x, ball.y, TileFactory.EMPTY);
                        ball = Point.of(x, y);
                        surface.set(x, y, TileFactory.BALL);
                    } else if (tile == TileFactory.PADDLE) {
                        if (paddle != null)
                            surface.set(paddle.x, paddle.y, TileFactory.EMPTY);
                        paddle = Point.of(x, y);
                        surface.set(x, y, TileFactory.PADDLE);
                    } else {
                        surface.set(x, y, tile);
                    }
                }
                state = State.X;
                if (ball != null && paddle != null) {
                    if (ball.x < paddle.x) joystick.left();
                    else if (ball.x > paddle.x) joystick.right();
                    else joystick.neutral();
                }
            }
        }
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public List<Long> asList() {
        return List.of();
    }

    @Override
    public String toString() {
        return "SCORE " + score + "\n" + surface.toString();
    }

    @Override
    public void run() {
        if (device == null)
            device = IntCodeComputer.builder().memory(memory).input(joystick).output(this).build();

        device.run();
    }

    private enum State {
        X, Y, SYMBOL
    }
}
