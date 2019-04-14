public class CellManager
{
	private Cell[][] grid;
	private int gridWidth, gridHeight;
	private float cellSpawnAliveChance = 0.1f;

	public CellManager(int cellSize)
	{
		gridWidth = width / cellSize;		
		gridHeight = height / cellSize;		

		createGrid(cellSize);
	}


	//Private

	private void createGrid(int cellSize)
	{
		grid = new Cell[gridWidth][gridHeight];

		for (int x = 0; x < gridWidth; ++x)
			for (int y = 0; y < gridHeight; ++y)
				grid[x][y] = new Cell(x, y, cellSize, random(0, 1) < cellSpawnAliveChance);
	}

	private int getNeighbourCount(int x, int y)
	{
		int neighbours = 0;

		for (int nX = -1; nX < 2; ++nX)
			for (int nY = -1; nY < 2; ++nY)
				if (isOnGrid(x + nX, y + nY) && !(nX == 0 && nY == 0))
					if (grid[x + nX][y + nY].getAlive())
						neighbours++;

		return neighbours;
	}

	private boolean isOnGrid(int x, int y)
	{
		if (x < 0 || y < 0 || x > gridWidth - 1 || y > gridHeight - 1) 
			return false;
		else
			return true;
	}


	//Public

	public void checkNeighbours()
	{
		for (int x = 0; x < gridWidth; ++x)
			for (int y = 0; y < gridHeight; ++y)
				grid[x][y].setNeighbours(getNeighbourCount(x, y));
	}

	public void drawCells()
	{
		for (int x = 0; x < gridWidth; ++x)
			for (int y = 0; y < gridHeight; ++y)
					grid[x][y].drawCell();
	}
}