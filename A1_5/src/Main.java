import java.util.Scanner;

public class Main {
	public static void main (String args[]) {
		Maze maze = new Maze();
		int [] [] moves = {{-1,0}, {0,1} ,{1,0} ,{0 -1}};
		String [] id_moves = {"N", "E", "S", "O"};
		String input;
		maze.setMovs(moves);
		maze.setId_mov(id_moves);
		Scanner readinput= new Scanner (System.in);
		input=readinput.nextLine();
		int size;
		size = Integer.parseInt(input);
		maze.setMax_n(size); //con esta llamada se crean tambien las cells.
		// Coger dos cells aleatorias, la primera como final y la segunda como principio.
		// Empezar wilson.
	}
}
