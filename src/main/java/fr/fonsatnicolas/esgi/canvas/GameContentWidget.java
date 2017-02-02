package fr.fonsatnicolas.esgi.canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
import ej.style.Style;
import ej.style.container.Rectangle;
import ej.widget.StyledWidget;
import fr.fonsatnicolas.esgi.common.BBColors;
import fr.fonsatnicolas.esgi.common.BBConstants;
import fr.fonsatnicolas.esgi.common.BBFonts;
import fr.fonsatnicolas.esgi.component.Brick;

public class GameContentWidget extends StyledWidget {
	
	private static int[] sColors = {
			BBColors.CYAN,
			BBColors.RED,
			BBColors.YELLOW,
			BBColors.GREEN,
			BBColors.WHITE
	};
	
	private List<Brick> bricks = new ArrayList<>();
	
	private Brick currentBrick;
	
	private int positionX;
	
	private int globalWidth;
	
	private int globalHeight;
	
	private boolean isPlaying = true;
	
	private int counter = 1;
	
	private int score = 0;
	
	private int speed = 1;
	
	public GameContentWidget() {
		this.animateBrick(sColors[0], this.counter);
	}

	/************************ StyledWidget ************************/
	@Override
	public void renderContent(GraphicsContext g, Style style, Rectangle bounds) {
		g.setColor(BBColors.DARK_GREY);
		g.fillRect(0,0,globalWidth,globalHeight);
		
		g.setColor(BBColors.LIGHT_BROWN);
		g.fillRect(this.positionX, this.getBottomLimit(), BBConstants.WIDTH_PLATE, BBConstants.HEIGHT_PLATE);
		
		for(Brick item : this.bricks) {
			g.setColor(item.getColor());
			g.fillRect(item.getX(), item.getY(), item.getWidth(), item.getHeight());
		}
		
		if (this.currentBrick != null) {
			g.setColor(this.currentBrick.getColor());
			g.fillRect(this.currentBrick.getX(), this.currentBrick.getY(), this.currentBrick.getWidth(), this.currentBrick.getHeight());
		}
				
		final Font font = Font.getFont(BBFonts.FONT_04B_30, 0, Font.STYLE_PLAIN);
		g.setColor(BBColors.WHITE);
		g.setFont(font);
		
		if (font == Font.getDefaultFont()) {
			System.out.println("Unable to find custom font! Using default font instead");
		}

		g.drawString(String.valueOf(this.score), this.globalWidth-30, 30,
				GraphicsContext.HCENTER | GraphicsContext.VCENTER);
	}

	@Override
	public Rectangle validateContent(Style style, Rectangle bounds) {
		this.globalHeight = bounds.getHeight();
		this.globalWidth  = bounds.getWidth();
		this.positionX = (bounds.getWidth()/2) - (BBConstants.WIDTH_PLATE/2);
		return bounds;
	}
	
	/************************ GameContent Helper ************************/
	private int getBottomLimit() {
		return this.globalHeight - BBConstants.HEIGHT_PLATE - 10;
	}
	
	private int getHeightBricks() {
		return ((this.bricks.size() + 1) * BBConstants.HEIGHT_PLATE);
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
			return BBConstants.WIDTH_PLATE;
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
	
	private void animateBrick(int color, int speed) {
		this.currentBrick = new Brick(this.getLastWidth(), BBConstants.HEIGHT_PLATE, color);
		AnimationBrick animator = new AnimationBrick(this, speed);
		Timer timer = new Timer();
		int frame = 1000 / 60;
		timer.schedule(animator, frame, frame);
	}
	
	private void addCurrentBrick() {
		if (this.currentBrick != null) {
			if (this.currentBrick.getWidth() < BBConstants.MIN_WIDTH_PLATE) {
				this.gameOver();
				return;
			}
			
			if (this.getHeightBricks() > (3*(this.globalHeight/5))) {
				this.bricks.clear();
				int height = this.getBottomLimit() - this.getHeightBricks();
				this.currentBrick.setY(height);
			}
			
			this.bricks.add(this.currentBrick);
			this.score++;
			this.currentBrick = null;
			
			this.counter++;
			int index = this.counter%sColors.length;
			
			if (this.counter%5 == 0) {
				this.speed++;
			}
			
			this.animateBrick(sColors[index], this.speed);
		}
	}
	
	private void gameOver() {
		System.out.println("You lose ! Your score is " + this.score);
		this.isPlaying = false;
		this.repaint();
	}
	
	/************************ EventHandler ************************/
	@Override
	public boolean handleEvent(int event) {
		if(Event.getType(event) == Event.POINTER) {
			if ((Pointer.isPressed(event) || Pointer.isDragged(event)) & this.isPlaying) {
				Pointer ptr = (Pointer)Event.getGenerator(event);
				
				int position = ptr.getX() - (BBConstants.WIDTH_PLATE/2);
				if (position < BBConstants.LIMIT_BLOCK) {
					position = BBConstants.LIMIT_BLOCK;
				} else if (position > (this.globalWidth - BBConstants.LIMIT_BLOCK - BBConstants.WIDTH_PLATE)) {
					position = this.globalWidth - BBConstants.LIMIT_BLOCK - BBConstants.WIDTH_PLATE;
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
		private GameContentWidget widget;
		private int speed;
		
		public AnimationBrick(GameContentWidget w, int s) {
			this.widget = w;
			this.speed = s;
			widget.currentBrick.setX(this.randomX());
			widget.currentBrick.setY(0);
		}

		@Override
		public void run() {
			int y = widget.currentBrick.getY() + this.speed;
			int height = widget.getBottomLimit() - widget.getHeightBricks();
			if (y > height) {
				float colision = height - y;
				if (colision > 0) {
					System.out.println("Brick Animation DETECTION: " + colision);
					this.cancel();
					return;
				}

				int startBrickX = widget.currentBrick.getX();
				int endBrickX = startBrickX + widget.currentBrick.getWidth();

				int startBlockX = widget.getLastX();
				int endBlockX = startBlockX + widget.getLastWidth();
				
				boolean hasChanged = false;
				if (endBrickX > startBlockX && endBrickX < endBlockX) {
					System.out.println("Brick Animation NESTED LEFT");
					int width = endBrickX - startBlockX ;
					widget.currentBrick.setWidth(width);
					widget.currentBrick.setX(startBlockX);
					hasChanged = true;
				}
				else if (startBrickX > startBlockX && startBrickX < endBlockX) {
					System.out.println("Brick Animation NESTED RIGHT");
					int width = endBlockX - startBrickX;
					widget.currentBrick.setWidth(width);
					widget.currentBrick.setX(endBlockX - widget.currentBrick.getWidth());
					hasChanged = true;
				}
				else if (startBrickX == startBlockX && endBrickX == endBlockX) {
					System.out.println("Brick Animation NESTED");
					hasChanged = true;
				}
				
				
				if (hasChanged) {
					widget.currentBrick.setY(height);
					widget.addCurrentBrick();
					this.cancel();
					return;
				}
			}

			if (y > getBottomLimit()) {
				this.cancel();
				widget.gameOver();
				return;
			}
			
			widget.currentBrick.setY(y);
			widget.repaint();
		}
		
		private int randomX() {
			int min = BBConstants.LIMIT_BLOCK + BBConstants.WIDTH_PLATE;
			int max = (int) (widget.globalWidth - BBConstants.LIMIT_BLOCK - (BBConstants.WIDTH_PLATE*1.5));
			return min + (int)(Math.random() * ((max - min) + 1));
		}
		
	}
}
