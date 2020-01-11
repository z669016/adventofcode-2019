package com.putoet.day15;

import com.putoet.day9.IOutputDevice;


public class Explorer {
    public static final int BLOCKED = 0;
    public static final int MOVED = 1;
    public static final int HIT_AIR = 2;

    private final Navigator navigator;
    private final IOutputDevice outputDevice;
    private final IExtendableSurface surface;

    private final Direction initialDirection = Direction.NORTH;
    private Direction currentDirection = initialDirection;

    public Explorer(IExtendableSurface surface, Navigator navigator, IOutputDevice outputDevice) {
        this.surface = surface;
        this.navigator = navigator;
        this.outputDevice = outputDevice;
    }


    public void explore() {
        navigator.move(Direction.NORTH);
        checkStatus();
    }

    private void checkStatus() {
        switch (outputDevice.get().intValue()) {
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

            case BLOCKED:
            default:
                if (navigator.currentTile().type() == Tile.Type.UNKNOWN) {
                    navigator.currentTile().discovered(Tile.Type.WALL);
                    surface.paint(navigator.currentPoint(), navigator.currentTile());
                }
                navigator.back();
        }
    }
}


