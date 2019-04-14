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