package com.gof.game.grid;

public class Neighbour {
	private short number;
	private boolean corruptible;
	
	public Neighbour() {
		number=0;
		corruptible=false;
	}
	
	public Neighbour(short number, boolean corruptible) {
		this.number=number;
		this.corruptible=corruptible;
	}
	
	public boolean isCorruptible() {
		return corruptible;
	}
	
	public int  getNumber() {
		return number;
	}
	
	public void setNumber(short number) {
		this.number = number;
	}
	
	public void corrupt() {
		corruptible = true;
	}
}
