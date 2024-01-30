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
```RepairDroid``` runs an IntCode program. It implements ```InputDevice``` and ```OutputDevice``` to get the data. The
```IntCodeDevice``` of the ```RepairDroid``` must stop when no input is available, and be able to resume later. This 
requires some refactoring on the ```IntCodeComputer``` developed so far, but it enables the use of the generic bfs() 
of the algorithms project.

The objective of the ```RepairDroid``` is to find all non-open spaces in the maze (which includes walls and the oxygen
system). This allows the creation of a ```Grid``` to solve the actual problem.

The starting point for the ```RepairDroid``` search is at ```Point.ORIGIN```. The next states will be 4 different 
from there (add NORTH, SOUTH, EAST, and WEST). The success state will have the value 0 or 2 as it's last output value. 

Once the Grid is available, it can be used for a ```bfs()``` for the oxygen system (part 1), or a ```findAll()``` for
all routes to a wall and then just select the longest route (part 2). The ```GridSearch``` is a simple helper class 
for the actual search and the search state.

## Day 16
This exercise was mean ... For part 1, I started with a ```Signal``` class that just implemented the required algorithm. 
Pretty  straight forward nothing difficult. Part 2 of course could not be solved reusing the same algorithm, I 
implemented an optimized ```FasterSignal``` that skips calculations for zeros in the pattern. Unfortunately that still 
doesn't do the trick.

After reading some Reddit posts, I realized that the offset was part of the original message, so there was no need to 
calculate the numbers on the first part of the signal (everything before the offset), also because according to the 
pattern the numbers before the offset should be multiplied with 0. All the numbers after the offset should be 
multiplied with 1, which means you can fill new data from the end just by summing up the values (which would prevent 
looping over all remaining values as well).  

## Day 17
The ```VacuumRobot``` is used to run the int program (puzzle input). One constructor only takes an int code program and
it produces a scan of the scaffolds. The ```Calibrator``` takes the map and does the calibration (part 1), and creates 
the route from the map (the map is represented by a ```Grid```).

The``` Route``` class takes the route, and splits it into a main routine, and three functions as described in the 
assignment. The route is split by searching for the longest repeating piece of route (max sublist is 10 commands, 
otherwise it could exceed the 20 characters). 

The second constructor of the ```VacuumRobot``` takes an int code program and a route. This constructor sets the first 
instruction to 2, and sets up the input device with the routes main routine and functions A, B, and C, and no contiuous 
video feed to solve part 2.

## Day 18
Started with a ```KeyMap``` class that represents the maze using a grid. It provides some convenience methods to get 
all the keys, the doors, the entrance, etc. It also provides a method to get the shortest paths to available keys from 
the current location given a set of available keys (using a bfs). Then an ```Explorer``` class also performs a bfs to 
find the shortest path to collect all keys. In order to make that one perform, states that have already been evaluated
and have proven shorter are pruned (a state contains a current location, a set of collected keys, and a number of 
steps). this solves part 1, not blazingly fast but with acceptable performance (less than 2.5 seconds).     

Part 2 uses the same algorithm, but with a different state. However, first a ```SplitKeyMap``` is created from the 
```KeyMap```. Then the MultiMapExplorer searches for the shortest route, where the state now contains a current 
location for each section of the map. So the structure is a little different, but the algorithm itself is basically 
the same.  

## Day 19
The ```Drone``` class takes an int code program and is able to determine the state for a coordinate (```Point```) by 
running the program and providing the X and Y coordinate as input values. The very first test run showed, you need to 
initialize the program memory for each run, as the program throws an error when you try to run it again reusing the 
state (memory) it had after a previous run. The ```TractorBeamMap``` class creates a 50x50 grid and fills all the cells
on the grid with the output from the drone. Not the fastest solution but it works and can be easily visualized.

Part 2 needs a bit of more work, as the sheer number of elements in the grid to contain a 100x100 block is probably 
be too large. I created a ```TractorBeamSearch``` that performs a search for the location of the block. It starts the 
search at the first line for which the beam at least 100 units wide. The initial estimation for that line is based on 
data from part one (the angle for the lower and upper beam lines of line 35). Then just move one line down until the 
line contains the top of the square. The spead of the search can probably be improved but the runtime was okay(ish).

## Day 20
Started to address this challenge with the ```DonutMaze``` class, that provides some helpful methods for this 
particular weird grid. It collects the labels and caches the from-to values per label, it can identify  a label and get 
the entry and exit points for the maze.  Basically the stuff you need to create methods to run a BFS on the maze.

The ```DonutMazeExplorer``` contains a ```shortestRoute()``` method that uses BFS to find the exit using a Point as 
state for the search. The ```successors(Point point)``` method simply transports to the other end of the gate when the 
next point is a label. 

The recursive part is just as straight forward, now with a ```RecursiveDonutMazeExplorer```. The search through the 
maze now uses a ```Pair<Integer,Point>``` as its state (which is the level, and the current location). The init starts
at level 0, the success is at the exit point only if current level is 0, and the ```successors()``` method takes the 
level into account (moving through an inner circle gate will reduce the level with 1, moving through an outer gate
will increase the level with 1), and that's basically it. 

So, fundamentally the two explorers are identical. This exercise looked more difficult than it actually was.  

