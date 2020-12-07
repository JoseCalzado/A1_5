
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

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
			//The option A is creating a random maze, as it was asked on the SUBTASK 1
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
			//The option B is to read a json and obtain the initial position, the objetive position and the maze. Then, we ask the user for an algorithm and we try to solve the maze.
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
						printMaze(mazeread);
						
						jsonmanager.writeJson(mazeread);						
						
						choosefile = true;
						System.out.println(
							"The filename was correct, the readed maze has been written on the finalMaze.json file!");
						
						
					} catch (Exception e) {
						System.out.println("Error reading the file, please try again");
						input = readinput.nextLine();
						input.replace("/", "//");
					}
					boolean choosestrategy = false;
					//We ask for one of the strategies that we have been told to perform.
					System.out.println("Please, choose one of the following strategies: DEPTH, BREADTH, UNIFORM, GREEDY or A");
					input = readinput.nextLine();
					while (!choosestrategy) {
						if (input.matches("DEPTH") || input.matches("BREADTH") || input.matches("UNIFORM")|| input.matches("GREEDY")|| input.matches("A")) {
							choosestrategy = true;
							String strategy = input;
							//We create the statespace in which we are going to search for a solution.
							StateSpace statespace = new StateSpace(strategy,jsonmanager.getInitial(), jsonmanager.getObjective(), jsonmanager.getMaze());
							//If we have a solution we print the png;
							if(statespace.searchSolution()) {
								System.out.println("The maze has been solved!");
								Maze finalMaze = statespace.getMaze();
								finalMaze.setSolved(true);
								printSolvedMaze(statespace.getMaze(), statespace.getStrategy());
							}
							else {
								System.out.println("Solution not found!");
							}
						} else {
							System.out.println("Your strategy is not available, try again!");
							input = readinput.nextLine();
						}
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
			printMaze(maze);
			return maze;
		} else {
			return null;
		}
		
	}
	//this method creates the first JFrame and invokes the print method from the Maze class which will print the original maze.
	public static  void printMaze(Maze maze) {
		JFrame frame = new JFrame();
		frame.setSize(1000, 1000);
	    frame.setTitle("Maze");
	    frame.add(maze);
	    frame.setVisible(true);;
	    BufferedImage bi = new BufferedImage(frame.getSize().width, frame.getSize().height, BufferedImage.TYPE_INT_ARGB); 
	    Graphics g = bi.createGraphics();
	    frame.paint(g);  
	    g.dispose();
	    String filename="puzzle_loop_"+maze.getRows()+"x"+maze.getCols()+".png";
	    try{ImageIO.write(bi,"png",new File(filename));}catch (Exception e) {}
	}
	
	//When we solve the maze, we print it as we have been told, with the colors for the frontier, visited list and 
	public static  void printSolvedMaze(Maze maze, String strategy) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
	    frame.setTitle("Maze");
	    frame.add(maze);
	    frame.setVisible(true);;
	    BufferedImage bi = new BufferedImage(frame.getSize().width, frame.getSize().height, BufferedImage.TYPE_INT_ARGB); 
	    Graphics g = bi.createGraphics();
	    frame.paint(g);  
	    g.dispose();
	    String filename="solution_"+maze.getRows()+"x"+maze.getCols()+"_"+strategy+"20.png";
	    try{ImageIO.write(bi,"png",new File(filename));}catch (Exception e) {}
	}
	

}
