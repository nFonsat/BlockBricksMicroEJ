package fr.fonsatnicolas.esgi.page;

import java.util.Calendar;
import ej.container.Dock;
import ej.container.List;
import ej.container.Scroll;
import ej.mwt.Widget;
import ej.navigation.page.Page;
import ej.widget.basic.Label;
import ej.widget.composed.Button;
import ej.widget.listener.OnClickListener;
import fr.fonsatnicolas.esgi.BlockBricksActivity;
import fr.fonsatnicolas.esgi.component.Score;
import fr.fonsatnicolas.esgi.stylesheet.StatsStyleSheet;

public class Stats extends Page {
	
	private Dock container;
	
	
	public Stats() {
		StatsStyleSheet.Initialize();
		
		this.container = new Dock(false);
		this.setWidget(this.container);
		this.container.setFirst(this.initTopBar());
		
		Scroll body = new Scroll(false, true);
		body.setWidget(this.initListScore());
		this.container.setCenter(body);
	}
	
	private Widget initTopBar() {
		Dock topBar = new Dock();
		
		Button back = new Button("");
		back.addClassSelector("back-button");
		back.addOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				BlockBricksActivity.back();
			}
		});
		topBar.setFirst(back);
		
		Label title = new Label("SCORES");
		title.addClassSelector("stats-title-label");
		topBar.setCenter(title);
		
		return topBar;
	}
	
	private List initListScore() {
		List content = new List(false);
		
		
		for (Score score : BlockBricksActivity.scores()) {
			content.add(this.addScoreItem(score));
		}
		
		return content;
	}
	
	private Dock addScoreItem(Score score) {
		Dock dock = new Dock(true);
		
		String dateString = this.formatDateTime(score.getStart());
		Label dateLabel = new Label(dateString);
		dateLabel.addClassSelector("score-item");
		dateLabel.addClassSelector("score-item-date");
		dock.setFirst(dateLabel);
		
		Label scoreLabel = new Label(String.valueOf(score.getValue()));
		scoreLabel.addClassSelector("score-item");
		scoreLabel.addClassSelector("score-item-value");
		dock.setCenter(scoreLabel);
		
		String time = this.formatTime(score.time());
		Label timeLabel = new Label(time);
		timeLabel.addClassSelector("score-item");
		timeLabel.addClassSelector("score-item-time");
		dock.setLast(timeLabel);
		
		return dock;
	}
	
	private String formatTime(long time) {
		StringBuilder builder = new StringBuilder();
		
		int second = (int)time/1000;
		
		builder.append(second).append("s");
		return builder.toString();
	}
	
	private String formatDateTime(Calendar date) {
		StringBuilder builder = new StringBuilder();
		
		int day = date.get(Calendar.DAY_OF_MONTH);
		int month = date.get(Calendar.MONTH) + 1;
		int year = date.get(Calendar.YEAR);
		
		int hours = date.get(Calendar.HOUR_OF_DAY);
		int minutes = date.get(Calendar.MINUTE);
		
		builder.append(day < 10 ? '0'+String.valueOf(day) : day).append('/')
			.append(month < 10 ? '0'+String.valueOf(month) : month).append('/')
			.append(year).append("  ")
			.append(hours < 10 ? '0'+String.valueOf(hours) : hours).append(':')
			.append(minutes < 10 ? '0'+String.valueOf(minutes) : minutes)
			;
		
		return builder.toString();
	}
}
