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

public class Minesweeper extends PApplet {

public static boolean win = false;
public static boolean loose = false;
boolean newGame = false;
GridManager gridManager;

public void setup () {
	
	gridManager = new GridManager();
	startGame();
}

public void startGame () {
	clearScreen();
	rectMode(CORNER);
	gridManager.initiateGrid();
	gridManager.placeMines();
	gridManager.adjecentField();
	gridManager.updateField();
	newGame = false;
	loose = false;
	win = false;
}

public void draw () {
	if (win || loose)
		endGame();
}

public void mouseClicked () {
	clearScreen();
	if (!loose || !win) {
		if (mouseButton == LEFT)
			gridManager.mouseClicked(mouseX, mouseY, "open");

		if (mouseButton == RIGHT) 
			gridManager.mouseClicked(mouseX, mouseY, "flag");
	}
	gridManager.updateField();
	gridManager.checkWinConditions();

	if (newGame)
		startGame();
}

public void endGame () {
	gridManager.openAll();
	gridManager.updateField();

	fill(75, 75, 75, 115);
	rectMode(RADIUS);
	rect(width * 0.5f, height * 0.3f, 300, 75);

	fill(250);
	textSize(48);
	textAlign(CENTER);

	if (win)
		text("You win!", width * 0.5f, height * 0.33f);
	
	if (loose)
		text("Game over!", width * 0.5f, height * 0.33f);

	newGame = true;
	loose = false;
	win = false;
}

public void clearScreen () {
	background(0);
}
public class Field {
	private int x, y, size;
	public boolean isMine;
	public boolean opened;
	public boolean flagged;
	public int adjecentMines;

	public Field (int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = width / size;
		this.isMine = isMine;
		this.opened = false;
		this.flagged = false;
	}

	public void draw () {
		if (!opened)
			fill(150);

		if (opened) {
			if (isMine)
				fill(200, 50, 50);
			else 
				fill(200);	
		}

		if (flagged)
			fill(50, 50, 200);

		rect(x * size + 5, y * size + 5, size, size);

		if (opened && adjecentMines != 0) {
			fill(20 * adjecentMines * 2, 20, 20);
			textSize(size * 0.5f);
			textAlign(CENTER);
			text(adjecentMines, x * size + size * 0.75f, y * size + size);
		}
	}

}
public class GridManager {
	int gridSize;
	Field[][] mineField;
	int mines;

	public GridManager () {
		gridSize = 20;
		mineField = new Field[gridSize][gridSize];
	}

	public void initiateGrid () {
		mines = 0;
		for (int y = 0; y < gridSize; ++y) 
			for (int x = 0; x < gridSize; ++x) 
				mineField[x][y] = new Field(x, y, gridSize);
	}

	public void placeMines () {
		for (int y = 0; y < gridSize; ++y) 
			for (int x = 0; x < gridSize; ++x) 
				if (random(0, 1) < 0.05f) {
					mineField[x][y].isMine = true;
					mines++;
				}
	}

	public void adjecentField() {
		for (int y = 0; y < gridSize; ++y) 
			for (int x = 0; x < gridSize; ++x) 
				for (int adjecentX = -1; adjecentX <= 1; adjecentX++)
					for (int adjecentY = -1; adjecentY <= 1; adjecentY++)
						if(	(x + adjecentX) >= 0 && 
							(y + adjecentY) >= 0 && 
							(x + adjecentX) < gridSize && 
							(y + adjecentY) < gridSize && 
							!(adjecentX == 0 && adjecentY == 0)
							)
							if (!mineField[x][y].isMine && mineField[x + adjecentX][y + adjecentY].isMine)
								mineField[x][y].adjecentMines++;
	}
	
	public void mouseClicked (int mousex, int mousey, String action) {
		int size = width / gridSize;
		for (int y = 0; y < gridSize; ++y) {
			for (int x = 0; x < gridSize; ++x) {
				if (mousex < x * size + size && 
					mousex > x * size &&
					mousey < y * size + size && 
					mousey > y * size) {

					if (!mineField[x][y].opened) {
						if (action == "open") {
							mineField[x][y].opened = true;
							mineField[x][y].flagged = false;
							if(mineField[x][y].adjecentMines == 0)
								openZeros(x, y);
						}
						if (action == "flag") {
							if (!mineField[x][y].flagged)
								mineField[x][y].flagged = true;
							else 
								mineField[x][y].flagged = false;
						}
					}
				}
			}
		}
	}

	private void openZeros (int x, int y) {
		for (int adjecentX = -1; adjecentX <= 1; adjecentX++)
			for (int adjecentY = -1; adjecentY <= 1; adjecentY++) 
				if(	(x + adjecentX) >= 0 && 
					(y + adjecentY) >= 0 && 
					(x + adjecentX) < gridSize && 
					(y + adjecentY) < gridSize && 
					!(adjecentX == 0 && adjecentY == 0)
					) 
				{
					if (!mineField[x + adjecentX][y + adjecentY].opened && 
						!mineField[x + adjecentX][y + adjecentY].flagged) 
					{
						if (mineField[x + adjecentX][y + adjecentY].adjecentMines > 0)
							mineField[x + adjecentX][y + adjecentY].opened = true;
						
						if (mineField[x + adjecentX][y + adjecentY].adjecentMines == 0) {
							mineField[x + adjecentX][y + adjecentY].opened = true;
							openZeros(x + adjecentX, y + adjecentY);
						}
					}
				}

	}

	public void updateField () {
		for (int y = 0; y < gridSize; ++y) 
			for (int x = 0; x < gridSize; ++x)
				mineField[x][y].draw();
		}

	public void openAll () {
		for (int y = 0; y < gridSize; ++y) 
			for (int x = 0; x < gridSize; ++x) 
				mineField[x][y].opened = true;
	}

	public void checkWinConditions () {
		int notOpened = 0;
		int minesFlagged = 0;

		for (int y = 0; y < gridSize; ++y) 
			for (int x = 0; x < gridSize; ++x){
				if (!mineField[x][y].opened)
					notOpened++;

				if (mineField[x][y].flagged && mineField[x][y].isMine)
					minesFlagged++;

				if (mineField[x][y].isMine && mineField[x][y].opened)
					Minesweeper.loose = true;
			}

		if (minesFlagged == mines || notOpened == mines)
			Minesweeper.win = true;

	}
}
  public void settings() { 	size(512, 512); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Minesweeper" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
