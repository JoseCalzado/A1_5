
public class Maze {
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
	
	public void createCells(int size) {
		//Dependiendo del size, se crean unas cells u otras
		//se llama desde setMax y se llama solo al principio.
	}
	
	
}
