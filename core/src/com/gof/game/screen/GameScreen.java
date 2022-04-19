package com.gof.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gof.game.grid.Grid;
import com.gof.game.hud.Hud;
import com.gof.game.GameRunner;


public class GameScreen implements Screen {

	final GameRunner game;
	private Music backgroundMusic;
	private OrthographicCamera camera;
	private Grid grid;
	private long lastUpdateTime;
	private long updateCycleTime = 10000000;
	private boolean paused;
	private boolean zoomed;
	private int height;
	private int width;
	private Hud hud;
	

	public GameScreen(GameRunner game, int height, int width) {
		this.game = game;
		this.paused = true;
		this.zoomed = false;
		this.grid = new Grid(height - 20, width - 20, 10, 10);
		
		this.width = width;
		this.height = height;

		// load background music
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background_music2.wav"));
		backgroundMusic.setLooping(true);

		// create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, height, width);

		// creates hud stage
		Stage stage = new Stage(new ScreenViewport());
		// stage.setDebugAll(true);
		
		this.hud = new Hud(this,stage);
	
		// first update
		grid.updateGrid();
	}

	public GameScreen(GameRunner game, int height, int width, Grid grid) {
		this.game = game;
		this.paused = true;
		this.zoomed = false;
		this.grid = grid;
		
		this.width = width;
		this.height = height;

		// load background music
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background_music2.wav"));
		backgroundMusic.setLooping(true);

		// creates the Camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, height, width);

		// creates HUD 
		Stage stage = new Stage(new ScreenViewport());
		this.hud = new Hud(this,stage);
	}

	@Override
	public void render(float delta) {
		// clear the screen with a dark blue color. The arguments to clear are RGBA.
		ScreenUtils.clear(0, 0, 0.2f, 1);

		// tells the camera to update its matrices.
		camera.update();

		// rendering is projected to camera matrix.
		game.batch.setProjectionMatrix(camera.combined);
		game.shapeRenderer.setProjectionMatrix(camera.combined);

		// check if grid update is needed
		if (TimeUtils.nanoTime() - lastUpdateTime > updateCycleTime && !paused) {
			grid.updateGrid();
			lastUpdateTime = TimeUtils.nanoTime();
		}

		// draws all alive cells.
		grid.render(game.shapeRenderer);
		
		// process user input
		hud.act();
		hud.draw();
	}

	

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		backgroundMusic.play();
		backgroundMusic.setVolume(0.25f);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		backgroundMusic.dispose();
	}
	
	public boolean togglePause() {
		paused = !paused;
		return paused;
	}
	
	public Grid getGrid() {
		return grid;
	}

	public void backToMenu() {
		game.setScreen(new OptionScreen(game, 800, 600));
		dispose();
	}
	
	public boolean pause(boolean paused) {
		return this.paused=paused;
	}
	
	public void volumeOff() {
		backgroundMusic.setVolume(0);
	}
	
	public void volumeOn() {
		backgroundMusic.setVolume(0.25f);
	}

	public void toggleZoom() {
		zoomed = !zoomed;
		if (zoomed) {
			camera.zoom = 0.5f;
			Vector3 mousePos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			Vector3 cameraPos = camera.position;
			camera.translate(mousePos.x - cameraPos.x, mousePos.y - cameraPos.y, mousePos.z - cameraPos.z);
		} else {
			camera.zoom = 1f;
			camera.setToOrtho(false, height, width);
		}
		camera.update();
	}
	
	public void centerZoom() {
		zoomed = !zoomed;
		if (zoomed) {
			camera.zoom = 0.5f;
		} else {
			camera.zoom = 1f;
			camera.setToOrtho(false, height, width);
		}
		camera.update();
	}

	public Camera getCamera() {
		return camera;
	}

	public void setUpdateCycleTime(long cycleTime) {
		this.updateCycleTime = cycleTime;
	}

	
	
}
