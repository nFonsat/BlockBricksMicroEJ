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
		this.animateBrick(new Brick(this.getLastWidth(), HEIGHT_PLATE, BBColors.YELLOW));
	}
	
	/************************ Displayable ************************/
	@Override
	public void paint(GraphicsContext g) {
		// Set green background
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
	
	private int getSizeBricks() {
		return ((this.bricks.size() + 1) * HEIGHT_PLATE);
	}
	
	private int getLastX() {
		return this.positionX;
	}
	
	private int getLastWidth() {
		if (this.bricks.size() == 0) {
			return WIDTH_PLATE;
		}
		
		Brick brick = this.bricks.get(this.bricks.size()-1);
		if (brick == null) {
			return WIDTH_PLATE;
		}
		
		return brick.getWidth();
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
				//System.out.println("Current position = " + position);
				//System.out.println("POINTER = " + ptr.getX());
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
		private int interval;
		
		public AnimationBrick(GameContent display, int interval) {
			this.display = display;
			this.interval = interval;
			display.brick.setX(display.dimV/2);
			display.brick.setY(0);
		}

		@Override
		public void run() {
			int y = display.brick.getY() + this.interval;
			if (y > getBottomLimit() - getSizeBricks()) {
				int colision = (getBottomLimit() - getSizeBricks()) - y;
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
					int width = endBrickX - startBlockX;
					this.display.brick.setWidth(width);
					this.display.brick.setX(this.display.positionX);
					hasChanged = true;
				}
				else if (startBrickX > startBlockX && startBrickX < endBlockX) {
					System.out.println("Brick Animation NESTED RIGHT");
					int width = endBrickX - endBlockX;
					this.display.brick.setWidth(width);
					hasChanged = true;
				}
				else if (startBrickX == startBlockX && endBrickX == endBlockX) {
					System.out.println("Brick Animation NESTED");
					hasChanged = true;
				}
				
				
				if (hasChanged) {
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
		
	}
	
	private void animateBrick(Brick brick) {
		this.brick = brick;
		AnimationBrick animator = new AnimationBrick(this, 2);
		Timer timer = new Timer();
		int frame = 1000 / 60;
		timer.schedule(animator, frame, frame);
	}
	
	private void addCurrentBrick() {
		if (this.brick != null) {
			this.bricks.add(this.brick);
			this.brick = null;
			this.repaint();
		}
	}
}
