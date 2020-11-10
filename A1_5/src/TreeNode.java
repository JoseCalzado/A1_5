
public class TreeNode implements Comparable<TreeNode> {
	private int id;
	private Cell state;
	private double value;
	private int depth;
	private int cost;
	private double heuristic;
	public TreeNode(int id, Cell state, double value, int depth, int cost, double heuristic, String action,
			TreeNode parent) {
		super();
		this.id = id;
		this.state = state;
		this.value = value;
		this.depth = depth;
		this.cost = cost;
		this.heuristic = heuristic;
		this.action = action;
		this.parent = parent;
	}

	private String action;
	private TreeNode parent;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	//This constructor is used to create the first node of the frontier, that doesn't have parent or action.
	public TreeNode(int id, Cell state, double value, int depth, int cost, double heuristic) {
		this.id = id;
		this.state = state;
		this.value = value;
		this.depth = depth;
		this.cost = cost;
		this.heuristic = heuristic;
	}
	public Cell getState() {
		return state;
	}
	public void setState(Cell state) {
		this.state = state;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public double getHeuristic() {
		return heuristic;
	}
	public void setHeuristic(double heuristic) {
		this.heuristic = heuristic;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public TreeNode getParent() {
		return parent;
	}
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	
	
	//Compares first the values, then the rows and then the cols of 2 cells, to order the frontier.
	public int compareTo(TreeNode node) {  
	    
		if(this.getValue() > node.getValue()) {
		
            return 1;
        } else if (this.getValue()< node.getValue()) {
            return -1;
        }else{
        	if(this.getState().getPosition()[0] > node.getState().getPosition()[0]){
        		return 1;
        	}else if(this.getState().getPosition()[0] < node.getState().getPosition()[0]){
        		return -1;
        	}
        	else {
        		if(this.getState().getPosition()[1] >= node.getState().getPosition()[1]){
            		return 1;
            	}else {
            		return -1;
            	}
        	}
        }
	  }
}
