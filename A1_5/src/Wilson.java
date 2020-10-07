import java.util.Random;

public class Wilson {
	int width = 40;
	int height = 50;
	
	Random random = new Random();
	
	private String [][] grid;
	private int remaining;
	
	for (int i = 0; i < grid.lenght(); i++) {
		for (int j = 0; j < grid[].lenght(); j++) {
			grid[i][j] = "OUT";
		}
	}
	
	while (remaining > 0) {
		grid [random.nextInt(width)] [random.nextInt(height)] = "IN";
		remaining = width * height - 1;	
	}

}
