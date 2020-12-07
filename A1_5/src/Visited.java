import java.util.ArrayList;

public class Visited {
	private ArrayList<int[]> visited;
	
	public void createEmpty() {
		
		visited = new ArrayList<>();
		
	}
	
	//Puts a node into the frontier. It gets ordered due to the compareTo method on the TreeNode class.
	public void insertState(Cell state) {
		visited.add(state.getPosition());
    }
	
	//Gets the top of the head or the top of the frontier, and it removes it from it.
	public boolean belongs(Cell state) {
		if(visited.contains(state.getPosition())) {
			
			return true;
		}
		return false;
	}
}
