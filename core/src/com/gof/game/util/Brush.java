package com.gof.game.util;

import com.gof.game.grid.CellType;

/*
 * Support class holding the information about the user input's brush type and size.
 * Potential abstract class to implement new kinds of brush.
 */
public class Brush {
	private int size;
	private CellType type;
	
	public Brush (int size, CellType type) {
		this.size = size;
		this.type = type;
	}
	
	public int getSize() {
		return size;
	}
	
	public CellType getType() {
		return type;
	}
	
	public void setType(CellType type) {
		this.type = type;
	}
	
	public void setSize(int size) {
		if(size >= 0) {
			this.size = size;
		}else {
			throw new IllegalArgumentException("Specified brush size is < 0.");
		}
	}
}
