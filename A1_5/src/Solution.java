import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solution {
	private Frontier frontier;
	private List<Cell> frontierCellsList;
	private Visited visitedList;
	private TreeNode finalNode;
	private List<Cell> solutionList;
	
	public Solution(Frontier frontier, Visited visitedList, TreeNode finalNode) {
		this.frontier = frontier;
		this.visitedList = visitedList;
		this.finalNode = finalNode;
		this.solutionList = new ArrayList<Cell>();
		this.frontierCellsList = new ArrayList<Cell>();
		solutionList.add(finalNode.getState());
		do{
			solutionList.add(finalNode.getParent().getState());
		
			finalNode = finalNode.getParent();
		}while(finalNode.getParent() != null);
		TreeNode auxnode =frontier.pop();
		do{
			frontierCellsList.add(auxnode.getState());
			auxnode=frontier.pop();
		}while(auxnode != null);
		
	}
	public List<Cell> getFrontierCellsList() {
		return frontierCellsList;
	}
	public void setFrontierCellsList(List<Cell> frontierCellsList) {
		this.frontierCellsList = frontierCellsList;
	}
	public List<Cell> getSolutionList() {
		return solutionList;
	}
	public void setSolutionList(List<Cell> solutionList) {
		this.solutionList = solutionList;
	}
	public Frontier getFrontier() {
		return frontier;
	}
	public void setFrontier(Frontier frontier) {
		this.frontier = frontier;
	}
	public Visited getVisitedList() {
		return visitedList;
	}
	public void setVisitedList(Visited visitedList) {
		this.visitedList = visitedList;
	}
	public TreeNode getFinalNode() {
		return finalNode;
	}
	public void setFinalNode(TreeNode finalNode) {
		this.finalNode = finalNode;
	}
}
