//package com.putoet.day11;
//
//import com.putoet.day9.IMemory;
//import com.putoet.day9.Memory;
//import com.putoet.resources.ResourceLines;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class MemoryLoader {
//    public static IMemory fromFile(String fileName) {
//        final List<Long> intCode = new ArrayList<>();
//        ResourceLines.stream(fileName)
//                .map(line -> line.split(","))
//                .forEach(list -> Arrays.stream(list).forEach(l -> intCode.add(Long.valueOf(l))));
//        return Memory.ofLongList(intCode);
//    }
//}
