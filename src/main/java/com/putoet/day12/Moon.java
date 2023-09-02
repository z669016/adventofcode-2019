package com.putoet.day12;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

class Moon {
    private final String name;
    private Position position;
    private Velocity velocity = new Velocity(0, 0, 0);

    public Moon(@NotNull String name, @NotNull Position position) {
        this.name = name.trim();
        this.position = position;
    }

    public static Moon of(@NotNull String name, @NotNull String coordinates) {
        return new Moon(name, Position.of(coordinates));
    }

    public Position position() {
        return position;
    }

    public Velocity velocity() {
        return velocity;
    }

    public void applyGravity(@NotNull Map<String, Moon> moonMap) {
        moonMap.values().forEach(moon -> velocity = velocity.applyGravity(position, moon.position));
    }

    public void applyVelocity() {
        position = position.applyVelocity(velocity);
    }

    public int potentialEnergy() {
        return position.energy();
    }

    public int kineticEnergy() {
        return velocity.energy();
    }

    public int totalEnergy() {
        return potentialEnergy() * kineticEnergy();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Moon moon)) return false;
        return Objects.equals(name, moon.name) && Objects.equals(position, moon.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, position);
    }

    @Override
    public String toString() {
        return String.format("%s %s pot=%d %s kin=%d energy=%d", name, position, potentialEnergy(), velocity, kineticEnergy(), totalEnergy());
    }
}
