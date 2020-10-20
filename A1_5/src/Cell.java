import java.util.Arrays;

public class Cell {
	private int [] position;
	//private int x;
	//private int y;
	private int value;
	private boolean [] neighbors;
	private boolean visited;
	
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
	public int getOppositeNeighbor(int neighbor) {
		if(neighbor<2) {
			return neighbor+2;
		}
		else if(neighbor>=2) {
			return neighbor-2;
		}
		return 0;
	}
}
