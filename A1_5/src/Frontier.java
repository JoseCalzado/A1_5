import java.util.PriorityQueue;
public class Frontier {
	private PriorityQueue <TreeNode> frontier;
	
	//Initialize the frontier. It needs to be called when we create a new frontier or it doesn't allow us to insert anything. 
	public void initializeFrontier() {
		
		frontier=new PriorityQueue<TreeNode>();
		
	}
	
	//Puts a node into the frontier. It gets ordered due to the compareTo method on the TreeNode class.
	public void push(TreeNode node) {
		frontier.add(node);
	}
	
	//Gets the top of the head or the top of the frontier, and it removes it from it.
	public TreeNode pop() {
		return frontier.poll();
	}
}
