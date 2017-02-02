package fr.fonsatnicolas.esgi.page;

import ej.container.Dock;
import ej.navigation.page.Page;
import fr.fonsatnicolas.esgi.canvas.GameContentWidget;

public class Game extends Page {
	
	private Dock container;
	
	public Game() {
		this.container = new Dock(false);
		this.setWidget(this.container);
		
		
		GameContentWidget widget =  new GameContentWidget();
		this.container.setCenter(widget);
	}
}
