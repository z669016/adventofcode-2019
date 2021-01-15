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
Pretty straight forward: parse the stream, do some math (using type save ```Mass``` and ```Fuel``` classes).

## Day 2
Based on learnings from other years and knowing the IntCodeDevice would be heavily reused, I decided to put those 
reusable classes in a generic package (intcode) to be enhanced later. The original approach with the classes that 
implemented the operations was way too complex.

## Day 3
Based on the description I decided to create a ```Route``` from the route description (puzzle input) containing all 
points visited. The intersection contains all the points which are on both routes, and those can be easily determined 
using a filter. By reusing the ```Point``` class from the aoc project it's pretty easy.

The processing of the puzzle input (constructing the routes, and find the intersections) takes a while (as both routes 
contain about 150.000 steps). A faster algorithm would only record the locations of the turns in the route, which would 
make the data set way smaller, but the calculation of the intersections a bit harder. 

## Day 4
Created two generators (one for part 1 and another for part 2) which implement ```Iterator<String>``` to generate valid 
the passwords (codes) based on the provided min and max (puzzle input). The number of genarated values is counted 
using the ```Iterator.forEachRemaining(Consumer<>)``` method, where the consumer only updates a simple counter to 
obtain the answer.

## Day 5
Refactored the ```IntCodeDevice``` and its implementation ```IntCodeComputer``` to handle the input and out put 
instructions, by using a ```Queue<Long>``` as input and output device. Added the comparison and jump instructions 
similar  to all the other instructions. Now solving part 1 and two is simple. I deleted all the specific IntCode 
classes originally developed for this puzzle.

## Day 6
Collect info about space objects in orbit of other space objects ... The ```SpaceObject``` class models a space object 
(which has a name, and another space object as its center), and has some convenience methods to find the route to the 
absolute center (a space object called COM), or the number of steps to take towards another space object on the 
route towards the center.

The ```SpaceMap``` contains a map of all the space objects and methods to count the total number of orbits, and the 
distance between two arbitrary space objects on the map.

The ```SpaceMapLoader``` loads the puzzle input and creates the ```SpaceMap```.

## Day 7
Due to deleting the IntCode classes of day5, made it necessary to also refactor day 7 (as it reused the day5 classes). 
I changed the ```Queue<Long>``` of the input device into a ```BlockingDeque<Long>```, so it was able to wait for a 
value to become available. I've added  the ```IntCodeDevice``` to the ```Interpreter``` as a property, as the number of
properties needed for executing instructions was increasing. This required all static methods to become non-static, and 
a ''Builder'' as part of the IntCodeComputer to limit the amount of constructors for creating different configurations
of an ```IntCodeDevice```.

The AmplifierArray now became quite simple to implement using independent threads. The ```SimpleAmplifierArray``` uses 
six queues, where the last one is the utput queue of the fifth amplifier. The ```FeedbackAmplifierArray``` uses five 
queues, where the output queue of the fifth amplifier is set to the input queue of the first one. I added a 
```CountDownLatch``` to the Interpreter, so it was able to signal to any other waiting thread, that it was finished. 

I reused the ```Permutator``` from my AOC project for the creation of the possible ```PhaseSetting```.

IMHO all in all quite fancy, and yet simple and straight forward.

## Day 8
An ```Image``` has a specific ```Size```, and a number of ```Layer```s. To create an ```Image``` fro the puzzle input, 
simply split the list of integers into sub-lists based on the size of the image.

For part 1, just stream over the layers, and find the one with the minimum count for the specified pixel value (0 in 
my puzzle). The ```Layer``` class contains a ```count(int pixelValue)```  method for that.

For decoding an image, for each pixel, move down and reduce to the first non-2 value encountered.

## Day 9
Added relative mode to the ```Interpreter``` to solve part 1 and part 2. 

## Day 10
A nice challenge for day 10. The Asteroid class models a single asteroid at a specific location (reused the Point class 
from my the AoC project). The SpaceArea creates a ```Map<Point,Asteroid>``` and provides some convenience methods, for 
instance to create ```LineOfSightMap```s, which contain all ```LineOfSight```s for a single Asteroid (an entry for each 
direction in which other Asteroids are visible in a line-of-sight). A line-if-sight is identified by a Vector (which 
represents) the direction in which the other asteroids are positioned from the point of the origin of the 
line-of-sight.

Using these structured puzzle input classes, it's a piece of cake to solve part 1 and 2 for this day. 

## Day 11
The idea is that a robot running an IntCode program navigates across a surface to paint panels, so I started with
a simple ```Panel``` class, and an extensible ```Surface``` that "grows" as soon as the robot navigates across its 
boundary (in any direction). The ```Panel``` class remembers if it has been painted of not, and the ```Surface``` 
keeps track of the position of the robot. 

