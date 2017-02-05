package fr.fonsatnicolas.esgi.page;

import ej.container.Dock;
import ej.navigation.page.Page;
import ej.widget.composed.Button;
import ej.widget.listener.OnClickListener;
import fr.fonsatnicolas.esgi.BlockBricksActivity;
import fr.fonsatnicolas.esgi.canvas.GameContentWidget;
import fr.fonsatnicolas.esgi.stylesheet.GlobalStyleSheet;

public class Game extends Page {
	
	private Dock container;
	
	private GameContentWidget widget;
	
	public Game() {
		GlobalStyleSheet.Initialize();
		this.container = new Dock(false);
		this.setWidget(this.container);
		
		Button cancel = new Button("");
		cancel.addClassSelector("back-button");
		this.container.setFirst(cancel);
		cancel.addOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				if (widget != null && widget.isPlaying()) {
					widget.gameOver();
				}
				else {
					BlockBricksActivity.back();
				}
			}
		});
		
		widget =  new GameContentWidget();
		this.container.setCenter(widget);
	}
}
