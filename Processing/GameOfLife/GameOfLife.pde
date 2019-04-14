private CellManager cellManager;
private int cellSize = 8;

public void setup() 
{
	frameRate(15);
	size(1024, 768, P2D);
	stroke(128);

	cellManager = new CellManager(cellSize);
}

public void draw()
{
	clearScreen();
	cellManager.checkNeighbours();
	cellManager.drawCells();
}

private void clearScreen() {
	background(64);
}