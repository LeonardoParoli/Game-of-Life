package com.gof.game.grid;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gof.game.util.Brush;

/*
 * The Grid class is responsible of holding the information about the simulation cells, their position, their state and to properly update 
 * each simulation cycle through the combined application of the Game of Life's rules and any other overriding behavior each Cell with a specific CellType might have.
 * The Grid class impersonates the Model entity in the Model View Controller architectural pattern. 
 */
public class Grid {
	private Cell[][] cells;
	private HashMap<String, Cell> living;
	private GridRenderer gridRenderer;
	private int height;
	private int width;
	private int positionX;
	private int positionY;

	public Grid(int height, int width) {
		positionX = 0;
		positionY = 0;
		cells = new Cell[height + 1][width + 1];
		for (int i = 0; i <= height; i++) {
			for (int j = 0; j <= width; j++) {
				cells[i][j] = new Cell(i, j, CellType.NORMAL);
			}
		}
		living = new HashMap<>();
		this.height = height;
		this.width = width;
		this.gridRenderer = new ClassicGridRenderer();
	}

	public Grid(int height, int width, int positionX, int positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
		cells = new Cell[height + 1][width + 1];
		for (int i = 0; i <= height; i++) {
			for (int j = 0; j <= width; j++) {
				cells[i][j] = new Cell(i, j, CellType.NORMAL);
			}
		}
		living = new HashMap<>();
		this.height = height;
		this.width = width;
		this.gridRenderer = new ClassicGridRenderer();
	}

	public void setRenderer(GridRenderer gridRenderer) {
		this.gridRenderer = gridRenderer;
	}

	public void setPosition(int x, int y) {
		positionX = x;
		positionY = y;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void render(ShapeRenderer renderer) {
		renderer.begin(ShapeRenderer.ShapeType.Line);
		renderer.setColor(Color.WHITE);
		renderer.rect(positionX, positionY, height+1, width+1);
		renderer.end();
		gridRenderer.render(renderer, living, positionX, positionY);

	}

	public void updateGrid() {
		short[][] corrupted = new short[height + 1][width + 1];
		for (int i = 0; i <= height; i++) {
			for (int j = 0; j <= width; j++) {
				corrupted[i][j] = 0;
			}
		}
		
		living.forEach((key, value) -> {
			if (value.getType() == CellType.CORRUPTOR) {
				int x = value.getx();
				int y = value.gety();
				for (int i = x - 1; i <= x + 1; i++) {
					for (int j = y - 1; j <= y + 1; j++) {
						if (i >= 0 && i <= height && j >= 0 && j <= width) {
							corrupted[i][j] = 1;
						}
					}
				}
			}
		});
		
		short[][] up = new short[height + 1][width + 1];
		short[][] upLeft = new short[height + 1][width + 1];
		short[][] upRight = new short[height + 1][width + 1];
		short[][] down = new short[height + 1][width + 1];
		short[][] downLeft = new short[height + 1][width + 1];
		short[][] downRight = new short[height + 1][width + 1];
		for (int j = 0; j <= width; j++) {
			up[height][j] = 0;
			upLeft[height][j] = 0;
			upRight[height][j] = 0;
			down[0][j] = 0;
			downLeft[0][j] = 0;
			downRight[0][j] = 0;
		}

		short[][] left = new short[height + 1][width + 1];
		short[][] right = new short[height + 1][width + 1];
		for (int i = 0; i <= height; i++) {
			left[i][width] = 0;
			upLeft[i][width] = 0;
			downLeft[i][width] = 0;
			right[i][0] = 0;
			upRight[i][0] = 0;
			downRight[i][0] = 0;
		}

		// up shift
		for (int i = 0; i < height; i++) {
			for (int j = 0; j <= width; j++) {
				up[i][j] = (short) (cells[i + 1][j].isAlive() ? 1 : 0);
			}
		}

		// down shift
		for (int i = 1; i <= height; i++) {
			for (int j = 0; j <= width; j++) {
				down[i][j] = (short) (cells[i - 1][j].isAlive() ? 1 : 0);
			}
		}

		// left shift
		for (int i = 0; i <= height; i++) {
			for (int j = 0; j < width; j++) {
				left[i][j] = (short) (cells[i][j + 1].isAlive() ? 1 : 0);
			}
		}

		// right shift
		for (int i = 0; i <= height; i++) {
			for (int j = 1; j <= width; j++) {
				right[i][j] = (short) (cells[i][j - 1].isAlive() ? 1 : 0);
			}
		}

		// up left shift
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				upLeft[i][j] = (short) (cells[i + 1][j + 1].isAlive() ? 1 : 0);
			}
		}

		// up right shift
		for (int i = 0; i < height; i++) {
			for (int j = 1; j <= width; j++) {
				upRight[i][j] = (short) (cells[i + 1][j - 1].isAlive() ? 1 : 0);
			}
		}

