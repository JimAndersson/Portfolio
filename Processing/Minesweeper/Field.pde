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
			textSize(size * 0.5);
			textAlign(CENTER);
			text(adjecentMines, x * size + size * 0.75, y * size + size);
		}
	}

}