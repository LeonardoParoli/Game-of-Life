package com.gof.game.grid;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ClassicGridRenderer implements GridRenderer {

	@Override
	public void render(ShapeRenderer renderer, HashMap<String,Cell> livingCells, int gridPositionX, int gridPositionY) {
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		livingCells.forEach((key,value)->{
			switch(value.getType()) {
			case NORMAL:
				renderer.setColor(Color.GREEN);
				break;
			case CORRUPTOR:
				renderer.setColor(Color.RED);
				break;
			case IMMORTAL:
				renderer.setColor(Color.YELLOW);
				break;
			}
			renderer.rect(value.getx() + gridPositionX, value.gety() + gridPositionY, 1, 1);
		});	
		renderer.end();
	}

}
