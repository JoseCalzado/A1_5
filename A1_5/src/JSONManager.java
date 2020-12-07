import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONManager {
	
	private int []initial;
	private int [] objective;
	private Maze maze;
	
	
	public int[] getInitial() {
		return initial;
	}

	public void setInitial(int[] initial) {
		this.initial = initial;
	}

	public int[] getObjective() {
		return objective;
	}

	public void setObjective(int[] objective) {
		this.objective = objective;
	}

	public Maze getMaze() {
		return maze;
	}

	public void setMaze(Maze maze) {
		this.maze = maze;
	}
	
	//Given a maze, it prints it into the finalMaze.json file.
	public  void writeJson(Maze maze) {
		JSONObject mazeobject = new JSONObject();

		JSONArray id_mov = new JSONArray();
		JSONArray movs = new JSONArray();
		for (int i = 0; i < 4; i++) {
			JSONArray mov = new JSONArray();
			mov.add(maze.getMovs()[i][0]);
			mov.add(maze.getMovs()[i][1]);
			id_mov.add(maze.getId_mov()[i]);
			movs.add(mov);
		}

		JSONObject finalcells = new JSONObject();
		JSONObject almostcells = new JSONObject();

		Cell[] auxcells = new Cell[maze.getCells().length];
		auxcells = maze.getCells();
		for (int j = 0; j < (maze.getRows() * maze.getCols()); j++) {
			JSONObject individualcell = new JSONObject();
			JSONArray neighbors = new JSONArray();
			
			for (int k = 0; k < maze.getMax_n(); k++) {
				neighbors.add(auxcells[j].getNeighbors()[k]);

			}
			individualcell.put("value", maze.getCells()[j].getValue());
			individualcell.put("neighbors", neighbors);
			almostcells.put(
					"(" + maze.getCells()[j].getPosition()[0] + ", " + maze.getCells()[j].getPosition()[1] + ")",
					individualcell);

		}

		mazeobject.put("rows", maze.getRows());
		mazeobject.put("cols", maze.getCols());
		mazeobject.put("max_n", maze.getMax_n());
		mazeobject.put("mov", movs);
		mazeobject.put("id_mov", id_mov);
		mazeobject.put("cells", almostcells);

		try {
			FileWriter file = new FileWriter("finalMaze.json");
			file.write(mazeobject.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	//It reads a JSON that contains the objective position, the initial position and the name of the file that contains the maze.
	public void readJson(String filename) throws org.json.simple.parser.ParseException, IOException {
		JSONParser jsonParser = new JSONParser();

		FileReader reader = new FileReader(filename);

		Object object = jsonParser.parse(reader);
		JSONObject jsonObject = (JSONObject) object;
		String objectivestr = (String)jsonObject.get("OBJETIVE");
		objective = getPosition(objectivestr);
		String initialstr = (String)jsonObject.get("INITIAL");
		initial = getPosition(initialstr);
		String mazeName = (String)jsonObject.get("MAZE");
		maze =readMaze(mazeName);
	}
	
	//It reads a maze from a JSON file.
	public Maze readMaze(String filename) throws org.json.simple.parser.ParseException, IOException {
		Maze readmaze = new Maze();

		JSONParser jsonParser = new JSONParser();

		FileReader reader = new FileReader(filename);

		Object object = jsonParser.parse(reader);

		JSONObject jsonmaze = (JSONObject) object;
		
		Long longcols = (long) jsonmaze.get("cols");
		int cols = longcols.intValue();
		
		Long longrows = (long) jsonmaze.get("rows");
		int rows = longrows.intValue();
		Long longmax_n = (long) jsonmaze.get("max_n");
		int max_n = longmax_n.intValue();
		JSONArray idmovs_array = (JSONArray) jsonmaze.get("id_mov");
		String[] id_movs = new String[idmovs_array.size()];
		for (int i = 0; i < idmovs_array.size(); i++) {
			id_movs[i] = (String) idmovs_array.get(i);
		}
		JSONArray movsArray = (JSONArray) jsonmaze.get("mov");
		int[][] movs = new int[movsArray.size()][2];
		for (int i = 0; i < movsArray.size(); i++) {
			JSONArray mov = (JSONArray) movsArray.get(i);
			String temporalString = "";
			temporalString = String.valueOf(mov.get(0));
			movs[i][0] = Integer.parseInt(temporalString);
			temporalString = String.valueOf(mov.get(1));
			movs[i][1] = Integer.parseInt(temporalString);

		}
		JSONObject cellsObject = (JSONObject) jsonmaze.get("cells");
		Cell[] cells = new Cell[cellsObject.size()];
		int x = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int[] position = new int[2];
				position[0] = i;
				position[1] = j;
				String key = "(" + i + ", " + j + ")";
				JSONObject temporalCell = (JSONObject) cellsObject.get(key);
				Long longvalue = (long) temporalCell.get("value");
				int value = longvalue.intValue();
				JSONArray temporalNeighbours = (JSONArray) temporalCell.get("neighbors");
				
				boolean[] neighbours = new boolean[max_n];
				for (int k = 0; k < max_n; k++) {
					neighbours[k] = (boolean) temporalNeighbours.get(k);
					
				}
				Cell finalCell = new Cell(position, value, neighbours);
				cells[x++] = finalCell;

			}
		}
		readmaze.setCells(cells);
		readmaze.setId_mov(id_movs);
		readmaze.setMovs(movs);
		readmaze.setCols(cols);
		readmaze.setRows(rows);
		readmaze.setMax_n(max_n);
		if(readmaze.checkMaze()){
			System.out.println("Your file has been read and the maze is correctly generated!");
			return readmaze;
			
		}
		else {
			System.out.println("Your file has been read but the maze wasn't correct!");
			System.exit(-1);
			return null;
		}
		
	}
	
	//It transform the string that we get from the json file into the int array that we use as position.
	public int[] getPosition(String positionstr) {
		
		int [] finalposition = new int[2];
		String auxiliarNumber="";
		int x=0;
        for (int i = 0; i < positionstr.length(); i++) {
            if (Character.isDigit(positionstr.charAt(i))) {
                auxiliarNumber += positionstr.charAt(i);
            } else {
                if (!auxiliarNumber.isEmpty()) {
                    finalposition[x++]=Integer.parseInt(auxiliarNumber);
                    auxiliarNumber="";
                }
            }
        }
		return finalposition;
	}

}
