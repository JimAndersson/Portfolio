CharacterManager characterManager;

void setup()
{
	size(1024, 768);
	characterManager = new CharacterManager(100);
}

void draw()
{
	clearScreen();
	characterManager.update();
}

void clearScreen()
{
	background(255);
}