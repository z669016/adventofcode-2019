package com.putoet.day13;

import com.putoet.intcode.*;
import com.putoet.resources.CSV;

import java.util.List;

public class Day13 {
    public static void main(String[] args) {
        final List<Long> intCode = CSV.flatList("/day13.txt", Long::parseLong);

        final Memory memory = new ExpandableMemory(intCode);
        final Joystick joystick = new Joystick();
        final Game game = new Game(memory, joystick);

        part1(game);
        System.out.println();
        part2(memory, joystick, game);
    }

    private static void part1(Game game) {
        game.run();
        System.out.println("Number of block tiles is " + game.count(TileFactory.BLOCK));
        System.out.println(game);
    }

    private static void part2(Memory memory, Joystick joystick, Game game) {
        memory.poke(Address.START, 2L);
        joystick.neutral();
        game.run();
        System.out.println(game);
    }
}
