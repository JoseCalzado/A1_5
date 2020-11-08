import java.util.ArrayList;
import java.util.List;

public class Wilson {
	private Maze maze;
	private Cell auxCell;
	public Wilson(Maze maze) {
		this.maze = maze;
	}
	public Maze getMaze() {
		return maze;
	}
	public void setMaze(Maze maze) {
		this.maze = maze;
	}
	

	//We start the algorithm, that will be up until we have visited all the cells.
	public void start() {
		while(maze.getNotVisitedNum()>0) {
		
			Cell start = maze.pickRandomNotVisitedCell(); 
			
			maze.pickSpecificCell(start.getPosition()).setVisited(true);
			start.setVisited(true);
			List<Cell> path = new ArrayList<Cell>(); //We create the path for later checks (to not get into loops).
			path.add(start);
			auxCell = null;
		
			dig(start, path);
			
		}
	}
	public void dig(Cell start, List<Cell> path) {
		boolean exit= false;
		while(!exit) {
			
			int randomNum = possibleMove(start, path); //It puts the auxcell global variable into a possible move.
			
			
			
			if(randomNum == 5) {//This case is when the cell has every single one visited, so we can't get out there.
				//A cell cannot reach the end s
				maze.pickSpecificCell(start.getPosition()).setVisited(true);
				exit=true;
			}
			else if(maze.getNotVisitedNum()==0) { //We check if the maze has still some not visited cells.
				exit = true;
			}
			else if(auxCell.getVisited()) {//If we have reached a visited cell, we stop digging.
				maze.pickSpecificCell(start.getPosition()).setNeighbor(randomNum, true);
				maze.pickSpecificCell(auxCell.getPosition()).setNeighbor((auxCell.getOppositeNeighbor(randomNum)), true);
				exit = true;
				
			}
			else  {//We dig more!
				maze.pickSpecificCell(auxCell.getPosition()).setVisited(true);
				maze.pickSpecificCell(start.getPosition()).setNeighbor(randomNum, true);
				maze.pickSpecificCell(auxCell.getPosition()).setNeighbor((auxCell.getOppositeNeighbor(randomNum)), true);
				path.add(auxCell);
				start = auxCell;
		}
		}
	}
	 
	public int possibleMove(Cell cell,List<Cell> path) {
		Cell auxcell= null;
		int x = cell.getPosition()[0];
		int y = cell.getPosition()[1];
		
		boolean exit = false;
		List <Integer> checkedMoves = new ArrayList<>();
		while(!exit) {
			int randomNum=(int) Math.floor(Math.random()*4);
			if(checkedMoves.size()==4) {
				exit=true;
				return 5; //Returns a 5 when every sing cell around our cell has already been visited.
			}
			if(!(checkedMoves.contains(randomNum))) {
				checkedMoves.add(randomNum);
				int[] randomMove=maze.getMovs()[randomNum];
				int auxx =0;
				int auxy=0;
				auxx=x+randomMove[0];
				auxy=y+randomMove[1];
				int []auxposition = {auxx, auxy};
				auxcell= maze.pickSpecificCell(auxposition);
				if(!(auxposition[0]<0 || auxposition[0]>=maze.getRows() || auxposition[1]<0 || auxposition[1]>=maze.getCols()) && !(path.contains(auxcell))) {
					exit = true;
					auxCell=auxcell;
					return randomNum; //Returns the number of the neighbor that we are going to visit (Example: randomNum = 0 ; 0 is the position of N).
				}
			}
		}
		return 6; // We can't reach this code.
	}
}
