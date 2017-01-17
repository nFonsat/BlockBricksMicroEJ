package fr.fonsatnicolas.esgi.canvas;

import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
import ej.microui.util.EventHandler;

public class GameContent extends Displayable implements EventHandler {
	public static final int LIGHT_BROWN = 0xc5967a;
	public static final int DARK_GREY = 0x5c5c5c;
	
	public static final int WIDTH_PLATE = 100;
	public static final int HEIGHT_PLATE = 15;
	
	public static final int LIMIT_BLOCK = 20;
	
	private int dimH;
	private int dimV;
	
	private int positionX;
	
	public GameContent() {
		super(Display.getDefaultDisplay());
		Display disp = Display.getDefaultDisplay();
		this.dimH = disp.getHeight();
		this.dimV = disp.getWidth();
		this.positionX = (this.dimV/2) - (WIDTH_PLATE/2);
	}
	
	/************************ Displayable ************************/
	@Override
	public void paint(GraphicsContext g) {
		// Set green background
		g.setColor(DARK_GREY);
		g.fillRect(0,0,dimV,dimH);
		
		g.setColor(LIGHT_BROWN);
		g.fillRect(this.positionX, (dimH - HEIGHT_PLATE - 10), WIDTH_PLATE, HEIGHT_PLATE);
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
				System.out.println("Current position = " + position);
				System.out.println("POINTER = " + ptr.getX());
				if (position < LIMIT_BLOCK) {
					System.out.println("DETECTION LEFT END");
					position = LIMIT_BLOCK;
				} else if (position > (this.dimV - LIMIT_BLOCK - WIDTH_PLATE)) {
					System.out.println("DETECTION RIGHT END");
					position = this.dimV - LIMIT_BLOCK - WIDTH_PLATE;
				}
				
				if (position != this.positionX) {
					this.positionX = position;
					this.repaint();
				}
				
				System.out.println("X POSTIION = " + this.positionX);
				return true;
			}
		}

		return false;
	}
}
