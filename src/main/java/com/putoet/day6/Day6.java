package com.putoet.day6;

public class Day6 {
    public static void main(String[] args) {
        SpaceMapLoader.loadMap("spacemap.txt");
        System.out.println(SpaceMap.MAP.objects());
        System.out.println("Orbit count for the spacemap is " + SpaceMap.MAP.orbits());

        System.out.println("The distance from YOU to SAN is " + SpaceMap.MAP.distance(SpaceMap.MAP.get("YOU"), SpaceMap.MAP.get("SAN")));
    }
}
