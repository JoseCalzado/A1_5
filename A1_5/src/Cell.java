import java.util.Arrays;


public class Cell {
	private int [] position;
	private int value;
	private boolean [] neighbors;
	private boolean visited;
	private Successor [] successors;
	
	public Cell(int[] position, int value, boolean[] neighbors) {
		this.position = position;
		this.value = value;
		this.neighbors = neighbors;
	}
	public int[] getPosition() {
		return position;
	}
	public void setPosition(int[] position) {
		this.position = position;
	}
	public int getValue() { 
		return value;
	}
	@Override
	public String toString() {
		return "Cell [position=" + Arrays.toString(position) + ", value=" + value + ", neighbors="
				+ Arrays.toString(neighbors) + "]";
	}
	public Successor[] getSuccessors() {
		return successors;
	}
	public void setSuccessors(Successor[] successors) {
		this.successors = successors;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public boolean[] getNeighbors() {
		return neighbors;
	}
	public void setNeighbors(boolean[] neighbors) {
		this.neighbors = neighbors;
	}
	public void setNeighbor(int position, boolean value) {
		if (neighbors.length < 4) {
			
		}
		else {
		this.neighbors[position] = value;
		}
	}
	public boolean getVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	//Gets the opposite side neighbors (if the neighbor is the South, it returns the North).
	public int getOppositeNeighbor(int neighbor) {
		if(neighbor<2) {
			return neighbor+2;
		}
		else if(neighbor>=2) {
			return neighbor-2;
		}
		return 0;
	}
	
	//Generate the successors of this cell;
	public void generateSuccessors(Maze maze) {
		int x =0;
		String auxMov="";
		Cell auxState = this;
		int [] auxposition = new int [2];
		Successor[] auxsuccessors = new Successor[4];
		for(int i=0; i<neighbors.length;i++) {
			if(neighbors[i]==true) {
				auxposition[0]=position[0]+maze.getMovs()[i][0];
				auxposition[1]=position[1]+maze.getMovs()[i][1];
				auxMov=maze.getId_mov()[i];
				auxState=maze.pickSpecificCell(auxposition);
				Successor auxsuccessor = new Successor(auxMov, auxState,1);
				auxsuccessors[x++]=auxsuccessor;
			}
		}
		Successor [] finalsuccessors = new Successor[x];
		for(int i=0; i<x;i++) {
			finalsuccessors[i]=auxsuccessors[i];
		}
		this.successors=finalsuccessors;
		
	}
	
	//It prints the successors as we have been told.
	public void printSuccessors() {
		System.out.println("Successors of state ("+position[0]+", "+position[1]+"):");
		for(int i=0; i<successors.length;i++) {
			System.out.println("('"+successors[i].getMov()+"',("+successors[i].getState().getPosition()[0]+","+successors[i].getState().getPosition()[1]+"),"+
			successors[i].getCost()+")");
		}
	}
	
}
