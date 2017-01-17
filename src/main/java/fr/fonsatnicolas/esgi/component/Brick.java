package fr.fonsatnicolas.esgi.component;

public class Brick {
	private int color;
	
	private int width;
	
	private int height;
	
	public Brick(int w, int h,  int c) {
		this.width  = w;
		this.height = h;
		this.color  = c;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getColor() {
		return this.color;
	}
}
