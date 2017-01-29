package fr.fonsatnicolas.esgi.page;

import ej.widget.basic.Label;
import ej.widget.composed.Button;
import ej.widget.container.List;
import ej.widget.container.SimpleDock;
import ej.widget.navigation.page.Page;

public class Home extends Page {
	
	private SimpleDock container;
	
	public Home() {
		this.container = new SimpleDock(false);
		
		Label title = new Label("BlockBricks");
		title.addClassSelector("home-title-label");
		this.container.setFirst(title);
		
		/*Button playBtn = new Button("JOUER");
		playBtn.addClassSelector("home-btn");
		
		Button statsBtn = new Button("CLASSEMENT");
		statsBtn.addClassSelector("home-btn");
		
		Button exitBtn = new Button("QUITTER");
		exitBtn.addClassSelector("home-btn");
		
		List list = new List();
		list.add(playBtn);
		list.add(statsBtn);
		list.add(exitBtn);
		this.container.setCenter(list);*/

		this.setWidget(this.container);
	}

}
