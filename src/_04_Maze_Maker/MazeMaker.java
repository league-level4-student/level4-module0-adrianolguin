package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeMaker {

	private static int width;
	private static int height;

	private static Maze maze;

	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();

	public static Maze generateMaze(int w, int h) {
		width = w;
		height = h;
		maze = new Maze(width, height);

		// 4. select a random cell to start
		Cell randomCell = maze.getCell(randGen.nextInt(maze.getWidth() - 1), randGen.nextInt(maze.getHeight() - 1));
		// 5. call selectNextPath method with the randomly selected cell
		selectNextPath(randomCell);

		Cell randomCell1 = maze.getCell(0, randGen.nextInt(maze.getHeight() - 1));
		Cell randomCell2 = maze.getCell(maze.getWidth() - 1, randGen.nextInt(maze.getHeight() - 1));

		randomCell1.setWestWall(false);
		randomCell2.setEastWall(false);

		return maze;
	}

	// 6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		// A. mark cell as visited
		currentCell.setBeenVisited(true);
		// B. Get an ArrayList of unvisited neighbors using the current cell and the
		// method below

		ArrayList<Cell> unvisitedNeighbors = getUnvisitedNeighbors(currentCell);
		System.out.println("Number of UnvisitedNeighbors: " + unvisitedNeighbors.size());

		// C. if has unvisited neighbors,
		if (unvisitedNeighbors.size() > 0) {
			System.out.println("Unvisited Neighbors > 0");
			// C1. select one at random.

			int selectedCellIndex;

			if (unvisitedNeighbors.size() - 1 < 1) {
				selectedCellIndex = 0;
			} else {
				selectedCellIndex = randGen.nextInt(unvisitedNeighbors.size() - 1);
			}

			System.out.println("selected cell index: " + selectedCellIndex);
			Cell selectedCell = unvisitedNeighbors.get(selectedCellIndex);

			// C2. push it to the stack
			uncheckedCells.push(selectedCell);

			// C3. remove the wall between the two cells
			removeWalls(currentCell, selectedCell);

			// C4. make the new cell the current cell and mark it as visited
			currentCell = selectedCell;
			currentCell.setBeenVisited(true);

			// C5. call the selectNextPath method with the current cell
			selectNextPath(currentCell);
		} else if (unvisitedNeighbors.size() == 0) {
			System.out.println("Unvisited Neighbors = 0");

			// D. if all neighbors are visited
			// D1. if the stack is not empty
			if (uncheckedCells.size() > 0) {

				// D1a. pop a cell from the stack
				Cell poppedCell = uncheckedCells.pop();

				// D1b. make that the current cell
				currentCell = poppedCell;

				// D1c. call the selectNextPath method with the current cell
				selectNextPath(currentCell);

			}
		}
	}

	// 7. Complete the remove walls method.
	// This method will check if c1 and c2 are adjacent.
	// If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {

		System.out.println("Removing Walls between cell (" + c1.getX() + ", " + c1.getY() + ") and cell(" + c2.getX() + ", " + c2.getY() + ")");

		float dist = (float) Math.sqrt(Math.pow((c1.getX() - c2.getX()), 2) + Math.pow((c1.getY() - c2.getY()), 2));
		if (dist == 1) {

			if (c1.getX() - c2.getX() == -1) {
				c1.setEastWall(false);
				c2.setWestWall(false);
			}
			if (c1.getX() - c2.getX() == 1) {
				c1.setWestWall(false);
				c2.setEastWall(false);
			}
			if (c1.getY() - c2.getY() == -1) {
				c1.setSouthWall(false);
				c2.setNorthWall(false);
			}
			if (c1.getY() - c2.getY() == 1) {
				c1.setNorthWall(false);
				c2.setSouthWall(false);
			}

		}
	}

	// 8. Complete the getUnvisitedNeighbors method
	// Any unvisited neighbor of the passed in cell gets added
	// to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		System.out.println("Getting unvisited neighbors...");

		ArrayList<Cell> unvistedNeighbors = new ArrayList<Cell>();

		if (c.getX() != 0 && !maze.getCell(c.getX() - 1, c.getY()).hasBeenVisited()) {
			unvistedNeighbors.add(maze.getCell(c.getX() - 1, c.getY()));
		}

		if (c.getX() != maze.getWidth() - 1 && !maze.getCell(c.getX() + 1, c.getY()).hasBeenVisited()) {
			unvistedNeighbors.add(maze.getCell(c.getX() + 1, c.getY()));
		}

		if (c.getY() != 0 && !maze.getCell(c.getX(), c.getY() - 1).hasBeenVisited()) {
			unvistedNeighbors.add(maze.getCell(c.getX(), c.getY() - 1));
		}

		if (c.getY() != maze.getHeight() - 1 && !maze.getCell(c.getX(), c.getY() + 1).hasBeenVisited()) {
			unvistedNeighbors.add(maze.getCell(c.getX(), c.getY() + 1));
		}

		return unvistedNeighbors;
	}
}
