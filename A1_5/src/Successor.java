
public class Successor {
	private String mov;
	private Cell state;
	private int cost;
	public String getMov() {
		return mov;
	}
	public void setMov(String mov) {
		this.mov = mov;
	}
	public Cell getState() {
		return state;
	}
	public void setState(Cell state) {
		this.state = state;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public Successor(String mov, Cell state, int cost) {
		this.mov = mov;
		this.state = state;
		this.cost = state.getValue()+1;
	}
	
	
}
