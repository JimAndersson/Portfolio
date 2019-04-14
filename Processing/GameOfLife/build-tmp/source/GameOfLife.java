import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class GameOfLife extends PApplet {

private CellManager cellManager;
private int cellSize = 8;

public void setup() 
{
	frameRate(15);
	
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
public class Cell
{
	private int x, y;
	private int size;
	private boolean alive;
	private int neighbours;
	private int age;

	public Cell(int x, int y, int size, boolean alive)
	{
		this.x = x * size;
		this.y = y * size;
		this.size = size;
		this.alive = alive;
	}


	//Private

	private void checkConditions()
	{
		if (neighbours < 2 || neighbours > 3)
			die();
		else if (!alive && neighbours == 3)
			alive = true;
	}

	private void die()
	{
		alive = false;
		age = 0;
	}

	private void setColor()
	{
		if (age > 3) fill(50, 250, 50);
		else fill(100);
	}


	//Public

	public boolean getAlive()
	{
		return alive;
	}

	public void setNeighbours(int value)
	{
		neighbours = value;
	}

	public void drawCell()
	{
		checkConditions();
		
		if (!alive) return;

		age++;
		setColor();
		rect(x, y, size, size);
	}
}
public class CellManager
{
	private Cell[][] grid;
	private int gridWidth, gridHeight;
	private float cellSpawnAliveChance = 0.5f;

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
  public void settings() { 	size(1024, 768, P2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "GameOfLife" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
