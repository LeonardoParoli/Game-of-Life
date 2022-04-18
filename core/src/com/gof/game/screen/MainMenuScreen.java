package com.gof.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gof.game.grid.Grid;
import com.gof.game.GameRunner;
import com.gof.game.util.BackgroundColor;

public class MainMenuScreen implements Screen {
	final GameRunner game;
	private Stage stage;
	private Grid grid;
	private long lastUpdateTime;
	int height;
	int width;

	public MainMenuScreen(final GameRunner game, int height, int width) {
		this.game = game;
		this.height = height;
		this.width = width;
		this.grid = new Grid(height, width);
		
		grid.backgroundRandomizer();

		stage = new Stage(new ScreenViewport());
		//stage.setDebugAll(true);

		Skin skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));
		Container<Table> tableContainer = new Container<Table>();

		float sw = Gdx.graphics.getWidth();
		float sh = Gdx.graphics.getHeight();

		float cw = sw * 0.50f;
		float ch = sh * 0.99f;

		tableContainer.setSize(cw, ch);
		tableContainer.setPosition((sw - cw) / 2.0f, (sh - ch) / 2.0f);
		tableContainer.fillX().fillY();

		Table menuTable = new Table(skin);
		Label topLabel = new Label("The Game of Life", skin);
		topLabel.setFontScale(1.5f);
		topLabel.setAlignment(Align.center);
		String menuDescription = "-------------------------------- \n"
				+ "A cellular automaton developed by the \n "
				+ "mathematician John Horton Conway in 1970,\n "
				+ "it's the first example of a zero-player-game\n"
				+ "where evolution is determined only by the\n"
				+ "initial conditions.\n\n"
				+ "Much like the cells in the game,\n "
				+ "the orginal idea has evolved in time: \n"
				+ "this is one of those evolutions...\n"
				+ "-------------------------------- \n "
				+ "\n \n Click anywhere to play...";
		Label anotherLabel = new Label(menuDescription, skin);
		anotherLabel.setAlignment(Align.center);
		Label credits = new Label("Leonardo Paroli, 2021-2022", skin);
		credits.setAlignment(Align.center);
		
		menuTable.row().expand().top();
		menuTable.add(topLabel).fillX().center();
		menuTable.row().expand().fillX().center();
		menuTable.add(anotherLabel).fillX().center();
		menuTable.row().expand().fillX().bottom();
		menuTable.add(credits).center().bottom();
			
		tableContainer.setActor(menuTable);
		BackgroundColor backgroundColor = new BackgroundColor("white_color_texture.png");
		backgroundColor.setColor(0, 0, 0, 220);
		tableContainer.setBackground(backgroundColor);
		
		stage.addActor(tableContainer);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		// check if grid update is needed
		if (TimeUtils.nanoTime() - lastUpdateTime > 10000000) {
			grid.updateGrid();
			lastUpdateTime = TimeUtils.nanoTime();
		}

		// draws all alive cells in the background
		grid.render(game.shapeRenderer);

		stage.act(delta);
		stage.draw();

		if (Gdx.input.isTouched()) {
			game.setScreen(new OptionScreen(game, height, width));
			stage.dispose();
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
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
	}

}
