package com.gof.game.hud;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gof.game.grid.CellType;
import com.gof.game.screen.GameScreen;
import com.gof.game.util.BackgroundColor;
import com.gof.game.util.Brush;

public class Hud {
	
	private Stage stage;
	private Brush brush;
	private Table menuTable;
	private GameScreen gameScreen;
	private Label pausedLabel;
	private boolean volumeOn = true;
	private Table inputTable;
	
	public Hud(GameScreen gameScreen, Stage stage) {
		this.stage = stage;
		this.brush = new Brush(0, CellType.NORMAL);
		this.gameScreen = gameScreen;
		
		Skin skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));
		Container<Table> tableContainer = new Container<Table>();

		float sw = Gdx.graphics.getWidth();
		float sh = Gdx.graphics.getHeight();

		tableContainer.setSize(sw, sh);
		tableContainer.setPosition(0, 0);
		tableContainer.fillX().fillY();

		// clear Button
		TextButton clearButton = new TextButton("Clear",skin);
		clearButton.addListener(new ClickListener() {
			public void clicked(InputEvent e, float x, float y) {
				gameScreen.getGrid().clear();
			}
		});
		
		// Paused Label
		pausedLabel = new Label("<<- Paused ->>",skin); 
		pausedLabel.setFontScale(2f);
		pausedLabel.setColor(Color.GREEN);
		
		// Back Button
		TextButton backButton = new TextButton("Back", skin);
		backButton.addListener(new ClickListener() {
			public void clicked(InputEvent e, float x, float y) {
				stage.dispose();
				gameScreen.backToMenu();
			}
		});

		// Utility Buttons Table setup
		Table utilityButtonsTable = new Table(skin);
		utilityButtonsTable.center();
		TextButton pauseButton = new TextButton(" Pause (P) ", skin);
		pauseButton.addListener(new ClickListener() {
			public void clicked(InputEvent e, float x, float y) {
				pausedLabel.setVisible(gameScreen.togglePause());
				pausedLabel.isVisible();
				e.stop();
			}
		});
		utilityButtonsTable.add(pauseButton).expand().center();
		utilityButtonsTable.row();
		TextButton zoomButton = new TextButton(" Zoom (Z) ", skin);
		zoomButton.addListener(new ClickListener() {
			public void clicked(InputEvent e, float x, float y) {
				gameScreen.centerZoom();
				e.stop();
			}
		});
		utilityButtonsTable.add(zoomButton).expand().center();

		// Brush Type Buttons Table setup
		Table brushTypeButtonsTable = new Table(skin);
		brushTypeButtonsTable.center();
		Texture greenTexture = new Texture(Gdx.files.internal("greenCellTexture.jpg"));
		Texture redTexture = new Texture(Gdx.files.internal("redCellTexture.jpg"));
		Texture yellowTexture = new Texture(Gdx.files.internal("yellowCellTexture.jpg"));
		Texture greenSelectedTexture = new Texture(Gdx.files.internal("greenSelectedCellTexture.jpg"));
		Texture redSelectedTexture = new Texture(Gdx.files.internal("redSelectedCellTexture.jpg"));
		Texture yellowSelectedTexture = new Texture(Gdx.files.internal("yellowSelectedCellTexture.jpg"));
		Drawable greenCellDrawable = new TextureRegionDrawable(greenTexture);
		greenCellDrawable.setMinHeight(50);
		greenCellDrawable.setMinWidth(50);
		Drawable redCellDrawable = new TextureRegionDrawable(redTexture);
		redCellDrawable.setMinHeight(50);
		redCellDrawable.setMinWidth(50);
		Drawable yellowCellDrawable = new TextureRegionDrawable(yellowTexture);
		yellowCellDrawable.setMinHeight(50);
		yellowCellDrawable.setMinWidth(50);
		Drawable greenSelectedCellDrawable = new TextureRegionDrawable(greenSelectedTexture);
		greenSelectedCellDrawable.setMinHeight(50);
		greenSelectedCellDrawable.setMinWidth(50);
		Drawable redSelectedCellDrawable = new TextureRegionDrawable(redSelectedTexture);
		redSelectedCellDrawable.setMinHeight(50);
		redSelectedCellDrawable.setMinWidth(50);
		Drawable yellowSelectedCellDrawable = new TextureRegionDrawable(yellowSelectedTexture);
		yellowSelectedCellDrawable.setMinHeight(50);
		yellowSelectedCellDrawable.setMinWidth(50);
		ImageButton greenCellButton = new ImageButton(greenCellDrawable,greenSelectedCellDrawable,greenSelectedCellDrawable);
		greenCellButton.addListener(new TextTooltip("Cell type:\n Normal.", skin));
		greenCellButton.setWidth(50);
		greenCellButton.setHeight(50);
		ImageButton redCellButton = new ImageButton(redCellDrawable,redSelectedCellDrawable,redSelectedCellDrawable);
		redCellButton.addListener(new TextTooltip("Cell type:\n Corruptor.", skin));
		redCellButton.setWidth(50);
		redCellButton.setHeight(50);
		ImageButton yellowCellButton = new ImageButton(yellowCellDrawable,yellowSelectedCellDrawable,yellowSelectedCellDrawable);
		yellowCellButton.addListener(new TextTooltip("Cell type:\n Immortal.", skin));
		yellowCellButton.setWidth(50);
		yellowCellButton.setHeight(50);
		greenCellButton.addListener(new ClickListener() {
			public void clicked(InputEvent e, float x, float y) {
				yellowCellButton.setChecked(false);
				redCellButton.setChecked(false);
				greenCellButton.setChecked(true);
				brush.setType(CellType.NORMAL);
				super.clicked(e, x, y);
			}
		});
		redCellButton.addListener(new ClickListener() {
			public void clicked(InputEvent e, float x, float y) {
				yellowCellButton.setChecked(false);
				redCellButton.setChecked(true);
				greenCellButton.setChecked(false);
				brush.setType(CellType.CORRUPTOR);
				super.clicked(e, x, y);
			}
		});
		yellowCellButton.addListener(new ClickListener() {
			public void clicked(InputEvent e, float x, float y) {
				yellowCellButton.setChecked(true);
				redCellButton.setChecked(false);
				greenCellButton.setChecked(false);
				brush.setType(CellType.IMMORTAL);
				super.clicked(e, x, y);
			}
		});
		greenCellButton.setChecked(true);
		
		Label sizeLabel = new Label("Brush size:",skin);
		Slider sizeSlider = new Slider(0,9,1,false,skin);
		sizeSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				brush.setSize((int) sizeSlider.getValue());
				event.stop();
			}
		});
		brushTypeButtonsTable.add(greenCellButton).expandX();
		brushTypeButtonsTable.add(redCellButton).expandX();
		brushTypeButtonsTable.add(yellowCellButton).expandX();
		brushTypeButtonsTable.row().pad(10);
		brushTypeButtonsTable.add(sizeLabel);
		brushTypeButtonsTable.add(sizeSlider).expandX().colspan(2);
		
		// Simulation Speed Slider Table setup
		Table slidersTable = new Table(skin);
		slidersTable.center();
		Label simulationSpeedLabel = new Label("Simulation speed:",skin);
		Slider simulationSpeedSlider = new Slider(0.000f,0.990f,0.001f,false,skin); 
		simulationSpeedSlider.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				gameScreen.setUpdateCycleTime((long) ((1f-simulationSpeedSlider.getValue())*1000000000));
				event.stop();
			}
		});
		simulationSpeedSlider.setValue(0.990f);
		slidersTable.add(simulationSpeedLabel).center();
		slidersTable.row().pad(10);
		slidersTable.add(simulationSpeedSlider).center();
		
		
		// Save Button and Save Dialog setup
		TextButton saveButton = new TextButton("Save", skin);
		Dialog saveDialog = new Dialog("",skin);
		saveDialog.getTitleTable().clear();
		saveDialog.getTitleTable().add(new Label("Grid save", skin)).center().expand().pad(10);
		saveDialog.getContentTable().add(new Label("Successfully saved!",skin)).center().expand().pad(10);
		TextButton doneButton = new TextButton("Done",skin);
		doneButton.addListener(new ClickListener() {
			public void clicked(InputEvent e, float x, float y) {
				saveDialog.hide();
			}
		});
		saveDialog.getButtonTable().add(doneButton).center().expand().pad(10);
		
		saveButton.addListener(new ClickListener() {
			public void clicked(InputEvent e, float x, float y) {
				gameScreen.pause(true);
				pausedLabel.setVisible(true);
				pausedLabel.isVisible();
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
				String fileName = "Save#" +sdf.format(date);
				FileHandle saveFile = Gdx.files.local("data/saves/" + fileName +".txt");
				gameScreen.getGrid().saveToFile(saveFile);
				saveDialog.show(stage);
				e.stop();
			}
		});
		
		

		//Volume Button setup
		Texture volumeTexture = new Texture(Gdx.files.internal("volumeTexture.jpg"));
		Drawable volumeDrawable = new TextureRegionDrawable(volumeTexture);
		Texture volumeOffTexture = new Texture(Gdx.files.internal("volumeOffTexture.jpg"));
		Drawable volumeOffDrawable = new TextureRegionDrawable(volumeOffTexture);
		ImageButton volumeButton = new ImageButton(volumeDrawable, volumeOffDrawable, volumeOffDrawable);
		volumeButton.setHeight(30f);
		volumeButton.setWidth(30f);
		volumeButton.addListener(new ClickListener() {
			public void clicked(InputEvent e, float x, float y) {
				volumeOn = !volumeOn;
				if(volumeOn) {
					volumeButton.setChecked(false);
					gameScreen.volumeOn();
					
				}else {
					volumeButton.setChecked(true);
					gameScreen.volumeOff();
				}
				super.clicked(e, x, y);
			}
		});
		//top table setup
		Table topTable = new Table(skin);
		topTable.add(clearButton).expandX().top().left();
		topTable.add(pausedLabel).expandX().center();
		topTable.add(volumeButton).expandX().width(30).height(30).right();
		topTable.add(backButton).expandX().top().right();
		BackgroundColor topBackgroundColor = new BackgroundColor("white_color_texture.png");
		topBackgroundColor.setColor(0, 0, 0, 150);
		topTable.setBackground(topBackgroundColor);
		
		//bottom table setup
		Table bottomTable = new Table(skin);
		bottomTable.add(utilityButtonsTable).width(sw/5f).height(sh/5f);
		bottomTable.add(brushTypeButtonsTable).expandX().height(sh/5f);
		bottomTable.add(slidersTable).expandX().height(sh/5f);
		bottomTable.add(saveButton).width(sw/5f).height(sh/10f);
		BackgroundColor bottomBackgroundColor = new BackgroundColor("white_color_texture.png");
		bottomBackgroundColor.setColor(0 , 0, 0, 150);
		bottomTable.setBackground(bottomBackgroundColor);
		
		//input table setup
		inputTable = new Table(skin);
		inputTable.setTouchable(Touchable.enabled);
		inputTable.addListener(new InputListener() {
			int buttonPressed;
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Vector3 touchPos = new Vector3();
				int mouseCursorSize = 10;
				if(event.getButton() == Buttons.LEFT) {	
					buttonPressed = Buttons.LEFT;
					touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
					gameScreen.getCamera().unproject(touchPos);
					gameScreen.getGrid().lifeTouch(getBrush(), (int) touchPos.x - mouseCursorSize, (int) touchPos.y - mouseCursorSize);
				}
				if(event.getButton() == Buttons.RIGHT) {
					buttonPressed = Buttons.RIGHT;
					touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
					gameScreen.getCamera().unproject(touchPos);
					gameScreen.getGrid().deathTouch(getBrush(), (int) touchPos.x - mouseCursorSize, (int) touchPos.y- mouseCursorSize);
				}
				return true;
			}
			
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				Vector3 touchPos = new Vector3();
				int mouseCursorSize = 10;
				if(buttonPressed == Buttons.LEFT) {	
					touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
					gameScreen.getCamera().unproject(touchPos);
					gameScreen.getGrid().lifeTouch(getBrush(), (int) touchPos.x - mouseCursorSize, (int) touchPos.y - mouseCursorSize);
				}
				if(buttonPressed == Buttons.RIGHT) {
					touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
					gameScreen.getCamera().unproject(touchPos);
					gameScreen.getGrid().deathTouch(getBrush(), (int) touchPos.x - mouseCursorSize, (int) touchPos.y- mouseCursorSize);
				}
			}
		});
		inputTable.debug();
		
		//total table setup
		menuTable = new Table(skin);
		menuTable.row().top().expandX();
		menuTable.add(topTable).width(sw).top();
		menuTable.row().center().expand();
		menuTable.add(inputTable).fill();
		menuTable.row().bottom().expandX();
		menuTable.add(bottomTable).width(sw).height(sh/5f).bottom();
		
		
		tableContainer.setActor(menuTable);
		BackgroundColor backgroundColor = new BackgroundColor("white_color_texture.png");
		backgroundColor.setColor(0,0,0,0);
		tableContainer.setBackground(backgroundColor);
		
		stage.addActor(tableContainer);
		Gdx.input.setInputProcessor(stage);
	}
	
	public void act() {
		stage.act();
		handleInput();
	}
	
	public void draw(){
		stage.draw();
	}

	public void setBrushType(CellType type) {
		brush.setType(type);
	}

	public void setBrushSize(int i) {
		brush.setSize(i);	
	}

	public Brush getBrush() {
		return brush;
	}
	
	private void handleInput() {
		if (Gdx.input.isKeyJustPressed(Keys.Z)) {
			gameScreen.toggleZoom();
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			gameScreen.getCamera().translate(-2, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			gameScreen.getCamera().translate(2, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			gameScreen.getCamera().translate(0, -2, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			gameScreen.getCamera().translate(0, 2, 0);
		}
		if (Gdx.input.isKeyJustPressed(Keys.I)) {
			setBrushType(CellType.IMMORTAL);
		}
		if (Gdx.input.isKeyJustPressed(Keys.K)) {
			setBrushType(CellType.CORRUPTOR);
		}
		if (Gdx.input.isKeyJustPressed(Keys.N)) {
			setBrushType(CellType.NORMAL);
		}
		if(Gdx.input.isKeyJustPressed(Keys.B)) {
			stage.dispose();
			gameScreen.backToMenu();
		}
		if (Gdx.input.isKeyJustPressed(Keys.P)) {
			pausedLabel.setVisible(gameScreen.togglePause());
			pausedLabel.isVisible();
		}
		if (Gdx.input.isKeyJustPressed(Keys.C)) {
			gameScreen.getGrid().clear();
		}
	}
}
