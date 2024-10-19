# A star algorithm (A\* path finder)

### How to run

To compile:

```sh
javac -d build -sourcepath src src/stantardjavaswing/Main.java
```

To open window app:

```sh
java -cp build stantardjavaswing.Main
```

## Features

- Drawing:
  - First left click: source point
  - Second left click: target point
  - Hold left click: Draw maze walls
  - Hold right click: Erase maze walls

- Distance calculation methods:
	- Different calculations might perform better in different mazes.
	- Euclidian distance (General go-to method).
	- Manhattan distance.
	- Grid distance (Custom method based on diagonals).

- Grid size:
	- It is possible to set any grid size.

- Maze import:
	- You can import huge mazes from the internet or create your own.
	- The maze must be a black and white image. A single black pixel represents a wall.
	- The input is the absolute path to the image.

- Debug tools:
	- Perform Path: It will perform the path immediately and open a dialog with the elapsed time.

		<img width="300" alt="image" src="https://github.com/user-attachments/assets/5a63f987-18b3-45db-a258-0beb188d154d">
	- Execute frame: It will execute one single frame, usually helpful with "Show Costs" options checked.

		<img width="300" alt="image" src="https://github.com/user-attachments/assets/997ce49a-6ce3-46b1-92ef-dde63bdbe25f">

## Images

**Overview**
![Overview](https://github.com/user-attachments/assets/0b67974f-00fe-41da-913a-5a7885bec3eb)
