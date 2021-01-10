//package com.putoet.day13;
//
//import com.putoet.day11.MemoryLoader;
//import com.putoet.day9.*;
//
//import java.util.List;
//
//public class Day13 {
//    public static void main(String[] args) {
//        IMemory memory = MemoryLoader.fromFile("/day13.txt");
//        IInputDevice inputDevice = new InputDevice(List.of());
//        Screen screen = new Screen();
//        Processor processor = new Processor(memory, inputDevice, screen);
//        try {
//            processor.run();
//            System.out.println("Number of block tiles is " + screen.count(Tile.BLOCK));
//        } catch (RuntimeException exc) {
//            System.out.println("Output so far: " + screen.dump());
//            throw exc;
//        } finally {
//            System.out.println(screen);
//        }
//
//        System.out.println();
//
//        memory = MemoryLoader.fromFile("/day13.txt");
//        memory.poke(Address.START_ADDRESS, 2L);
//        Joystick joystick = new Joystick();
//        screen = new Screen();
//        processor = new Processor(memory, joystick, screen);
//        try {
//            processor.run();
//            System.out.println("Number of block tiles is " + screen.count(Tile.BLOCK));
//        } catch (RuntimeException exc) {
//            System.out.println("Output so far: " + screen.dump());
//            throw exc;
//        } finally {
//            System.out.println(screen);
//            System.out.println(joystick.dump());
//        }
//    }
//}
