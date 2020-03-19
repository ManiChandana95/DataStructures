import java.util.Random;
import java.util.Scanner;

public class MazeGenerator {

	public static class Maze {
		int rows, columns;
		boolean horizontalW[][], verticalW[][];

		public Maze(int row, int col) {
			rows = row;
			columns = col;

			if (rows > 1) 
			{
				horizontalW = new boolean[columns][rows];
				for (int j = 0; j < rows; j++) 
				{
					for (int i = 0; i < columns; i++) 
					{
						horizontalW[i][j] = true;
					}
				}
			}

			if (columns > 1) 
			{
				verticalW = new boolean[columns][rows];
				for (int i = 0; i < columns; i++) 
				{
					for (int j = 0; j < rows; j++) 
					{
						verticalW[i][j] = true;
					}
				}
			}
		}

		//To print the Maze
		public String toString() {
			int i, j;
			String str = "  ";

			// exit
			horizontalW[columns - 1][rows - 1] = false;

			// Top wall.
			for (i = 0; i < columns - 1; i++) {
				str = str + " _";
			}
			str = str + " \n";

			//whole maze and bottom wall
			for (j = 0; j < rows; j++) {
				str = str + "|";
				for (i = 0; i < columns; i++) {
					if (horizontalW[i][j]) {
						str = str + "_";
					} else {
						str = str + " ";
					}
					if (i < columns - 1) {
						if (verticalW[i][j]) {
							str = str + "|";
						} else {
							str = str + " ";
						}
					}
				}
				str = str + "|\n";
			}
			return str + "\n";
		}
		
         //this function will set an internal wall(t/f)
		public boolean wallRemove(int row, int col, int dir) {
			if (dir == 0) {
				if (horizontalW[row][col] == true) 
				{
					horizontalW[row][col] = false;
					return true;
				} 
				else
					{return false;}
			} 
			else 
			{
				if (verticalW[row][col] == true)
				{
					verticalW[row][col] = false;
					return true;
				}
				else
					{return false;}
			}
		}
	}

	public static void main(String[] args) {
		int numRows, numCols;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter the number of rows for the Maze:");
		numRows = scanner.nextInt();
		while (numRows < 2) 
		{
			System.out.println("Number of rows should be more than 2. Please enter again:");
			numRows = scanner.nextInt();
		} 
		System.out.println("Enter the number of columns for the Maze:");
		numCols = scanner.nextInt();

		while (numCols < 2) 
		{
			System.out.println("Number of columns should be more than 2. Please enter again:");
			numCols = scanner.nextInt();
		}

		Maze maze = new Maze(numRows, numCols);

		System.out.println("The grid with "+numRows+" Rows and "+numCols+" Columns");
		System.out.print(maze);

		DisjSets disjSet = new DisjSets(numRows * numCols);

		int size = numRows * numCols;
		int internal_row_walls, internal_column_walls, cell_one_row, cell_one_column, cell_two_row, cell_two_column;
		int cell_one, cell_two, setFirst, setSecond;

		Random random_1 = new Random();
		Random random_2 = new Random();
        


		// Creating the maze
		while (size > 1) 
		{
			int dir = random_1.nextInt(2);
			if (dir == 0) 
			{
				// Dir = 0 means an internal horizontal wall
				internal_column_walls = random_2.nextInt(numRows - 1);
				internal_row_walls = random_2.nextInt(numCols);


				// Cell one row and column values
				cell_one_column = internal_row_walls + 1;
				cell_one_row = internal_column_walls + 1;


				// Cell two row and column values
				cell_two_column = internal_row_walls + 1;
				cell_two_row = internal_column_walls + 2;


				// Finding the actual cell one and cell two
				cell_one = (cell_one_row - 1) * numCols + cell_one_column - 1;
				cell_two = (cell_two_row - 1) * numCols + cell_two_column - 1;
			} 
			else 
			{
				// create an internal vertical wall dir =1
				internal_row_walls = random_2.nextInt(numCols - 1);
				internal_column_walls = random_2.nextInt(numRows);

				// Cell one row and column values
				cell_one_row = internal_column_walls + 1;
				cell_one_column = internal_row_walls + 1;

				// Cell two row and column values
				cell_two_row = internal_column_walls + 1;
				cell_two_column = internal_row_walls + 2;

				// Finding the actual cellone  and celltwo
				cell_one = (cell_one_row - 1) * numCols + cell_one_column - 1;
				cell_two = (cell_two_row - 1) * numCols + cell_two_column - 1;
			}

			setFirst = disjSet.find(cell_one);
			setSecond = disjSet.find(cell_two);
			if (setFirst != setSecond) {
				// remove the wall
				if (maze.wallRemove(internal_row_walls, internal_column_walls,dir) == true) {
					size--;
					disjSet.union(setFirst, setSecond);
				}
			}
		}
		System.out.println("Maze Created is:(Entry and Exit will be as per above grid");
		System.out.print(maze);
	}
}