# Game of Life - Human Computer Interaction Course 2021-2022

The Game of Life is a mathematical simulation developed by Herny Conway in the '70s: the simulation runs on a grid (theoretically infinite) and follows 
3 fundamental rules:

**1) Any live cell with two or three live neighbours survives.**<br>
**2) Any dead cell with three live neighbours becomes a live cell.**<br>
**3) All other live cells die in the next generation. Similarly, all other dead cells stay dead.**<br>

With an initial condition called **Seed**, specified by the player, the original simulation runs without interaction from the user, and shows how the laws of chaos and
unpredictability almost always lead to periodic, recurring or static, configurations.

# Project Development
The project has been developed through Java, with the use of the LIBGDX game engine library which offers a framework to instantiate cameras, draw pixels and sprites, and a 
rudimental Event System based on classic widgets and listeners.

The **first objective** of the project is to offer a -possibly pleasing- way to simulate the original game of life: creating a seed on a grid of variable size, 
then unpausing the simulation to watch how the cells interact with eachother and, possibly, reach a stable state.

The **second objective** of the project is to offer revised, different kinds of simulations: enabling the user to insert different type of cells, each type 
following a distinct set of rules, to experiment and watch different kind of "organisms" interact with eachother in meaningful ways, offering tools to slow or fast forward
the simulation cycles, brush types to create or delete cells while the simulation is paused (**or in real time**), saving and loading mechanisms, and a 
small tutorial for the user to read about commands and shortcuts. 

# Features
The application greets the user with this screen: 

![game intro](https://user-images.githubusercontent.com/87710818/164039835-b0fbe872-abb6-4076-a330-90f7650f0c3b.JPG)

Pressing any key to continue, the main menu is shown: the user might create a new simulation -specifying the grid size-, load a previously saved simulation, or review the tutorial.

![game options](https://user-images.githubusercontent.com/87710818/164039877-ede5ec61-bfce-48c1-8367-ab0990079e4c.JPG)

Choosing to create a new simulation or load a saved one directs to the simulation screen:

![game](https://user-images.githubusercontent.com/87710818/164039895-429d18aa-6df6-4b27-85ab-9aace89aaad6.JPG)

The user can use the mouse left button to place cells and the right button to delete them (press and hold movements are enabled).<br>
Arrow keys on the keyboard can be used to pan the camera. <br>
From top left to top right, the HUD enables the user to clear the grid or go back to the menu.<br>
From bottom left to bottom right, the user may click the buttons to pause/unpause the simulation, 
toggle the camera zoom, select which brush type to use, the brush size, the simulation speed and to save the current simulation.

# Cell Types

![greenCellTexture](https://user-images.githubusercontent.com/87710818/164040994-be430407-0d00-414c-8ba2-c156cd41c559.JPG)
 **Green cell type**: also called Normal, is the typical cell represented in the original Game of Life. It follows the 3 fundamental rules, and is generally happy to live. 

![redCellTexture](https://user-images.githubusercontent.com/87710818/164041020-725a5217-0f52-472b-a1bf-51edbcf11967.jpg)
 **Red cell type**: also called Corruptor, is an aggressive version of the Normal cell: it follows the 3 fundamental rules, but every other cell in the vicinity (either alive or about to reproduce) is converted into a red one.

![yellowCellTexture](https://user-images.githubusercontent.com/87710818/164041051-fe977b66-550b-4259-8a3e-0086b4c67838.JPG)
 **Yellow cell type**: also called Immortal, is a cell that doesn't respect the 3 fundamental rules: it cannot die and does not need to reproduce, but may count as a neighbour for other types of cells. 

# Instructions to test and review

For code reviewing purposes, the application classes can be found inside the **Core** folder from the root of the project.

For application testing, a runnable .jar file called **GameOfLife.jar** can be found in the **Runnable** folder from the root of the project, with the **data/saves folders required for loading and saving simulations.** <br>
**Note: the application has been tested on multiple devices running Windows OS, with Java 9 Runtime Environment as the only dependency.**([JRE 9](https://www.oracle.com/it/java/technologies/javase/javase9-archive-downloads.html))


Leonardo Paroli, April 2022
