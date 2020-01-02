package com.putoet.day11;

import com.putoet.day9.IMemory;
import com.putoet.day9.Memory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemoryLoader {
    public static IMemory fromFile(String fileName) {
        try {
            final List<Long> intCode = new ArrayList<>();
            Files.lines(Paths.get(fileName))
                    .map(line -> line.split(","))
                    .forEach(list -> Arrays.stream(list).forEach(l -> intCode.add(Long.valueOf(l))));
            return Memory.ofLongList(intCode);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot read file " + fileName);
        }
    }
}
