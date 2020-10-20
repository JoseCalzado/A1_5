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
	

	
	public void start() {
		while(maze.getNotVisitedNum()>0) {
		
			Cell start = maze.pickRandomNotVisitedCell();
			
			maze.pickSpecificCell(start.getPosition()).setVisited(true);
			start.setVisited(true);
			List<Cell> path = new ArrayList<Cell>();
			path.add(start);
			auxCell = null;
		
			dig(start, path);
			
		}
	}
	public void dig(Cell start, List<Cell> path) {
		boolean exit= false;
		while(!exit) {
			
			int randomNum = possibleMove(start, path);
			//This case is when the cell has every single one visited, so we cant get out there.
			
			
			if(randomNum == 5) {
				//A cell cannot reach the end s
				maze.pickSpecificCell(start.getPosition()).setVisited(true);
				exit=true;
			}
			else if(maze.getNotVisitedNum()==0) {
				exit = true;
			}
			else if(auxCell.getVisited()) {
				
				//maze.pickSpecificCell(auxCell.getPosition()).setVisited(true);
				maze.pickSpecificCell(start.getPosition()).setNeighbor(randomNum, true);
				maze.pickSpecificCell(auxCell.getPosition()).setNeighbor((auxCell.getOppositeNeighbor(randomNum)), true);
				exit = true;
				
			}
			else  {
				maze.pickSpecificCell(auxCell.getPosition()).setVisited(true);
				maze.pickSpecificCell(start.getPosition()).setNeighbor(randomNum, true);
				maze.pickSpecificCell(auxCell.getPosition()).setNeighbor((auxCell.getOppositeNeighbor(randomNum)), true);
				path.add(auxCell);
				start = auxCell;
				//exit = true;
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
				return 5;
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
					return randomNum;
				}
			}
		}
		return 5;
	}
}
