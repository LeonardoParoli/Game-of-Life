package com.gof.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gof.game.screen.MainMenuScreen;

public class GameRunner extends Game {
	public ShapeRenderer shapeRenderer;
	public SpriteBatch batch;
	public BitmapFont font;
	private int height = 800;
	private int width = 600;
	
	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		batch = new SpriteBatch();
		
		// Use LibGDX's default Arial font.
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this, height, width));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
		batch.dispose();
		font.dispose();
	}
}
