package com.gof.game.screen;

import java.io.File;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gof.game.grid.Grid;
import com.gof.game.GameRunner;
import com.gof.game.util.BackgroundColor;
/*
 * The OptionScreen class implements a LibGDX Screen and an OptionScreen object is created following the disposal of an IntroScreen object.
 * It renders a menu composed of tables of buttons, enabling the user to:
 * -create a new simulation
 * -select a specific grid size for a new simulation
 * -load a previously saved simulation
 * -choose a previously saved simulation from a list.
 * It also handles a randomly generated Game of Life simulation in the background.
 * The OptionScreen class represents a View element in the Model View Controller architectural pattern.
 */
public class OptionScreen implements Screen {
	final GameRunner game;
	private Stage stage;
	private Grid grid;
	private long lastUpdateTime;
	int height;
	int width;

	public OptionScreen(final GameRunner game, final int height, int width) {
		this.game = game;
		this.height = height;
		this.width = width;
		this.grid = new Grid(height, width);

		grid.backgroundRandomizer();

		stage = new Stage(new ScreenViewport());

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
		Label topLabel = new Label("--- Main Menu ---", skin);
		topLabel.setFontScale(2.0f);
		topLabel.setAlignment(Align.center);

		// grid size selection widget
		Label gridSizeLabel = new Label("Grid Size:", skin);
		gridSizeLabel.setAlignment(Align.center);
		SelectBox<String> gridSizeSelection = new SelectBox<>(skin);
		String[] gridSizes = new String[4];
		gridSizes[0] = "50x50";
		gridSizes[1] = "100x100";
		gridSizes[2] = "300x300";
		gridSizes[3] = "800x600";
		gridSizeSelection.setItems(gridSizes);
		gridSizeSelection.setAlignment(Align.center);

		//simulation selection
		Label simulationLoadLabel = new Label("Simulation to load:", skin);
		simulationLoadLabel.setAlignment(Align.center);
		SelectBox<String> simulationSelection = new SelectBox<>(skin);
		simulationSelection.setMaxListCount(3);
		String[] simulations = loadSimulations();
		simulationSelection.setItems(simulations);
		simulationSelection.setAlignment(Align.center);
		
		// buttons widget
		TextButton newSimulationButton = new TextButton("New Simulation", skin);
		newSimulationButton.addListener(new ClickListener() {
			public void clicked(InputEvent e, float x, float y) {
				String[] gridSize = gridSizeSelection.getSelected().split("x");
				int gridX = Integer.parseInt(gridSize[0]);
				int gridY = Integer.parseInt(gridSize[1]);
				game.setScreen(new GameScreen(game, gridX, gridY));
				dispose();
			}
		});
		TextButton loadSimulationButton = new TextButton("Load Simulation", skin);
		if(simulationSelection.getSelection().isEmpty()) {
			loadSimulationButton.setTouchable(Touchable.disabled);
		}
		loadSimulationButton.addListener(new ClickListener() {
			public void clicked(InputEvent e, float x, float y) {
				FileHandle saveFile = Gdx.files.local("data/saves/" + simulationSelection.getSelected());
				Grid grid;
				try {
					grid = Grid.fromFile(saveFile);
					game.setScreen(new GameScreen(game,grid.getHeight()+20, grid.getWidth()+20, grid));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				dispose();
			}
		});
		
		TextButton tutorialButton = new TextButton("Tutorial", skin);
		tutorialButton.addListener(new ClickListener() {
			public void clicked(InputEvent e, float x, float y) {
				dispose();
				game.setScreen(new TutorialScreen(game,height,width));
			}
		});
		Label credits = new Label("Leonardo Paroli, 2021-2022", skin);
		credits.setAlignment(Align.center);

		// populating table
		menuTable.row().expand().top();
		menuTable.add(topLabel).fillX().center().colspan(2);
		menuTable.row().expand().fillX().center();
		menuTable.add(newSimulationButton).center().colspan(2);
		menuTable.row().expand().fillX().center();
		menuTable.add(gridSizeLabel).right().spaceTop(10).pad(20);
		menuTable.add(gridSizeSelection).left().spaceTop(10).pad(20);
		menuTable.row().expand().fillX().center();
		menuTable.add(line1).center().colspan(2);
		menuTable.row().expand().fillX().center();
		menuTable.add(loadSimulationButton).center().colspan(2);
		menuTable.row().expand().fillX().center();
		menuTable.add(simulationLoadLabel).right().fillX().spaceTop(10).pad(20);
		menuTable.add(simulationSelection).left().spaceTop(10).pad(20);
		menuTable.row().expand().fillX().center();
		menuTable.add(line2).center().colspan(2);
		menuTable.row().center();
		menuTable.add(tutorialButton).center().fillX().colspan(2);
		menuTable.row().expand().fillX().bottom().pad(20);
		menuTable.add(credits).center().bottom().colspan(2);

		// table container setup
		tableContainer.setActor(menuTable);
		BackgroundColor backgroundColor = new BackgroundColor("white_color_texture.png");
		backgroundColor.setColor(0, 0, 0, 220);
		tableContainer.setBackground(backgroundColor);

		// adding container to stage
		stage.addActor(tableContainer);
		Gdx.input.setInputProcessor(stage);
	}

	private String[] loadSimulations() {
		File folder = Gdx.files.local("data/saves").file();
		return folder.list();
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
		stage.dispose();
	}
}
