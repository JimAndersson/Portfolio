public static boolean win = false;
public static boolean loose = false;
boolean newGame = false;
GridManager gridManager;

void setup () {
	size(512, 512);
	gridManager = new GridManager();
	startGame();
}

void startGame () {
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

void draw () {
	if (win || loose)
		endGame();
}

void mouseClicked () {
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

void endGame () {
	gridManager.openAll();
	gridManager.updateField();

	fill(75, 75, 75, 115);
	rectMode(RADIUS);
	rect(width * 0.5, height * 0.3, 300, 75);

	fill(250);
	textSize(48);
	textAlign(CENTER);

	if (win)
		text("You win!", width * 0.5, height * 0.33);
	
	if (loose)
		text("Game over!", width * 0.5, height * 0.33);

	newGame = true;
	loose = false;
	win = false;
}

void clearScreen () {
	background(0);
}