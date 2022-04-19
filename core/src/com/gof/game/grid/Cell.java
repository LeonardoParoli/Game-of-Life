package com.gof.game.grid;

/*
 *  The Cell class is responsible of holding the information regarding the Grid's cell position, its CellType, if it's alive by exposing getter/setter methods.
 *  Each Cell also exposes a method to convert the object's information into a String (toString()), or create a new Cell object through the static method fromString(String cellString).
 */
public class Cell {
	private int x;
	private int y;
	private CellType type;
	private boolean alive;

	public Cell(int x, int y, CellType type) {
		this.x = x;
		this.y = y;
		this.type = type;
		alive = false;
	}

	public Cell(int x, int y, CellType type, boolean alive) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.alive = alive;
	}

	public CellType getType() {
		return type;
	}

	public int getx() {
		return x;
	}

	public int gety() {
		return y;
	}

	public boolean isAlive() {
		return alive;
	}

	public void live(CellType type) {
		alive = true;
		this.type = type;
	}

	public void die() {
		alive = false;
	}

	public String toString() {
		return "(" + x + "," + y + "," + type + "," + alive + ")";
	}

	public static Cell fromString(String cellString) {
		String subString =cellString.substring(1, cellString.length() - 1);
		String[] temp = subString.split(",");
		int cellX = Integer.parseInt(temp[0]);
		int cellY = Integer.parseInt(temp[1]);
		CellType cellType = CellType.valueOf(temp[2]);
		boolean isAlive = Boolean.valueOf(temp[3]);
		return new Cell(cellX, cellY, cellType, isAlive);
	}
}
