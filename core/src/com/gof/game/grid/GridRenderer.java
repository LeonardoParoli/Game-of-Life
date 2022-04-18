package com.gof.game.grid;

import java.util.HashMap;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface GridRenderer {
	public void render(ShapeRenderer renderer, HashMap<String,Cell> livingCells,  int gridPositionX, int gridPositionY);
}