## Day 21
Not my kind of puzzle. The ```SpringDroidProgrammingDevice``` class creates an IntCode ASCII computer. It takes a 
```List<String>``` feeds that into the input, runs the program and transforms the output data again into a list of 
strings. While trying different combinations of spring-instructions, the output of the program returned different 
pictures, so the damage the droid needed to navigate through depended on the input. At that point I decided to search 
Reddit for the answer, as I didn't want to run some kind of brute force search for all possible combinations of 
instructions. As I said, not my kind of puzzle.

## Day 22
My knowledge of math definitely wasn't good enough to solve this myself. For part one I started with an array of 
integers, and it worked very well. For part 2, I started on a new version that only tracked the position of one number
instead of a complete array, but even then the numbers are way too high, and the answer is not on a position of a 
card after shuffles but of a card at some position.

The math behind this is modular arithmetic, 
read [Modular Arithmetic for Beginners](https://codeforces.com/blog/entry/72527) for a good introduction. There is also
a [Tutorial for Advent of Code 2019 day 22 part 2](https://codeforces.com/blog/entry/72593) which describes the details
on how the solution works.

## Day 23
What a tremendously fun puzzle this time, a great opportunity to play around with threads and locks.

The ```NetworkInterfaceController``` (NIC) basically creates an ```IntCodeDevice```and the NIC can be run on a separate 
thread. The ```Network``` creates 50 NIC's and connects them to a dedicated ```SynchronizedInputDevice``` and 
```SynchronizedOutputDevice```. When the network is started, it creates a separate thread for each NIC, and it also 
runs itself on a separate thread. The network itself is a simple dispatcher which loops over all output 
devices, takes a value and (when a value was available) dispatches the packet to the appropriate input for the 
requested destination. The network instance saves the first package with an invalid address (0 <= address < 50). 

The synchronized input and output devices have been made thread safe using a ```ReentrantLock```, and both offer and 
poll complete packets, so the network doesn't have to handle partial packets. 

A ```NotAlwaysTransmit``` (NAT) class which also runs on a separate thread, can be connected to the network, and 
when present, will receive the invalid packages found by the network. The NAT loops and only when it finds the network
idle (all input devices are empty), and it has previously received an invalid packet, then it forwards that packet to
the input of device 0 (after which the last received invalid packet is set to null again). The NAT has been made thread 
safe using a ```ReentrantReadWriteLock```. If the NAT find a second consecutive packet with the same Y value for the 
first time, then that packed is stored. 

Probably not the fastest possible solution, but definitely fun to implement. 

## Day 24
Part 1 is straight forward reusing the ```Grid``` and ```Point``` that has been used before. Using the static field 
```NEIGHBOURS```(```List.of(Point.NORTH, Point.EAST, Point.SOUTH, Point.WEST)```) you can simply get all adjacent 
points in the grid using ```NEIGHBOURS.stream().map(current::add).collect(Collectors.toList())```. You can then count
adjacent bugs by ```adjacent.stream().filter(p -> grid.contains(p.x(), p.y())).filter(p -> grid.get(p.x(), p.y()) == BUG).count()```
All is handled within the ```Eris``` class.

For part 2 a different approach is required, so I introduced ```RecursiveFoldedEris``` which stores a contains a map of 
grids with the level as the map key (```Map<Integer,Grid>```). Now points outside the grid should no longer be ignored 
but be substituted by a list of points in the adjacent grid (o level up or down, depending on the current point being 
ckecked). With each next minute, you also need to check if the map should be expanded up or down (as the outer grids in 
the map can cause bugs to spread into new grids not yet in the map).

## Day 25
This looks like a game ... the ```SyncCommandInputDevice``` and ```SyncCommandOutputDevice``` are input and output 
devices for the intcode computer (they take commands and provide them as characters ended with a newline character to 
the droid). As the Droid will be running asynchronously the devices use locks. The Droid uses a resumable intcode 
computer that is started on a separate thread. If no input is available, it just waits a while, and retries. 

The ```Game``` class implements the game and can be run separately (using the ```main(...)``` method), or it can be 
used to ```play(...)``` a list of commands returning the output.

The ```play(...)``` method benefits from feature that a series of commands can be offered to the program without waiting 
for the output, as the output is only added to and not generated for each individual command processed.  

From playing the game manually, a few specifics playing rules are known:
* You can find the access code by entering the ```Pressure-Sensitive Floor``` next to ```Security Checkpoint``` room
* You are accepted on the ```Pressure-Sensitive Floor``` if have the right combination of items in your inventory
* The wrong combination of items will result in being transported back into the ```Security Checkpoint```
* The ```infinite loop``` item must be avoided, for it will cause an infinite loop in the program when it is added to your the inventory

The main program (```Day25```) reads the intcode program, and searches for the access code. The search actually finds 
possible paths using the doors while picking up all items it finds along the way. 
The ```Location``` record extracts the last location and the available doors and items at that location/room (removing 
the items already picked up). The ```State``` record is used to keep track on the visited rooms/states. 
```State.commands()``` returns the commands to get to the current state, while ```take(...)```, ```drop(...)```, and 
```move(...)``` return possible next states without duplicates (so, it checks if a command is already in the current 
command-chain). The State.enter(...) method is similar to ```State.move(...)``` but it doesn't checks for duplicates
(which is required to access the ```Pressure-Sensitive Floor``` with different combinations of items).
When it enters the ```Security Checkpoint``` it will try all possible (not yet tried) combinations of items in your 
inventory. In order to do so, a priority queue used for the search, which prioritizes any step from the 
```Security Checkpoint```over steps on any other room, and shorter command chains are prioritized over longer ones (so 
it will be a BSF).  

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