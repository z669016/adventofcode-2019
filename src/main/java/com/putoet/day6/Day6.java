package com.putoet.day6;

public class Day6 {
    public static void main(String[] args) {
        SpaceMap map = SpaceMapLoader.loadMap("spacemap.txt");
        System.out.println(map.objects());
        System.out.println("Orbit count for the spacemap is " + map.orbits());

        System.out.println("The distance from YOU to SAN is " + map.distance(map.get("YOU"), map.get("SAN")));
    }
}
