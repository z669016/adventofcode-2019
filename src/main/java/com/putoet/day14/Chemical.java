package com.putoet.day14;

import java.util.Objects;
import java.util.regex.Pattern;

public class Chemical {
    private static final Pattern pattern = Pattern.compile("[A-Z]+");
    public static final Chemical ORE = new Chemical("ORE");
    public static final Chemical FUEL = new Chemical("FUEL");
    private final String name;

    public Chemical(String name) {
        if (name != null)
            name = name.trim().toUpperCase();
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("No name");
        }

        if (!pattern.matcher(name).matches())
            throw new IllegalArgumentException("Invalid chemical name " + name);

        this.name = name;
    }

    public String name() { return name; }

    @Override
    public String toString() {
        return name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chemical)) return false;
        Chemical chemical = (Chemical) o;
        return Objects.equals(name, chemical.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