		// down left shift
		for (int i = 1; i <= height; i++) {
			for (int j = 0; j < width; j++) {
				downLeft[i][j] = (short) (cells[i - 1][j + 1].isAlive() ? 1 : 0);
			}
		}

		// down right shift
		for (int i = 1; i <= height; i++) {
			for (int j = 1; j <= width; j++) {
				downRight[i][j] = (short) (cells[i - 1][j - 1].isAlive() ? 1 : 0);
			}
		}

		Neighbour[][] neighbours = new Neighbour[height + 1][width + 1];
		for (int i = 0; i <= height; i++) {
			for (int j = 0; j <= width; j++) {
				neighbours[i][j] = new Neighbour(((short) (up[i][j] + down[i][j] + left[i][j] + right[i][j]
						+ upLeft[i][j] + upRight[i][j] + downLeft[i][j] + downRight[i][j])), false);
				if (corrupted[i][j] >= 1) {
					neighbours[i][j].corrupt();
				}
			}
		}

		// checking Rule 1: Any live cell with fewer than two live neighbours dies, as
		// if by underpopulation.
		// Rule 2: Any live cell with two or three live neighbours lives on to the next
		// generation.
		// Rule 3: Any live cell with more than three live neighbours dies, as if by
		// overpopulation.
		// Rule 4: Any dead cell with exactly three live neighbours becomes a live cell,
		// as if by reproduction.

		for (int i = 0; i <= height; i++) {
			for (int j = 0; j <= width; j++) {
				if (cells[i][j].isAlive()) {
					if (neighbours[i][j].getNumber() < 2 || neighbours[i][j].getNumber() > 3) {
						if (!cells[i][j].getType().equals(CellType.IMMORTAL)) {
							giveDeath(i, j);
						}
					}
				} else {
					if (neighbours[i][j].getNumber() == 3) {
						if (neighbours[i][j].isCorruptible()) {
							giveLife(i, j, CellType.CORRUPTOR);
						} else {
							giveLife(i, j, CellType.NORMAL);
						}
					}
				}
			}
		}
	}

	public void giveLife(int i, int j, CellType type) {
		cells[i][j].live(type);
		living.put(i + "_" + j, cells[i][j]);
	}

	public void giveDeath(int i, int j) {
		cells[i][j].die();
		living.remove(i + "_" + j);
	}

	public void clear() {
		for (int i = 0; i <= height; i++) {
			for (int j = 0; j <= width; j++) {
				giveDeath(i, j);
			}
		}
	}

	public void lifeTouch(Brush brush, int x, int y) {
		int brushSize = brush.getSize();
		CellType brushType = brush.getType();
		for (int i = -brushSize; i <= brushSize; i++) {
			for (int j = -brushSize; j <= brushSize; j++) {
				if (x + i >= 0 && y + j >= 0 && x + i <= height && y + j <= width) {
					giveLife(x + i, y + j, brushType);
				}
			}
		}
	}

	public void deathTouch(Brush brush, int x, int y) {
		int brushSize = brush.getSize();
		for (int i = -brushSize; i <= brushSize; i++) {
			for (int j = -brushSize; j <= brushSize; j++) {
				if (x + i >= 0 && y + j >= 0 && x + i <= height && y + j <= width) {
					giveDeath(x + i, y + j);
				}
			}
		}
	}

	public void backgroundRandomizer() {
		for (int i = 0; i <= height; i++) {
			for (int j = 0; j <= width; j++) {
				if (Math.random() <= 0.09) {
					if (Math.random() < 0.90) {
						giveLife(i, j, CellType.NORMAL);
					} else {
						if (Math.random() < 0.70) {
							giveLife(i, j, CellType.IMMORTAL);
						} else {
							giveLife(i, j, CellType.CORRUPTOR);
						}
					}
				}
			}
		}
	}

	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public static Grid fromFile(FileHandle gridFile) throws IOException {
		InputStream gridStream = gridFile.read();
		String gridString = new String(gridStream.readAllBytes(), StandardCharsets.UTF_8);
		String[] lines = gridString.split("\\r?\\n");
		int height = lines.length;
		int width = lines[0].split(";").length;
		Grid grid = new Grid(height, width, 10, 10);
		for (int i = 0; i < height; i++) {
			String[] cellRow = lines[i].split(";");
			for (int j = 0; j < cellRow.length; j++) {
				Cell cell = Cell.fromString(cellRow[j]);
				if (cell.isAlive()) {
					grid.giveLife(cell.getx(), cell.gety(), cell.getType());
				}
			}
		}
		return grid;
	}
	
	public FileHandle saveToFile(FileHandle saveFile) {
		String saveLine;
		for (int i = 0; i <= height; i++) {
			saveLine = cells[i][0].toString();
			for (int j = 1; j <= width; j++) {
				saveLine = saveLine + ";" + cells[i][j].toString();
			}
			saveLine = saveLine + "\n";
			saveFile.writeString(saveLine, true);
		}
		return saveFile;
	}
}
