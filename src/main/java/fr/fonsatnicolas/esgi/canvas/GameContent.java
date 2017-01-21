package fr.fonsatnicolas.esgi.canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
import ej.microui.util.EventHandler;
import fr.fonsatnicolas.esgi.common.BBColors;
import fr.fonsatnicolas.esgi.component.Brick;

public class GameContent extends Displayable implements EventHandler {
	
	public static int[] sColors = {
			BBColors.CYAN,
			BBColors.RED,
			BBColors.YELLOW,
			BBColors.GREEN,
			BBColors.WHITE
	};
	
	private int score = 0;
	
	private int speed = 1;
	
	private int counter = 1;
	
	public static final int MIN_WIDTH_PLATE = 5;
	
	public static final int WIDTH_PLATE = 100;
	public static final int HEIGHT_PLATE = 15;
	
	public static final int LIMIT_BLOCK = 20;
	
	private List<Brick> bricks;
	
	private int dimH;
	private int dimV;
	
	private int positionX;
	
	private Brick brick;
	
	public GameContent() {
		super(Display.getDefaultDisplay());
		Display disp = Display.getDefaultDisplay();
		this.dimH = disp.getHeight();
		this.dimV = disp.getWidth();
		this.positionX = (this.dimV/2) - (WIDTH_PLATE/2);
		this.bricks = new ArrayList<>();
		this.animateBrick(sColors[0], this.counter);
	}
	
	/************************ Displayable ************************/
	@Override
	public void paint(GraphicsContext g) {
		g.setColor(BBColors.DARK_GREY);
		g.fillRect(0,0,dimV,dimH);
		
		g.setColor(BBColors.LIGHT_BROWN);
		g.fillRect(this.positionX, this.getBottomLimit(), WIDTH_PLATE, HEIGHT_PLATE);
		
		for(Brick item : this.bricks) {
			g.setColor(item.getColor());
			g.fillRect(item.getX(), item.getY(), item.getWidth(), item.getHeight());
		}
		
		if (this.brick != null) {
			g.setColor(this.brick.getColor());
			g.fillRect(this.brick.getX(), this.brick.getY(), this.brick.getWidth(), this.brick.getHeight());
		}
	}
	
	/************************ GameContent Helper ************************/
	private int getBottomLimit() {
		return dimH - HEIGHT_PLATE - 10;
	}
	
	private int getHeightBricks() {
		return ((this.bricks.size() + 1) * HEIGHT_PLATE);
	}
	
	private int getLastX() {
		if (this.bricks.size() == 0) {
			return this.positionX;
		}
		
		int last = this.bricks.size()-1;
		return this.bricks.get(last).getX();
	}
	
	private int getLastWidth() {
		if (this.bricks.size() == 0) {
			return WIDTH_PLATE;
		}
		
		int last = this.bricks.size()-1;
		return this.bricks.get(last).getWidth();
	}
	
	private void updateBricks(int distance) {
		for(Brick item : this.bricks) {
			int x = item.getX();
			item.setX(x+distance);
		}
	}

	/************************ EventHandler ************************/
	@Override
	public EventHandler getController() {
		return this;
	}
	
	@Override
	public boolean handleEvent(int event) {
		if(Event.getType(event) == Event.POINTER) {
			if (Pointer.isPressed(event) || Pointer.isDragged(event)) {
				Pointer ptr = (Pointer)Event.getGenerator(event);
				
				int position = ptr.getX() - (WIDTH_PLATE/2);
				if (position < LIMIT_BLOCK) {
					position = LIMIT_BLOCK;
				} else if (position > (this.dimV - LIMIT_BLOCK - WIDTH_PLATE)) {
					position = this.dimV - LIMIT_BLOCK - WIDTH_PLATE;
				}
				
				if (position != this.positionX) {
					int distance = position - this.positionX;
					this.positionX = position;
					this.updateBricks(distance);
					this.repaint();
				}
				
				return true;
			}
		}

		return false;
	}
	
	/************************ AnimationBrick ************************/
	private class AnimationBrick extends TimerTask {
		private GameContent display;
		private int speed;
		
		public AnimationBrick(GameContent display, int speed) {
			this.display = display;
			this.speed = speed;
			display.brick.setX(this.randomX());
			display.brick.setY(0);
		}

		@Override
		public void run() {
			int y = display.brick.getY() + this.speed;
			int height = display.getBottomLimit() - display.getHeightBricks();
			if (y > height) {
				float colision = height - y;
				if (colision > 0) {
					System.out.println("Brick Animation DETECTION: " + colision);
					this.cancel();
					return;
				}

				int startBrickX = this.display.brick.getX();
				int endBrickX = startBrickX + this.display.brick.getWidth();

				int startBlockX = this.display.getLastX();
				int endBlockX = startBlockX + this.display.getLastWidth();
				
				boolean hasChanged = false;
				if (endBrickX > startBlockX && endBrickX < endBlockX) {
					System.out.println("Brick Animation NESTED LEFT");
					int width = endBrickX - startBlockX ;
					this.display.brick.setWidth(width);
					this.display.brick.setX(startBlockX);
					hasChanged = true;
				}
				else if (startBrickX > startBlockX && startBrickX < endBlockX) {
					System.out.println("Brick Animation NESTED RIGHT");
					int width = endBlockX - startBrickX;
					this.display.brick.setWidth(width);
					this.display.brick.setX(endBlockX - this.display.brick.getWidth());
					hasChanged = true;
				}
				else if (startBrickX == startBlockX && endBrickX == endBlockX) {
					System.out.println("Brick Animation NESTED");
					hasChanged = true;
				}
				
				
				if (hasChanged) {
					this.display.brick.setY(height);
					this.display.addCurrentBrick();
					this.cancel();
					return;
				}
			}

			if (y > getBottomLimit()) {
				this.cancel();
				System.out.println("Brick Animation END");
				return;
			}
			
			display.brick.setY(y);
			this.display.repaint();
		}
		
		private int randomX() {
			int min = LIMIT_BLOCK + WIDTH_PLATE;
			int max = (int) (this.display.dimV - LIMIT_BLOCK - (WIDTH_PLATE*1.5));
			return min + (int)(Math.random() * ((max - min) + 1));
		}
		
	}
	
	private void animateBrick(int color, int speed) {
		this.brick = new Brick(this.getLastWidth(), HEIGHT_PLATE, color);
		AnimationBrick animator = new AnimationBrick(this, speed);
		Timer timer = new Timer();
		int frame = 1000 / 60;
		timer.schedule(animator, frame, frame);
	}
	
	private void addCurrentBrick() {
		if (this.brick != null) {
			
			if (this.brick.getWidth() < MIN_WIDTH_PLATE) {
				System.out.println("You lose ! Your score is " + this.score);
				this.repaint();
				return;
			}
			
			if (this.getHeightBricks() > (3*(this.dimH/5))) {
				this.bricks.clear();
				int height = this.getBottomLimit() - this.getHeightBricks();
				this.brick.setY(height);
			}
			
			this.bricks.add(this.brick);
			this.score++;
			this.brick = null;
			
			this.counter++;
			int index = this.counter%sColors.length;
			
			if (this.counter%5 == 0) {
				this.speed++;
			}
			
			this.animateBrick(sColors[index], this.speed);
		}
	}
}
