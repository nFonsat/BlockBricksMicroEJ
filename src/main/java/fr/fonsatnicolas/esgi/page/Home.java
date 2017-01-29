package fr.fonsatnicolas.esgi.page;

import ej.container.Dock;
import ej.container.List;
import ej.navigation.page.Page;
import ej.widget.basic.Label;
import ej.widget.composed.Button;

public class Home extends Page {
	
	private Dock container;
	
	public Home() {
		this.container = new Dock(false);
		
		Label title = new Label("BlockBricks");
		title.addClassSelector("home-title-label");
		this.container.setFirst(title);
		
		Button playBtn = new Button("JOUER");
		playBtn.addClassSelector("home-btn");
		
		Button statsBtn = new Button("CLASSEMENT");
		statsBtn.addClassSelector("home-btn");
		
		Button exitBtn = new Button("QUITTER");
		exitBtn.addClassSelector("home-btn");
		
		List list = new List(false);
		list.add(playBtn);
		list.add(statsBtn);
		list.add(exitBtn);
		this.container.setCenter(list);

		this.setWidget(this.container);
	}

}
