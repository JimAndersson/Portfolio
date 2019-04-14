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

public class Zombies extends PApplet {

CharacterManager characterManager;

public void setup()
{
	
	characterManager = new CharacterManager(100);
}

public void draw()
{
	clearScreen();
	characterManager.update();
}

public void clearScreen()
{
	background(255);
}
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
				if(PApplet.parseInt(random(20)) == 1)
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
public class Human 
{
	public Vector position;
	public int size;
	public boolean isZombie = false;

	private float movementSpeed;
	private Vector direction;
	private int myColor;

	public Human (Vector position) 
	{
		this.size = PApplet.parseInt(random(15, 30));
		this.position = position;
		this.movementSpeed = random(1, 2);
		setDirection();
		myColor = color(100, 100, random(150, 255));
	}

	private void setDirection()
	{
		direction = new Vector(random(-1, 1), random(-1, 1));
		direction.normalize();
	}

	public void update()
	{
		walk();
		screenWrap();
		draw();
	}

	private void walk() 
	{
		position.add(direction.mult(movementSpeed));
	}

	private void screenWrap()
	{
		position.x = abs(position.x + width) % width;
		position.y = abs(position.y + height) % height;
	}

	private void draw() 
	{
		fill(myColor);
		ellipse(position.x, position.y, size, size);
	}

	public void turnZombie()
	{
		isZombie = true;
		movementSpeed = movementSpeed / 2;
		myColor = color(100, random(150, 255), 100);
		setDirection();
	}

}
public class Vector 
{
	public float x;
	public float y;

	public Vector (float x, float y) 
	{
		this.x = x;
		this.y = y;
	}

	public Vector mult(float multiplier)
	{
		return new Vector(x * multiplier, y * multiplier);
	}


	public void add(Vector vectorToAdd)
	{
		x += vectorToAdd.x;
		y += vectorToAdd.y;
	}

	public void normalize()
	{
		float length = (float)Math.sqrt(x * x + y * y);
      	if (length != 0) {
        	x = x/length;
        	y = y/length;
      	}
	}
}
  public void settings() { 	size(1024, 768); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Zombies" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
