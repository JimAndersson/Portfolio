public class Human 
{
	public Vector position;
	public int size;
	public boolean isZombie = false;

	private float movementSpeed;
	private Vector direction;
	private color myColor;

	public Human (Vector position) 
	{
		this.size = int(random(15, 30));
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