package fr.fonsatnicolas.esgi;

import ej.microui.MicroUI;
import ej.navigation.desktop.NavigationDesktop;
import ej.navigation.page.Page;
import ej.wadapps.app.Activity;

import fr.fonsatnicolas.esgi.page.Home;
import fr.fonsatnicolas.esgi.component.Score;

import java.util.ArrayList;
import java.util.List;

public class BlockBricksActivity implements Activity {
	
	private static List<Score> scores;
	
	private static NavigationDesktop navigator;

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
		scores = new ArrayList<>();
		navigator = new NavigationDesktop();
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
	
	public static boolean addScore(Score score) {
		return scores.add(score);
	}
	
	public static void clearScore() {
		scores.clear();
	}
	
	public static List<Score> scores() {
		return scores;
	}

}
