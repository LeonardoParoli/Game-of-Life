package com.gof.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gof.game.GameRunner;
import com.gof.game.grid.Grid;
import com.gof.game.util.BackgroundColor;

public class TutorialScreen implements Screen {
	final GameRunner game;
	private Stage stage;
	private Grid grid;
	private long lastUpdateTime;
	int height;
	int width;

	public TutorialScreen(final GameRunner game, int height, int width) {
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

		float cw = sw * 0.90f;
		float ch = sh * 0.90f;

		// container position
		tableContainer.setSize(cw, ch);
		tableContainer.setPosition((sw - cw) / 2.0f, (sh - ch) / 2.0f);
		tableContainer.fillX().fillY();

		//line widget
		Label line1 = new Label("_______________________________________",skin);
		line1.setAlignment(Align.center);
		Label line2 = new Label("_______________________________________",skin);
		line2.setAlignment(Align.center);
		
		// screen title
		Table menuTable = new Table(skin);
		Label topTitle = new Label("--- Tutorial ---", skin);
		topTitle.setFontScale(2.0f);
		topTitle.setAlignment(Align.center);

		// descriptions
		Label mainControls = new Label("Main controls: \n"
				+ "- Left mouse click: spawn cell of selected type on cursor location.\n"
				+ "- Right mouse click: despawn cell on cursor location.\n"
				+ "- Arrow keys: pan camera left, right, up and down.",skin);
		Label gameControls = new Label ("",skin);
		Label otherControls = new Label("Other controls: \n"
				+ "- N : select normal cell brush type.\n"
				+ "- K : select corruptor cell brush type.\n"
				+ "- I : select immortal cell brush type.\n"
				+ "- Z : toggle camera zoom on cursor.\n"
				+ "- P : toggle pause on simulation.\n"
				+ "- C : clear grid.", skin);
		
		// back button widget
		TextButton backButton = new TextButton("Back", skin);
		backButton.addListener(new ClickListener() {
			public void clicked(InputEvent e, float x, float y) {
				dispose();
				game.setScreen(new OptionScreen(game,height,width));
			}
		});

		// populating table
		menuTable.row().top();
		menuTable.add(topTitle).center();
		menuTable.row().top();
		menuTable.add(line1).center();
		menuTable.row().center().pad(20);
		menuTable.add(mainControls).center();
		menuTable.row().center();
		menuTable.add(gameControls).center();
		menuTable.row().center();
		menuTable.add(otherControls).center();
		menuTable.row().center().pad(20);
		menuTable.add(line2).center();
		menuTable.row().bottom();
		menuTable.add(backButton).center();

		// table container setup
		tableContainer.setActor(menuTable);
		BackgroundColor backgroundColor = new BackgroundColor("white_color_texture.png");
		backgroundColor.setColor(0, 0, 0, 220);
		tableContainer.setBackground(backgroundColor);

		// adding container to stage
		stage.addActor(tableContainer);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void show() {
		
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
		
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}
}
