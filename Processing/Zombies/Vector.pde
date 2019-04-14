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