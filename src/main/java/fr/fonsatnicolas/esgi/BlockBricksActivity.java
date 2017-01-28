package fr.fonsatnicolas.esgi;

import ej.microui.MicroUI;
import ej.wadapps.app.Activity;
import fr.fonsatnicolas.esgi.canvas.GameContent;

public class BlockBricksActivity implements Activity {
	
	private GameContent gameContent; 

	@Override
	public String getID() {
		return "BlockBricksActivity";
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRestart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart() {
		MicroUI.start();
		
		this.gameContent = new GameContent();
		this.gameContent.show();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStop() {
		this.gameContent.hide();
	}

	@Override
	public void onDestroy() {
		this.gameContent = null;
	}

}
