import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Maze extends JPanel {
	private int rows;
	private int cols;
	private int max_n;
	private int [] [] movs;
	private String [] id_mov;
	private Cell [] cells;
	
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getCols() {
		return cols;
	}
	public void setCols(int cols) {
		this.cols = cols;
	}
	public int getMax_n() {
		return max_n;
	}
	public void setInitialMax_n() {
		
		this.max_n = calculateMax_n();
		createCells(this.max_n, this.rows, this.cols);
	}
	public void setMax_n(int max_n) {
		this.max_n = max_n;
	}
	public int [] []  getMovs() {
		return movs;
	}
	public void setMovs(int [][] movs) {
		this.movs = movs;
	}
	public String[] getId_mov() {
		return id_mov;
	}
	public void setId_mov(String[] id_mov) {
		this.id_mov = id_mov;
	}
	public Cell[] getCells() {
		return cells;
	}
	public void setCells(Cell[] cells) {
		this.cells = cells;
	}
	
	public void createCells(int maxN, int rows , int cols) {
		int x = 0;
		Cell[] cellArray = new Cell [rows*cols]; 
		for(int i = 0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				int [] position = {i, j};
				boolean [] neighbours = new boolean[maxN];
				for(int k =0; k<maxN; k++) {
					neighbours[k] = false;
				}
				Cell cellprueba = new Cell(position, 0, neighbours);
				cellArray[x++] = cellprueba;
			}
			
		}
		setCells(cellArray);
	}
	
	public String toString() {
		return "Maze [rows=" + rows + ", cols=" + cols + ", max_n=" + max_n + ", movs=" + Arrays.toString(movs)
				+ ", id_mov=" + Arrays.toString(id_mov) + ", cells=" + Arrays.toString(cells) + "]";
	}
	public Cell pickRandomCell() {
		int randomnumber = (int) Math.floor(Math.random()*rows*cols);
		Cell randomCell = cells[randomnumber];
		return randomCell;
	}
	
	public Cell pickRandomNotVisitedCell() {
		int randomnumber = (int) Math.floor(Math.random()*rows*cols);
		Cell randomCell = cells[randomnumber];
		
		while( randomCell.getVisited()) {
			randomnumber = (int) Math.floor(Math.random()*rows*cols);
			randomCell = cells[randomnumber];
		}
		return randomCell;
	}
	public Cell pickSpecificCell(int [] position) {
		for(int i = 0; i< rows * cols; i++) {
			if(cells[i].getPosition()[0]==position[0] && cells[i].getPosition()[1]==position[1]) {
				return cells[i];
			}
		}
		return null;
	}
	public int getNotVisitedNum() {
		int notVisited = 0;
		for(int i = 0; i<cells.length; i++) {
			if(!(cells[i].getVisited())) {
				notVisited++;
			}
		}
		return notVisited;
	}
	public int calculateMax_n() {
		int maxN = 0;
		int rowspercols = rows * cols;
		float checkMaxN = (float)rowspercols/2;
		if(checkMaxN >= 4) {
			maxN= 4;
		}
		else {
			maxN = Math.round(checkMaxN);
		}
		return maxN;
	}
	public boolean checkCells() {
		if(this.cells.length != this.rows*this.cols) {
			System.out.println("The number of cells isn't correct.");
			return false;
		}
		
		else {
			int [] positioncell = new int [2];
			for(int i =0; i<rows; i++) {
				positioncell[0] = i;
				for(int j =0; j<cols; j++) {
					positioncell[1] = j;
					Cell auxcell=  pickSpecificCell(positioncell);
					if(auxcell== null || !checkCell(auxcell)) {
						System.out.println("Problem with the cells");
						return false;
					}
				}
			}
		}
		return true;
	}
	public boolean checkCell(Cell cell) {
		//x and y are the positions of the checked cell.
		
		int x, y;
		x=cell.getPosition()[0];
		y=cell.getPosition()[1];
		boolean [] auxneighbors = cell.getNeighbors();
		Cell auxcell;
		//first we check if the number of neighbors is right,
		if(auxneighbors.length != max_n) {
			System.out.println("The number of neighbors data of the cell of the position "+ cell.getPosition() + "isn't correct");
			return false;
		}
		//Then we are going to check if the neighbors data is correct.
		//auxx and auxy are going to be the position of the neighbors.
		int auxi=2;
		for(int i =0; i<max_n; i++) {
			int auxx =0;
			int auxy=0;
			auxx=x+movs[i][0];
			auxy=y+movs[i][1];
			//if it is out of bounds, it must be false.
			if(auxx<0 || auxx>=rows || auxy<0 || auxy>=cols)  {
				auxi+=1;
				if(auxneighbors[i]!=false) {
					System.out.println("Neighbor out of bounds");
					return false;
				}
			}
			else {
				if(auxi ==max_n ) {
					auxi=0;
				}
				else if(auxi == max_n+1) {
					auxi=1;
				}
				int [] positionauxcell = {auxx, auxy}; 
				auxcell = pickSpecificCell(positionauxcell);
				boolean [] auxneighbors1 = auxcell.getNeighbors();
				if(auxneighbors[i] != auxneighbors1[auxi]) {
					System.out.println("There is an incompatibility between two neighbors ("+ x+", " + y+") and ("+auxx+", "+auxy+")"+auxi);
					return false;
				}
				auxi+=1;
			}
		}
		return true;
	}
	
	public void paintComponent(Graphics g) 
	{
		List<Cell> paintedCells = new ArrayList();
		int AreaX = 10;
	    int AreaY = 10;
	    int cellSize = (35 - AreaX) / 35 + 60;

	    // temp variables used for painting
	    int x = AreaX;
	    int y = AreaY;
	    for (int i = 0; i < rows; i++) {
	        for (int j = 0; j < cols; j++) {
	        	int [] position = {i,j};
	        	Cell cell = pickSpecificCell(position);
	        	for(int k =0; k<cell.getNeighbors().length;k++) {
	        		int cellx = cell.getPosition()[0];
        			int celly = cell.getPosition()[1];
        			int auxx = cellx+getMovs()[k][0];
        			int auxy = celly+getMovs()[k][1];
        			int [] auxposition = {auxx, auxy};
        			Cell auxCell = pickSpecificCell(auxposition);
        	
	        		if (!(cell.getNeighbors()[k]) && !paintedCells.contains(auxCell) ) {
	        			
	        			switch(id_mov[k]) {
	        			case "N":
	     
	        				g.drawLine(x, y, x + cellSize, y);
	        				break;
	        			case "E":
	        				g.drawLine(x+cellSize, y+cellSize, x+cellSize,y);
	        				
	        				break;
	        			case "S":
	        				
	        				
	        				g.drawLine(x, y+cellSize , x+cellSize , y+cellSize );
	        				
	        				break;
	        			case "0":
	        				g.drawLine(x+cellSize, y, x+cellSize,y + cellSize );
	        				break;
	        				
	        			}
	        			
	        			
		                
		            }
	        			
		        }
	        	paintedCells.add(cell);
	        	 x += cellSize;
	        }
	      
	        x = AreaX;
	        y += cellSize;
	       
	    }
	    /*
	    BufferedImage bi = new BufferedImage(this.getSize().width, this.getSize().height, BufferedImage.TYPE_INT_ARGB);
        //Graphics g = bi.createGraphics();
        this.paint(g);
        g.dispose();
        File file = new File("graph.png");
        try{
            ImageIO.write(bi,"png",file);
        }catch (Exception e) {
            e.printStackTrace();
        }
        */
	  
	    
    }
	}
	//JPG!!!
	
        

