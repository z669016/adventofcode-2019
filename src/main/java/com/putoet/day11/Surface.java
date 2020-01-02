package com.putoet.day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Surface {
    private Panel[][] panels = new Panel[][]{{Panel.blackPanel()}};
    private Point robotPoint = new Point();
    private Direction robotDirection = Direction.UP;

    public Surface() {
    }

    public Surface(String[] initialPanels) {
        if (panels == null || initialPanels[0].length() == 0)
            throw new IllegalArgumentException("Empty panel");

        int len = initialPanels[0].length();
        if (Arrays.stream(initialPanels).anyMatch(line -> line.length() != len))
            throw new IllegalArgumentException("Panel line lengths would all be the same");

        this.panels = new Panel[initialPanels.length][];
        for (int idy = 0; idy < initialPanels.length; idy++) {
            this.panels[initialPanels.length - 1 - idy] = lineFrom(initialPanels[idy]);
        }

        this.robotPoint = robotPointFrom(initialPanels).orElse(new Point());
    }

    public Color panelColor() {
        return panel().color();
    }

    private Panel panel() {
        return panels[robotPoint.y()][robotPoint.x()];
    }

    private Optional<Point> robotPointFrom(String[] initialPanels) {
        for (int idy = 0; idy < initialPanels.length; idy ++) {
            for (int idx = 0; idx < initialPanels[idy].length(); idx++)
                if (initialPanels[idy].charAt(idx) == '^')
                    return Optional.of(new Point(idx, initialPanels.length - 1 - idy));
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
                if (robotPoint.isAt(idx, idy))
                    sb.append(robotDirection.toString());
                else
                    sb.append(panels[idy][idx].color() == Color.WHITE ? '#' : '.');
            panelsAsText[panels.length - 1 - idy] = sb.toString();
        }

        return Arrays.stream(panelsAsText).collect(Collectors.joining("\n"));
    }

    public void paint(Color color) {
        panels[robotPoint.y()][robotPoint.x()].paint(color);
    }

    public void moveRobot() {
        switch (robotDirection) {
            case UP:
                robotPoint = robotPoint.moveUp();
                break;
            case LEFT:
                robotPoint = robotPoint.moveLeft();
                break;
            case RIGHT:
                robotPoint = robotPoint.moveRight();
                break;
            case DOWN:
                robotPoint = robotPoint.moveDown();
                break;
        }
        checkBordersAndExpand();
    }

    private void checkBordersAndExpand() {
        if (robotPoint.x() < 0) {
            expandPanelsToTheLeft();
            robotPoint = robotPoint.moveRight();
        } else if (robotPoint.y() < 0) {
            expandPanelsAtTheBottom();
            robotPoint = robotPoint.moveUp();
        } else if (robotPoint.x() > panels[0].length - 1) expandPanelsAtTheRight();
        else if (robotPoint.y() > panels.length - 1) expandPanelsAtTheTop();
    }

    private void expandPanelsAtTheTop() {
        final Panel[][] newPanels = new Panel[panels.length + 1][];
        for (int idy = 0; idy < panels.length; idy++) {
            newPanels[idy] = panels[idy];
        }
        newPanels[panels.length] = createEmptyPanelLine();
        panels = newPanels;
    }

    private void expandPanelsAtTheRight() {
        for (int idy = 0; idy < panels.length; idy++) {
            final Panel[] newPanelLine = new Panel[panels[idy].length + 1];
            for (int idx = 0; idx < panels[idy].length; idx++)
                newPanelLine[idx] = panels[idy][idx];
            newPanelLine[panels[idy].length] = Panel.blackPanel();
            panels[idy] = newPanelLine;
        }
    }

    private void expandPanelsAtTheBottom() {
        final Panel[][] newPanels = new Panel[panels.length + 1][];
        for (int idy = 0; idy < panels.length; idy++) {
            newPanels[idy + 1] = panels[idy];
        }
        newPanels[0] = createEmptyPanelLine();
        panels = newPanels;
    }

    private void expandPanelsToTheLeft() {
        for (int idy = 0; idy < panels.length; idy++) {
            final Panel[] newPanelLine = new Panel[panels[idy].length + 1];
            newPanelLine[0] = Panel.blackPanel();
            for (int idx = 0; idx < panels[idy].length; idx++)
                newPanelLine[idx + 1] = panels[idy][idx];
            panels[idy] = newPanelLine;
        }
        robotPoint.moveRight();
    }

    private Panel[] createEmptyPanelLine() {
        final Panel[] newPanelLine = new Panel[panels[0].length];
        for (int idx = 0; idx < newPanelLine.length; idx++)
            newPanelLine[idx] = Panel.blackPanel();
        return newPanelLine;
    }

    public void turnRobotLeft() {
        switch (robotDirection) {
            case UP:
                robotDirection = Direction.LEFT;
                break;
            case LEFT:
                robotDirection = Direction.DOWN;
                break;
            case RIGHT:
                robotDirection = Direction.UP;
                break;
            case DOWN:
                robotDirection = Direction.RIGHT;
                break;
        }
    }

    public void turnRobotRight() {
        switch (robotDirection) {
            case UP:
                robotDirection = Direction.RIGHT;
                break;
            case LEFT:
                robotDirection = Direction.UP;
                break;
            case RIGHT:
                robotDirection = Direction.DOWN;
                break;
            case DOWN:
                robotDirection = Direction.LEFT;
                break;
        }
    }
}
