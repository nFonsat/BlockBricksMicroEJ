package fr.fonsatnicolas.esgi;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.style.Stylesheet;
import ej.style.outline.SimpleOutline;
import ej.style.selector.ClassSelector;
import ej.style.util.EditableStyle;
import ej.style.util.StyleHelper;
import ej.wadapps.app.Activity;
import ej.widget.navigation.navigator.HistorizedNavigator;
import ej.widget.navigation.page.Page;
import fr.fonsatnicolas.esgi.page.Home;

public class BlockBricksActivity implements Activity {
	
	private static HistorizedNavigator navigator;

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
		navigator = new HistorizedNavigator();
		show(new Home());
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
	}

	@Override
	public void onDestroy() {
	}
	
	public static void show(Page page) {
		System.out.println("Go to "+page.getCurrentURL());
		navigator.show(page);
	}
	
	public static void back() {
		System.out.println("Back");
		navigator.back();
	}
	
	public static void clearStack() {
		System.out.println("clearStack");
		navigator.clearHistory();
	}
	
	public static void reload() {
		System.out.println("reload");
		navigator.reload();
	}

}
