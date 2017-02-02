package fr.fonsatnicolas.esgi.page;

import java.io.IOException;

import ej.container.Dock;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.navigation.page.Page;
import ej.style.State;
import ej.style.Stylesheet;
import ej.style.background.NoBackground;
import ej.style.background.SimpleImageBackground;
import ej.style.outline.SimpleOutline;
import ej.style.selector.ClassSelector;
import ej.style.selector.StateSelector;
import ej.style.selector.TypeSelector;
import ej.style.selector.combinator.AndCombinator;
import ej.style.selector.combinator.ChildCombinator;
import ej.style.util.EditableStyle;
import ej.style.util.StyleHelper;
import ej.widget.basic.Label;
import ej.widget.composed.Button;
import ej.widget.listener.OnClickListener;
import fr.fonsatnicolas.esgi.BlockBricksActivity;
import fr.fonsatnicolas.esgi.canvas.GameContentWidget;

public class Game extends Page {
	
	private Dock container;
	
	private GameContentWidget widget;
	
	public Game() {
		InitializeStyleButton();
		InitializeStyleTransp();
		InitializeStyleButtonClassBase();
		InitializeStyleButtonClassActive();
		
		this.container = new Dock(false);
		this.setWidget(this.container);
		
		Button cancel = new Button("");
		cancel.addClassSelector("btn-cancel");
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
	
	private static void InitializeStyleButton() {
		Stylesheet css = StyleHelper.getStylesheet();
		
		EditableStyle styleButton =  new EditableStyle();
		
		SimpleOutline spacing = new SimpleOutline(5);
		styleButton.setPadding(spacing);
		
		AndCombinator selector = new AndCombinator(new TypeSelector(Button.class), new ClassSelector("btn-cancel"));
		css.addRule(selector, styleButton);
	}
	
	private static void InitializeStyleTransp() {
		Stylesheet css = StyleHelper.getStylesheet();
		
		EditableStyle transpStyle =  new EditableStyle();
		transpStyle.setBackground(NoBackground.NO_BACKGROUND);
		
		TypeSelector labelSel = new TypeSelector(Label.class);
		TypeSelector buttonSel = new TypeSelector(Button.class);
		
		css.addRule(new ChildCombinator(buttonSel, labelSel), transpStyle);
	}
	
	private static void InitializeStyleButtonClassBase() {
		Stylesheet css = StyleHelper.getStylesheet();
		
		EditableStyle styleBase =  new EditableStyle();
		
		try {
			SimpleImageBackground images = new SimpleImageBackground(Image.createImage("/images/btn-cancel.png"),
					GraphicsContext.LEFT | GraphicsContext.VCENTER, true);
			styleBase.setBackground(images);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		css.addRule(new ClassSelector("btn-cancel"), styleBase);
	}
	
	private static void InitializeStyleButtonClassActive() {
		Stylesheet css = StyleHelper.getStylesheet();
		
		EditableStyle styleActive =  new EditableStyle();
		
		try {
			SimpleImageBackground images = new SimpleImageBackground(Image.createImage("/images/btn-cancel-pressed.png"),
					GraphicsContext.LEFT | GraphicsContext.VCENTER, true);
			styleActive.setBackground(images);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		AndCombinator selectorActive = new AndCombinator(new ClassSelector("btn-cancel"), new StateSelector(State.Active));
		css.addRule(selectorActive, styleActive);
	}
}
