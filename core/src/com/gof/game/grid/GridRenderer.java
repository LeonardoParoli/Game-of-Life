package com.gof.game.grid;

import java.util.HashMap;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/*
 * The GridRenderer interface exposes the render method to be called when a Grid object requires its cells to be rendered.
 */
public interface GridRenderer {
	public void render(ShapeRenderer renderer, HashMap<String,Cell> livingCells,  int gridPositionX, int gridPositionY);
}
