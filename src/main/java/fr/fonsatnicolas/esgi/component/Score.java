package fr.fonsatnicolas.esgi.component;

import java.util.Calendar;
import java.util.Date;

public class Score {
	
	private Calendar startGame;
	
	private Calendar endGame;
	
	private int value;
	
	public Score() {
		this.startGame = Calendar.getInstance();
		this.value = 0;
	}
	
	public void end() {
		this.endGame = Calendar.getInstance();
	}
	
	public long time() {
		if (this.endGame == null) {
			Date current = new Date();
			return current.getTime() - this.startGame.getTime().getTime();
		}
		
		return this.endGame.getTime().getTime() - this.startGame.getTime().getTime() ;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void increment() {
		this.value++;
	}
	
	public Calendar getStart() {
		return this.startGame;
	}
	
}
