package fr.fonsatnicolas.esgi.page;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.container.Dock;
import ej.container.List;
import ej.exit.ExitHandler;
import ej.navigation.page.Page;
import ej.widget.basic.Label;
import ej.widget.composed.Button;
import ej.widget.listener.OnClickListener;
import fr.fonsatnicolas.esgi.BlockBricksActivity;
import fr.fonsatnicolas.esgi.stylesheet.HomeStyleSheet;

public class Home extends Page {
	
	private Dock container;
	
	public Home() {
		HomeStyleSheet.Initialize();
		
		this.container = new Dock(false);
		this.setWidget(this.container);
		
		this.addTitle("BlockBricks");
		this.loadMenu();
	}
	
	private Label addTitle(String name) {
		Label title = new Label(name);
		title.addClassSelector("home-title-label");
		this.container.setFirst(title);
		return title;
	}
	
	private void loadMenu() {
		Button playBtn = new Button("JOUER");
		playBtn.addClassSelector("home-btn");
		playBtn.addClassSelector("home-btn-green");
		playBtn.addOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				BlockBricksActivity.show(new Game());
			}
		});
		
		Button statsBtn = new Button("CLASSEMENT");
		statsBtn.addClassSelector("home-btn");
		statsBtn.addClassSelector("home-btn-yellow");
		statsBtn.addOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				BlockBricksActivity.show(new Stats());
			}
		});
		
		Button exitBtn = new Button("QUITTER");
		exitBtn.addClassSelector("home-btn");
		exitBtn.addClassSelector("home-btn-red");
		exitBtn.addOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				ExitHandler exithandler = ServiceLoaderFactory.getServiceLoader().getService(ExitHandler.class);
				if (exithandler !=  null) {
					exithandler.exit();
				}
			}
		});
		
		List list = new List(false);
		list.add(playBtn);
		list.add(statsBtn);
		list.add(exitBtn);
		this.container.setCenter(list);
	}
}
