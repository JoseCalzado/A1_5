
public class TreeNode implements Comparable<TreeNode> {
	private long id;
	private Cell state;
	private float value;
	private int depth;
	private int cost;
	private float heuristic;
	public TreeNode(long id, Cell state, float value, int depth, int cost, float heuristic, String action,
			TreeNode parent, String strategy) {
		super();
		this.id = id;
		this.state = state;
		this.depth = depth;
		this.cost = cost;
		this.heuristic = heuristic;
		this.action = action;
		this.parent = parent;
		
		switch(strategy) {
			case "BREADTH":
				this.value=  (float) depth;
				break;
			case "DEPTH":
				float depthFloat = (float) depth;
				this.value = (float) (1.0/ depthFloat+1.0);
				System.out.println(this.value);
				break;
			case "UNIFORM":
				this.value= (float) cost;
				break;
			case "GREEDY":
				this.value=heuristic;
				break;
			case "A":
				this.value=heuristic+ (float)cost;
				break;
	}
	}

	private String action;
	private TreeNode parent;
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	//This constructor is used to create the first node of the frontier, that doesn't have parent or action.
	public TreeNode(long id, Cell state, float value, int depth, int cost, float heuristic, String action, String strategy) {
		this.id = id;
		this.state = state;
		this.depth = depth;
		this.cost = cost;
		this.heuristic = heuristic;
		this.action = action;
		switch(strategy) {
		case "BREADTH":
			this.value= depth;
			break;
		case "DEPTH":
			this.value = (1/(depth+1));
			break;
		case "UNIFORM":
			this.value=cost;
			break;
		case "GREEDY":
			this.value=heuristic;
			break;
		case "A":
			this.value=heuristic+cost;
			break;
}
	}
	public Cell getState() {
		return state;
	}
	public void setState(Cell state) {
		this.state = state;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
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
	public float getHeuristic() {
		return heuristic;
	}
	public void setHeuristic(float heuristic) {
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
	
	
	//Compares first the values, then the rows , the cells and finally the nodeId.
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
        		if(this.getState().getPosition()[1] > node.getState().getPosition()[1]){
            		return 1;
            	}else if(this.getState().getPosition()[1] < node.getState().getPosition()[1]){
            		return -1;
            	}
            	else {
            		if(this.getId() >= node.getId()){
                		return 1;
                	}else {
                		return -1;
                	}
            	}
        	}
        }
	  }
	@Override
	public String toString() {
		if(parent != null) {
		return "["+id+"]"+"["+cost+",("+state.getPosition()[0]+", "+state.getPosition()[1]+"),"+parent.getId()+","+action+","+depth+","+heuristic+","+value+"]";
		}
		else {
			return "["+id+"]"+"["+cost+",("+state.getPosition()[0]+", "+state.getPosition()[1]+"),None,None,"+depth+","+heuristic+","+value+"]";
		}
		}
}
