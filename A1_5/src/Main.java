import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
	public static void main(String args[]) throws org.json.simple.parser.ParseException {

		System.out.println("Welcome to the Maze Generator and Solver from the A1-5 Group.");
		System.out.println("Pick an option: \n" + "\n" + "A for creating a random maze. \n"
				+ "B for reading a file that contains a maze. \n" + "E for exiting the application. \n");
		String input;
		Scanner readinput = new Scanner(System.in);
		input = readinput.nextLine();
		boolean chooseanoption = false;
		while (!chooseanoption)
			if (input.contentEquals("A") || input.contentEquals("a")) {
				chooseanoption = true;
				Maze maze;
				System.out.println(
						"Choose two numbers, the first one will be the number of rows and the second the number of columns.");
				input = readinput.nextLine();
				int rows = 0;
				boolean chooseanumber = false;
				while (!chooseanumber) {
					if (input.matches("\\d+")) {
						rows = Integer.parseInt(input);
						System.out.println("The number of rows are " + rows);
						chooseanumber = true;
					} else {
						System.out.println("You didn't choose a number, try again!");
						input = readinput.nextLine();
					}
				}
				int cols = 0;
				chooseanumber = false;
				input = readinput.nextLine();
				while (!chooseanumber) {
					if (input.matches("\\d+")) {
						cols = Integer.parseInt(input);
						System.out.println("The number of cols are " + cols);
						chooseanumber = true;
					} else {
						System.out.println("You didn't choose a number, try again!");
						input = readinput.nextLine();
					}
				}

				maze = createMaze(rows, cols);
				// El resultado del wilson se printea en un jpg.
				writeJson(maze);
			} else if (input.equals("B") || input.contentEquals("b")) {
				chooseanoption = true;
				System.out.println("Please, write the name of the file that you want to read.");
				input = readinput.nextLine();
				boolean choosefile = false;
				while (!choosefile) {
					try {
						Maze mazeread = readJson(input);
						// El resultado se printea en un jpg.
						writeJson(mazeread);
						choosefile = true;
						System.out.println(
								"The filename was correct, the maze has been written on the example.json file!");
					} catch (Exception e) {
						System.out.println("The name of the file wasn't correct, please try again");
						input = readinput.nextLine();
					}
				}
			} else if (input.equals("E") || input.contentEquals("e")) {
				chooseanoption = true;
				System.out.println("Hope to see you soon!");
				System.exit(0);
			} else {
				System.out.println("Choose between A, B or E!");
				input = readinput.nextLine();
			}

	}

	public static void writeJson(Maze maze) {
		JSONObject mazeobject = new JSONObject();

		JSONArray id_mov = new JSONArray();
		JSONArray movs = new JSONArray();
		for (int i = 0; i < 4; i++) {
			JSONArray mov = new JSONArray();
			mov.add(maze.getMovs()[i][0]);
			mov.add(maze.getMovs()[i][1]);
			id_mov.add(maze.getId_mov()[i]);
			movs.add(mov);
		}

		JSONObject finalcells = new JSONObject();
		JSONObject almostcells = new JSONObject();

		Cell[] auxcells = new Cell[maze.getCells().length];
		auxcells = maze.getCells();
		for (int j = 0; j < (maze.getRows() * maze.getCols()); j++) {
			JSONObject individualcell = new JSONObject();
			JSONArray neighbors = new JSONArray();
			
			for (int k = 0; k < maze.getMax_n(); k++) {
				neighbors.add(auxcells[j].getNeighbors()[k]);

			}
			individualcell.put("value", maze.getCells()[j].getValue());
			individualcell.put("neighbors", neighbors);
			almostcells.put(
					"(" + maze.getCells()[j].getPosition()[0] + ", " + maze.getCells()[j].getPosition()[1] + ")",
					individualcell);

		}

		mazeobject.put("rows", maze.getRows());
		mazeobject.put("cols", maze.getCols());
		mazeobject.put("max_n", maze.getMax_n());
		mazeobject.put("mov", movs);
		mazeobject.put("id_mov", id_mov);
		mazeobject.put("cells", almostcells);

		try {
			FileWriter file = new FileWriter("example.json");
			file.write(mazeobject.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Maze readJson(String filename) throws org.json.simple.parser.ParseException, IOException {
		Maze readmaze = new Maze();

		JSONParser jsonParser = new JSONParser();

		FileReader reader = new FileReader(filename);

		Object object = jsonParser.parse(reader);

		JSONObject jsonmaze = (JSONObject) object;
		
		Long longcols = (long) jsonmaze.get("cols");
		int cols = longcols.intValue();
		
		Long longrows = (long) jsonmaze.get("rows");
		int rows = longrows.intValue();
		Long longmax_n = (long) jsonmaze.get("max_n");
		int max_n = longmax_n.intValue();
		JSONArray idmovs_array = (JSONArray) jsonmaze.get("id_mov");
		String[] id_movs = new String[idmovs_array.size()];
		for (int i = 0; i < idmovs_array.size(); i++) {
			id_movs[i] = (String) idmovs_array.get(i);
		}
		JSONArray movsArray = (JSONArray) jsonmaze.get("mov");
		int[][] movs = new int[movsArray.size()][2];
		for (int i = 0; i < movsArray.size(); i++) {
			JSONArray mov = (JSONArray) movsArray.get(i);
			String temporalString = "";
			temporalString = String.valueOf(mov.get(0));
			movs[i][0] = Integer.parseInt(temporalString);
			temporalString = String.valueOf(mov.get(1));
			movs[i][1] = Integer.parseInt(temporalString);

		}
		JSONObject cellsObject = (JSONObject) jsonmaze.get("cells");
		Cell[] cells = new Cell[cellsObject.size()];
		int x = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int[] position = new int[2];
				position[0] = i;
				position[1] = j;
				String key = "(" + i + ", " + j + ")";
				JSONObject temporalCell = (JSONObject) cellsObject.get(key);
				Long longvalue = (long) temporalCell.get("value");
				int value = longvalue.intValue();
				JSONArray temporalNeighbours = (JSONArray) temporalCell.get("neighbors");
				
				boolean[] neighbours = new boolean[max_n];
				for (int k = 0; k < max_n; k++) {
					neighbours[k] = (boolean) temporalNeighbours.get(k);
					
				}
				Cell finalCell = new Cell(position, value, neighbours);
				cells[x++] = finalCell;

			}
		}
		readmaze.setCells(cells);
		readmaze.setId_mov(id_movs);
		readmaze.setMovs(movs);
		readmaze.setCols(cols);
		readmaze.setRows(rows);
		readmaze.setMax_n(max_n);
		if(checkMaze(readmaze)) {
			System.out.println("Your file has been read and the maze is correctly generated!");
			return readmaze;
			
		}
		else {
			System.out.println("Your file has been read but the maze wasn't correct!");
			System.exit(-1);
			return null;
		}
		
	}

	public static Maze createMaze(int cols, int rows) {
		Maze maze = new Maze();
		int[][] moves = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
		String[] id_moves = { "N", "E", "S", "O" };
		maze.setMovs(moves);
		maze.setId_mov(id_moves);
		maze.setRows(rows);
		maze.setCols(cols);
		maze.setInitialMax_n(); // with this call the cells are created.
		Cell finale = maze.pickRandomCell();
		Cell start = maze.pickRandomCell();
		while(finale == start) {
			start= maze.pickRandomCell();
		}
		if (checkMaze(maze)) {
			return maze;
		} else {
			return null;
		}
	}

	public static boolean checkMaze(Maze maze) {
		boolean correctmaze = false;
		int[][] moves = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
		String[] id_moves = { "N", "E", "S", "O" };
		if(maze.getId_mov().equals(id_moves)) {
			System.out.println("Error with the name of the movements!");
			return correctmaze;
		}
		if(maze.getMovs().equals(moves)) {
			System.out.println("Error with the value of the movements!"+ maze.getMovs());
			return correctmaze;
		}
		if (maze.calculateMax_n() != maze.getMax_n() || maze.getCells().length != maze.getCols() * maze.getRows()) {
			System.out.println("The max_n from the maze isn't correct");
			return correctmaze;
		}
		if (!maze.checkCells()) {
			System.out.println("Problem with the cells!");
			return correctmaze;
		}
		correctmaze = true;
		return correctmaze;
	}
	

}