The ```InputDevice``` is a ```Camera``` that returns a value for the color of the panel at which the robot is 
positioned at that point in time. The ```Painter``` is the ```OutputDevice``` for the ```IntCodeDevice```, and on 
instruction of the int code computer, it paints a panel on the surface and moves the robot to the next position. 

For part 1, run the intcode program, and count the number of painted panels on the surface. For part two, first 
paint the starting panel, than run the int code program and then print/visualize the surface.

## Day 12
A ```Moon``` class helps to keep track of the individual moons. A moon has a name, a position, and a velocity. The 
```Position``` and ```Velocity``` classes do the simple math of applying gravity and velocity to the position, and 
calculating the energy. The ```MoonMap``` class loads the puzzle input in a map.

Part 1 is straight forward, just apply the rules 1000 and sum the total energy in the system. Part 2 however is more 
nasty. Yes, the trick is in finding repetition, but the repetition is not in an individual moons' position and 
velocity, but in the combination of the X-values, the Y-values and Z-values for position and velocity of all moons at 
a point in time. When you found the repetition in each of these separately,you can calculate the moment of repetition 
by finding the least-common-multiple for the three values. 

## Day 13
Again we have an int code computer game. No changes to the computer are required though. A ```Game``` class contains 
the man functionality. The game object creates and runs the int code computer program (using itself as the 
```OutputDevice```), and processes the output. The game uses an ```ExtendableSurface``` (which is basically a grid of 
```Tile``` objects that grows if a point is set that's off the grid), which gets updated based on the output.
A ```Joystick``` class is being used as ```InputDevice``` and depending on the x-axis of the ball compared to the x-axis
of the paddle, the joystick returns -1 (move left), 0 (neutral), or +1 (move right) to the int code device.

That IntCodeDevice is really an amazing piece of hardware ...

## Day 14
This feels like a puzzle which requires a straight forward but step-by-step approach. From the description, I've 
identified a ```Chemical``` (e.g. ORE, or FWMGM), an ```Ingredient``` (which is a certain amount of one chemical, e.g. 
1 STKFG) and input chemicals and output chemicals are ingredients by itself, and finally a ```ChemicalReaction```. The 
first step is to construct the nanofactory which contains a list of chemical reactions from the puzzle input. As there 
is at most one chemical reaction to create another chemical, the reactions  are simply stored in a 
```Map<Chemical,ChemicalReaction>```.

For part 1, take the chemical reaction for the chemical FUEL and simplify it. To symplify the chemical reaction, 
just keep replacing chemicals with their chemical reaction that creates them, until you only have ORE's left. Each time
You replace a chemical that has no dependencies on any of the other chemicals in the ingredient list and after 
replacement you combine the ingredients with identical chemicals. Repeat that step until there is nothing to replace 
(and only ORE will be left). 

For solving part 2, you take the minimal amount of ORE for 1 fuel, calculate the max amount fuel you can make from it. 
As there is quite some waste, you then simplify that max amount of fuel reaction back to ORE. The amout of ORE actually
used will be less than you originally estimated (due to waste), so you calculate the amount of ORE left , and for that 
rest of ORE, redo this process until the remainder of ORE is not enough to create one more fuel.   

All in all not too difficult but it does require a careful breakdown of the process in small steps.

## Day 15
Part 1 must be a BFS (breadth first search) problem, but now using an IntCodeDevice as part of the state of the search
algorithm.

RepairDroid runs an IntCode program. It implements InputDevice and OutputDevice to get the data. The IntCodeDevice of 
the RepairDroid must stop when no input is available, and be able to resume later. This requires some refactoring
on the IntCodeComputer we developed so far, and it enables the use of the generic bfs() of the algorithms project.

The starting state is at Point.ORIGIN, and an empty set of visited locations. The next states will be 4 different 
commands and point.ORIGIN added to the set of visited places. Even dead-ends are recorded as visited, but those Node 
paths will stop and will not be explored any further. The success state will have the value 2 as it's last 
output value. The puzzle answer is the length of the Node path.




## intcode
An ```Address``` class is used to represent a memory address. 

The ```Memory``` interface defines a ```peek(Address)``` and ```poke(address long value)``` method. This interface is 
implemented by ```FixedMemory``` class (with fixed length) and ```ExpandableMemory``` which grows when the peek or poke 
method references an address out of range (beyond ```Memory.size()```).

The ```Instruction``` interface defines methods on an Instruction instance like ```opcode()``` (to obtain the 
instructions' opcode), ```size()``` (which returns the length of the instruction, i.e. number of used memory positions), 
and ```run()``` (which runs the instruction). 

The ```Interpreter``` implements ```Iterator<Instruction>``` that enables you to iterate over the instructions of the 
program using the instruction pointer. 

The ```IntCodDevice``` does the actual work. It takes a ```Memory``` instance on creation, and the ```run()``` method, 
interprets all instructions and executes them (unless it encounters an exit instruction (opcode 99)). In between two  
runs you cannot reset the memory, so be carefully.