# Advent of code 2019

I started this AoC when it had  already ended (end of December 2019), and decided to apply lessons from the 
**Objects Design Style Guide** (c) Manning.com - 2019. At some point I got stuck, tried again, read some more books,
and left it. Early 2021, I decided to give it another try, but now also apply lessons frm other years (including 2020 
which I was able to finish).

## Convenience methods and classes
I have a small library with some convenience methods used for other AoC exercises. Like the ```ResourceLines``` class
to read a resource file and transform the content into a ```List<String>```, or the CSV class to read a resource
file containing comma separated values and returning a List of these values, optionally after transformation from
```String``` to ```Integer```.

Also uses the algorithms library, which contains generic classes for addressing classic compute problems (from the book
**Classic Computer Science Problems In Java** (c) Manning.com - 2020)

It was never my intention to create the shortest program possible. I did try to create clear and simple implementations.

## Day 1
Pretty straight forward: parse the stream, do some math (using type save '''Mass''' and '''Fuel''' classes).

## Day 2
Based on learnings from other years and knowing the IntCodeDevice would be heavily reused, I decided to put those 
reusable classes in a generic package (intcode) to be enhanced later. The original approach with the classes that 
implemented the operations was way too complex.

## intcode
An '''Address''' class is used to represent a memory address. 

The '''Memory''' interface defines a '''peek(Address)''' and '''poke(address long value)''' mwthod. This interface is 
implemented by '''FixedMemory''' class (with fixed length) and '''ExpandableMemory''' which grows when the peek or poke 
method references an address out of range (beyond '''Memory.size()''').

The '''Instruction''' interface defines methods on an Instruction instance like '''opcode()''' (to obtain the 
intructions opcode), '''size()''' (which returns the length of the instruction, i.e. number of used memory positions), 
and '''run()''' (whoch runs the instruction. The '''Interpreter''' creates an instruction instance for the operation at 
a specific memory address. The factory menthod '''interpret(Address, Memory)''' will throw an exception when it 
encounters an invalid (unknown) opcode at the specified memory location.

The '''IntCodDevice''' does the actual work. It takes a '''Memory''' instance on creation, and the '''run()''' method, 
interprets all instructions and executes them (unless it encounters an exit instruction (opcode 99)). In between two  
runs you cannot reset the memory, so be carefull.