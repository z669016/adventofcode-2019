package com.putoet.day5;

import java.util.Optional;

public interface IInstruction {
    int size();

    Operation operation();

    Optional<Address> execute(Address ip, Memory memory, InputDevice inputDevice, OutputDevice outputDevice, Integer[] operants);
}
