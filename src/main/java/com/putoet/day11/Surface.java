package com.putoet.day11;

import com.putoet.grid.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Surface {
    private Panel[][] panels = new Panel[][]{{Panel.blackPanel()}};
    private Point robotPoint = Point.ORIGIN;
    private Direction robotDirection = Direction.UP;

    public Surface() {
    }

    public Surface(String[] initialPanels) {
        assert initialPanels != null && initialPanels[0].length() > 0;

        int len = initialPanels[0].length();
        if (Arrays.stream(initialPanels).anyMatch(line -> line.length() != len))
            throw new AssertionError("Panel line lengths must all be the same");

        this.panels = new Panel[initialPanels.length][];
        for (int idy = 0; idy < initialPanels.length; idy++) {
            this.panels[initialPanels.length - 1 - idy] = lineFrom(initialPanels[idy]);
        }

        this.robotPoint = robotPointFrom(initialPanels).orElse(Point.ORIGIN);
    }

    public PanelColor panelColor() {
        return panel().color();
    }

    private Panel panel() {
        return panels[robotPoint.y()][robotPoint.x()];
    }

    private Optional<Point> robotPointFrom(String[] initialPanels) {
        for (int idy = 0; idy < initialPanels.length; idy++) {
            for (int idx = 0; idx < initialPanels[idy].length(); idx++)
                if (initialPanels[idy].charAt(idx) == '^')
                    return Optional.of(Point.of(idx, initialPanels.length - 1 - idy));
        }
        return Optional.empty();
    }

    private Panel[] lineFrom(String lineAsText) {
        final List<Panel> line = new ArrayList<>();
        lineAsText.chars().forEach(c -> line.add(c == '#' ? Panel.whitePanel() : Panel.blackPanel()));
        return line.toArray(new Panel[0]);
    }

    public long paintedPanelsCount() {
        return Arrays.stream(panels).flatMap(Arrays::stream).filter(Panel::painted).count();
    }

    @Override
    public String toString() {
        final String[] panelsAsText = new String[panels.length];
        for (int idy = 0; idy < panels.length; idy++) {
            StringBuilder sb = new StringBuilder();
            for (int idx = 0; idx < panels[idy].length; idx++)
                if (robotPoint.x() == idx && robotPoint.y() == idy)
                    sb.append(robotDirection.toString());
                else
                    sb.append(panels[idy][idx].color() == PanelColor.WHITE ? '#' : '.');
            panelsAsText[panels.length - 1 - idy] = sb.toString();
        }

        return String.join("\n", panelsAsText);
    }

    public void paint(PanelColor color) {
        panels[robotPoint.y()][robotPoint.x()].paint(color);
    }

    public void moveRobot() {
        robotPoint = robotPoint.add(robotDirection.asMove());
        checkBordersAndExpand();
    }

    private void checkBordersAndExpand() {
        if (robotPoint.x() < 0) {
            expandPanelsToTheLeft();
            robotPoint = robotPoint.add(Point.EAST);
        } else if (robotPoint.y() < 0) {
            expandPanelsAtTheBottom();
            robotPoint = robotPoint.add(Point.NORTH);
        } else if (robotPoint.x() > panels[0].length - 1) expandPanelsAtTheRight();
        else if (robotPoint.y() > panels.length - 1) expandPanelsAtTheTop();
    }

    private void expandPanelsAtTheTop() {
        final Panel[][] newPanels = new Panel[panels.length + 1][];
        System.arraycopy(panels, 0, newPanels, 0, panels.length);
        newPanels[panels.length] = createEmptyPanelLine();
        panels = newPanels;
    }

    private void expandPanelsAtTheRight() {
        for (int idy = 0; idy < panels.length; idy++) {
            final Panel[] newPanelLine = new Panel[panels[idy].length + 1];
            System.arraycopy(panels[idy], 0, newPanelLine, 0, panels[idy].length);
            newPanelLine[panels[idy].length] = Panel.blackPanel();
            panels[idy] = newPanelLine;
        }
    }

    private void expandPanelsAtTheBottom() {
        final Panel[][] newPanels = new Panel[panels.length + 1][];
        System.arraycopy(panels, 0, newPanels, 1, panels.length);
        newPanels[0] = createEmptyPanelLine();
        panels = newPanels;
    }

    private void expandPanelsToTheLeft() {
        for (int idy = 0; idy < panels.length; idy++) {
            final Panel[] newPanelLine = new Panel[panels[idy].length + 1];
            newPanelLine[0] = Panel.blackPanel();
            System.arraycopy(panels[idy], 0, newPanelLine, 1, panels[idy].length);
            panels[idy] = newPanelLine;
        }
    }

    private Panel[] createEmptyPanelLine() {
        final Panel[] newPanelLine = new Panel[panels[0].length];
        for (int idx = 0; idx < newPanelLine.length; idx++)
            newPanelLine[idx] = Panel.blackPanel();
        return newPanelLine;
    }

    public void turnRobotLeft() {
        switch (robotDirection) {
            case UP -> robotDirection = Direction.LEFT;
            case LEFT -> robotDirection = Direction.DOWN;
            case RIGHT -> robotDirection = Direction.UP;
            case DOWN -> robotDirection = Direction.RIGHT;
        }
    }

    public void turnRobotRight() {
        switch (robotDirection) {
            case UP -> robotDirection = Direction.RIGHT;
            case LEFT -> robotDirection = Direction.UP;
            case RIGHT -> robotDirection = Direction.DOWN;
            case DOWN -> robotDirection = Direction.LEFT;
        }
    }
}
