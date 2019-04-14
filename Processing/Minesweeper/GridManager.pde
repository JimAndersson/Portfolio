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
				if (random(0, 1) < 0.05) {
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