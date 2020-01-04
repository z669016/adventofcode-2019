package com.putoet.day13;

import com.putoet.day11.MemoryLoader;
import com.putoet.day9.*;

import java.util.List;

public class Day13 {
    public static void main(String[] args) {
        final IMemory memory = MemoryLoader.fromFile("day13.txt");
        final IInputDevice inputDevice = new InputDevice(List.of());
        final Screen screen = new Screen();
        final Processor processor = new Processor(memory, inputDevice, screen);
        try {
            processor.run();
            System.out.println("Number of block tiles is " + screen.count(Tile.BLOCK));
            System.out.println(screen);
        } catch (RuntimeException exc){
            System.out.println("Output so far: " + screen.dump());
            throw exc;
        }
    }
}
