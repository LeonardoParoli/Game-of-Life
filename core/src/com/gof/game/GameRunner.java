package com.gof.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gof.game.screen.IntroScreen;

/*
 * The GameRunner class has the responsibility to create and hold the application window context, 
 * and is an entry point for the LibGdx game engine control methods.
 */

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
		this.setScreen(new IntroScreen(this, height, width));
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
