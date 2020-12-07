import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import org.json.simple.JSONObject;

public class StateSpace {
	private String strategy;
	int [] initial, objetive;
	Maze maze;
	//Method that creates nodes until the goal is achieved.
	//As we can't reach the goal because we are not told to implement any search algorithm, we create at least 100 nodes.
	public  boolean  searchSolution() {
		//We obtain the initial state
		Cell startstate = maze.pickSpecificCell(initial);
		long nodeid =0;
		TreeNode firstnode = new TreeNode(nodeid ,startstate,0,0,0,calculate_heuristic(startstate),null, strategy);
		Visited visitedList = new Visited();
		visitedList.createEmpty();
		Frontier frontier = new Frontier();
		//If we dont initialize the frontier, it returns an error.
		frontier.initializeFrontier();
		frontier.push(firstnode);
		TreeNode auxnode= firstnode;
		boolean solution = false;
		//As we aren't told to create a visited list or any search algorithm, we create at least 100 nodes to see that are introduced well into the frontier.
		while(!solution && (auxnode = frontier.pop())!=null) {
			if(goal(auxnode.getState())) {
					writeFinalSolution(auxnode);
					Solution solutionObject = new Solution(frontier, visitedList, auxnode);
					System.out.println("There is a solution in node " + auxnode.getId());
					maze.setSolution(solutionObject);
					solution = true;
					return solution;
			}
				
			else if(!visitedList.belongs(auxnode.getState())&& auxnode.getDepth()< 1000000) {
					visitedList.insertState(auxnode.getState());
					auxnode.getState().generateSuccessors(maze);
					Successor [] auxsuccessors = auxnode.getState().getSuccessors();
					for(int i=0;i<auxsuccessors.length;i++) {
						Successor auxsuccessor = auxsuccessors[i];
						nodeid++;
						if(auxsuccessor.getState()!= null)
							frontier.push(new TreeNode(nodeid, auxsuccessor.getState(), 0, auxnode.getDepth()+1, auxsuccessor.getCost()+auxnode.getCost(), calculate_heuristic(auxsuccessor.getState()), auxsuccessor.getMov(), auxnode, strategy));
					
					}
				}	
		}
		System.out.println("Error, the frontier is empty!");
		return false;
	}
	
	public StateSpace(String strategy, int[] initial, int[] objetive, Maze maze) {
		this.strategy = strategy;
		this.initial = initial;
		this.objetive = objetive;
		this.maze = maze;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public int[] getInitial() {
		return initial;
	}

	public void setInitial(int[] initial) {
		this.initial = initial;
	}

	public int[] getObjetive() {
		return objetive;
	}

	public void setObjetive(int[] objetive) {
		this.objetive = objetive;
	}

	public Maze getMaze() {
		return maze;
	}

	public void setMaze(Maze maze) {
		this.maze = maze;
	}
	//Checks if an specific state is the goal state.
	public  boolean goal(Cell state) {
		if(state.getPosition()[0]==objetive[0]&&state.getPosition()[1]==objetive[1]) {
			System.out.println("Solution!");
			return true;
		}
		
		else {
			return false;
		}
		
	}
	
	//Method to calculate the heuristic of the new tree node
	public  int calculate_heuristic(Cell current) {
			//Heuristic((row,column))= |row-target_row| + |column-target_column|
			int heuristic = Math.abs((current.getPosition()[0]-objetive[0]) + (current.getPosition()[1]-objetive[1]));
			
			return heuristic;
		}
		//This method prints the final solution into a TXT file.
		public void writeFinalSolution(TreeNode node) {
			FileWriter file = null;
			Stack<TreeNode> stack = new Stack<TreeNode>();
			//JSONObject obj;
			String filename = "solution_"+maze.getRows()+"x"+maze.getCols()+"_"+strategy+".txt";
			try {
				file = new FileWriter(filename);
			} catch(IOException e) {
				System.out.println(e.getMessage());
			}

			stack.add(node);
			do{
				stack.add(node.getParent());
				node = node.getParent();
			}while(node.getParent() != null);

			do{
				node = stack.pop();
				try {

					file.write(node.toString() + '\n');
				} catch(IOException e) {
					System.out.println(e.getMessage());
				}
			}while(!stack.isEmpty());
			
			
			System.out.println("File "+ filename +" generated succesfully!");
			try {
				file.flush();
				file.close();
			} catch(IOException e) {
				System.out.println(e.getMessage());
			}
		}
}
