package com.putoet.day9;

public interface IProcessor extends Runnable {
    IMemory memory();

    IInputDevice inputDevice();

    IOutputDevice outputDevice();

    void enableLog();

    void disableLog();
}
