package fr.fonsatnicolas.esgi.stylesheet;

import java.io.IOException;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
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

public class GlobalStyleSheet {
	
	public static void Initialize() {
		InitializeStyleButtonTransp();

		InitializeStyleButtonBack();
		InitializeStyleButtonBackBase();
		InitializeStyleButtonBackActive();
	}
	
	private static void InitializeStyleButtonBack() {
		Stylesheet css = StyleHelper.getStylesheet();
		
		EditableStyle styleButton =  new EditableStyle();
		
		SimpleOutline spacing = new SimpleOutline(5);
		styleButton.setPadding(spacing);
		
		AndCombinator selector = new AndCombinator(new TypeSelector(Button.class), new ClassSelector("back-button"));
		css.addRule(selector, styleButton);
	}
	
	private static void InitializeStyleButtonTransp() {
		Stylesheet css = StyleHelper.getStylesheet();
		
		EditableStyle transpStyle =  new EditableStyle();
		transpStyle.setBackground(NoBackground.NO_BACKGROUND);
		
		TypeSelector labelSel = new TypeSelector(Label.class);
		TypeSelector buttonSel = new TypeSelector(Button.class);
		
		css.addRule(new ChildCombinator(buttonSel, labelSel), transpStyle);
	}
	
	private static void InitializeStyleButtonBackBase() {
		Stylesheet css = StyleHelper.getStylesheet();
		
		EditableStyle styleBase =  new EditableStyle();
		
		try {
			SimpleImageBackground images = new SimpleImageBackground(Image.createImage("/images/btn-cancel.png"),
					GraphicsContext.LEFT | GraphicsContext.VCENTER, true);
			styleBase.setBackground(images);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		css.addRule(new ClassSelector("back-button"), styleBase);
	}
	
	private static void InitializeStyleButtonBackActive() {
		Stylesheet css = StyleHelper.getStylesheet();
		
		EditableStyle styleActive =  new EditableStyle();
		
		try {
			SimpleImageBackground images = new SimpleImageBackground(Image.createImage("/images/btn-cancel-pressed.png"),
					GraphicsContext.LEFT | GraphicsContext.VCENTER, true);
			styleActive.setBackground(images);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		AndCombinator selectorActive = new AndCombinator(new ClassSelector("back-button"), new StateSelector(State.Active));
		css.addRule(selectorActive, styleActive);
	}
}
