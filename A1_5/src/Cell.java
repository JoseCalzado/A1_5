
public class Cell {
	private int [] position;
	private int value;
	private boolean [] neighbours;
	public int[] getPosition() {
		return position;
	}
	public void setPosition(int[] position) {
		this.position = position;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public boolean[] getNeighbours() {
		return neighbours;
	}
	public void setNeighbours(boolean[] neighbours) {
		this.neighbours = neighbours;
	}
}
