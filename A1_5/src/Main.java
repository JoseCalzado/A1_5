
import java.util.Scanner;

public class Main  {
	public static void main(String args[]) throws org.json.simple.parser.ParseException {

		System.out.println("Welcome to the Maze Generator and Solver from the A1-5 Group.");
		System.out.println("Pick an option: \n" + "\n" + "A for creating a random maze. \n"
				+ "B for reading a file that contains a maze. \n" + "E for exiting the application. \n");
		String input;
		Scanner readinput = new Scanner(System.in);
		input = readinput.nextLine();
		JSONManager jsonmanager = new JSONManager();
		boolean chooseanoption = false;
		//We create a menu
		while (!chooseanoption)
			//The option A is creating a random maze;
			if (input.contentEquals("A") || input.contentEquals("a")) {
				chooseanoption = true;
				
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

				
				
				Maze maze = createMaze(rows, cols);
				
				
				jsonmanager.writeJson(maze);
			//The option B is to read a json and obtain the initial position, the objetive position and the maze.
			} else if (input.equals("B") || input.contentEquals("b")) {
				chooseanoption = true;
				System.out.println("Please, write the name of the file that you want to read.");
				input = readinput.nextLine();
				input.replace("/", "//");
				
				boolean choosefile = false;
				while (!choosefile) {
					try {
						jsonmanager.readJson(input);
						Maze mazeread = jsonmanager.getMaze();
						/* Given the sucesores_100x100.json file, with this quick test we can see that we generate the correct successors.
						
						int[] testposition = {63,77};
						Cell pruebaCell =mazeread.pickSpecificCell(testposition);
						pruebaCell.generateSuccessors(mazeread);
						pruebaCell.printSuccessors();
						
						*/
						
						createNodes(jsonmanager.getInitial(), jsonmanager.getObjective(), jsonmanager.getMaze());
						mazeread.printMaze();
						jsonmanager.writeJson(mazeread);
						System.out.println("Initial position ("+jsonmanager.getInitial()[0]+", "+jsonmanager.getInitial()[1]+")");
						System.out.println("Objetive position ("+jsonmanager.getObjective()[0]+", "+jsonmanager.getObjective()[1]+")");
						
						choosefile = true;
						System.out.println(
								"The filename was correct, the maze has been written on the finalMaze.json file!");
					} catch (Exception e) {
						System.out.println("Error reading the file, please try again");
						input = readinput.nextLine();
						input.replace("/", "//");
					}
				}
			//Option E for EXIT
			} else if (input.equals("E") || input.contentEquals("e")) {
				chooseanoption = true;
				System.out.println("Hope to see you soon!");
				System.exit(0);
			} else {
				System.out.println("Choose between A, B or E!");
				input = readinput.nextLine();
			}
		readinput.close();
	}

	
	//Method to create a maze from zero.
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
		maze.pickSpecificCell(finale.getPosition()).setVisited(true);
		finale.setVisited(true);
		Wilson wilson = new Wilson(maze);
		wilson.start();
		maze = wilson.getMaze();
		if (maze.checkMaze()) {
			System.out.println("The maze has been created and checked!");
			maze.printMaze();
			return maze;
		} else {
			return null;
		}
		
	}
	//Method that creates nodes until the goal is achieved.
	//As we can't reach the goal because we are not told to implement any search algorithm, we create at least 100 nodes.
	public static void  createNodes(int [] initial, int[] objetive, Maze maze) {
		//We obtain the initial state
		Cell startstate = maze.pickSpecificCell(initial);
		TreeNode firstnode = new TreeNode(0,startstate,0,0,0,0);
		Frontier frontier = new Frontier();
		//If we dont initialize the frontier, it returns an error.
		frontier.initializeFrontier();
		frontier.push(firstnode);
		TreeNode auxnode= firstnode;
		Successor [] auxsuccessors;
		Successor auxsuccessor;
		int nodeid =0;
		//As we aren't told to create a visited list or any search algorithm, we create at least 100 nodes to see that are introduced well into the frontier.
		while((!goal(objetive,auxnode.getState())) && (nodeid<100)) {
			auxnode = frontier.pop();
			auxnode.getState().generateSuccessors(maze);
			auxsuccessors = auxnode.getState().getSuccessors();
			for(int i=0;i<auxsuccessors.length;i++) {
				auxsuccessor = auxsuccessors[i];
				nodeid++;
				TreeNode newnode = new TreeNode(nodeid, auxsuccessor.getState(), auxnode.getValue()+1, auxnode.getDepth()+1, auxnode.getCost()+1, 0, auxsuccessor.getMov(),auxnode);
				frontier.push(newnode);
			}
		}
		
	}
	
	//Method to check the goal.
	public static boolean goal(int [] objetive, Cell state) {
		if(state.getPosition()==objetive) {
			System.out.println("Solution!");
			return true;
		}
		
		else {
			return false;
		}
		
	}
	
	
	

}
