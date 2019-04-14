public class CharacterManager 
{
	Human[] characters;

	public CharacterManager (int numberOfCharacters) 
	{
		characters = new Human[numberOfCharacters];

		for (int i = 0; i < numberOfCharacters; ++i) {
			Vector position = new Vector(random(width), random(height));
			characters[i] = new Human(position);
		}

		characters[0].turnZombie();
	}

	public void update() 
	{
		for (Human character : characters) {
			character.update();
			if(character.isZombie){
				checkCollision(character);
			}
		}
	}

	private void checkCollision(Human character)
	{
		for (Human otherCharacter : characters){
			if(!otherCharacter.isZombie && RoundCollision(character, otherCharacter)){
				if(int(random(20)) == 1)
					otherCharacter.turnZombie();
				else {
					character.setDirection();
				}
			}
		}
	}

	private boolean RoundCollision(Human firstHuman, Human secondHuman)
	{
		float x1 = firstHuman.position.x; 
		float y1 = firstHuman.position.y; 
		float size1 = firstHuman.size / 2; 

		float x2 = secondHuman.position.x; 
		float y2 = secondHuman.position.y;
		float size2 = secondHuman.size / 2;

		float maxDistance = size1 + size2;

		if(abs(x1 - x2) > maxDistance || abs(y1 - y2) > maxDistance) {
			return false;
		}
		else if(dist(x1, y1, x2, y2) > maxDistance) {
			return false;
		}
		else {
			return true; 
		}
	}

}