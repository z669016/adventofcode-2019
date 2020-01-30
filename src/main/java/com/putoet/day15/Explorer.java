package com.putoet.day15;

public class Explorer {
    private final Navigator navigator;
    private final Sensor sensor;
    private final IExtendableSurface surface;

    private final Direction initialDirection = Direction.NORTH;
    private Direction currentDirection = initialDirection;
    private boolean initialTurnTaken = false;

    public Explorer(IExtendableSurface surface, Navigator navigator, Sensor sensor) {
        this.surface = surface;
        this.navigator = navigator;
        this.sensor = sensor;
        surface.paint(navigator.currentPoint(), navigator.currentTile());
    }

    public void explore() {
        System.out.println(surface);

        navigator.move(currentDirection);
        checkStatus();
        System.out.println(surface);

        while (navigator.currentTile().type() != Tile.Type.AIR) {
            currentDirection = nextDirection();
            navigator.move(currentDirection);
            checkStatus();

            System.out.println(surface);
        }
    }

    private Direction nextDirection() {
        Direction nextDirection = navigator.lastDirection();

        // Once an initial turn has been taken, first try to get back on the original course
        if (initialTurnTaken && !navigator.currentTile().blockedOnDirection(nextDirection.right())) {
            return nextDirection.right();
        }

        // if current direction is not explored yet, just continue
        if (navigator.currentTile().get(nextDirection).isEmpty())
            return nextDirection;

        // when on a dead end, go back until you have options again
        while (navigator.currentTile().isDeadEnd() && navigator.currentTile() != Tile.START) {
            navigator.currentTile().discovered(Tile.Type.BLOCKED);
            navigator.back();
        }

        // Turn left until there is an opening
        while (navigator.currentTile().blockedOnDirection(nextDirection))
            nextDirection = nextDirection.left();
        initialTurnTaken = true;

        return nextDirection;
        // throw new IllegalStateException("Cannot determine next direction");
    }

    private void checkStatus() {
        switch (sensor.read()) {
            case HIT_AIR:
                if (navigator.currentTile().type() == Tile.Type.UNKNOWN) {
                    navigator.currentTile().discovered(Tile.Type.AIR);
                    surface.paint(navigator.currentPoint(), navigator.currentTile());
                }
                break;

            case MOVED:
                if (navigator.currentTile().type() == Tile.Type.UNKNOWN) {
                    navigator.currentTile().discovered(Tile.Type.OPEN);
                    surface.paint(navigator.currentPoint(), navigator.currentTile());
                }
                break;

            case HIT_WALL:
            default:
                if (navigator.currentTile().type() == Tile.Type.UNKNOWN) {
                    navigator.currentTile().discovered(Tile.Type.WALL);
                    surface.paint(navigator.currentPoint(), navigator.currentTile());
                }
                navigator.back();
        }
    }
}


