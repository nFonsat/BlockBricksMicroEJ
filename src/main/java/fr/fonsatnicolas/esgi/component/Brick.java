package fr.fonsatnicolas.esgi.component;

public class Brick {
	private int color;
	
	private int width;
	
	private int height;
	
	private int xPosition;
	
	private int yPosition;
	
	public Brick(int w, int h,  int c) {
		this.width  = w;
		this.height = h;
		this.color  = c;
		this.xPosition = 0;
		this.yPosition = 0;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public void setWidth(int w) {
		this.width = w;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getColor() {
		return this.color;
	}
	
	public int getX() {
		return this.xPosition;
	}
	
	public void setX(int position) {
		this.xPosition = position;
	}
	
	public int getY() {
		return this.yPosition;
	}
	
	public void setY(int position) {
		this.yPosition = position;
	}
}
