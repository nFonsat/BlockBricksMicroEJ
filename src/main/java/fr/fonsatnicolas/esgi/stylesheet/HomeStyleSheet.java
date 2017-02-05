package fr.fonsatnicolas.esgi.stylesheet;

import java.io.IOException;

import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.style.State;
import ej.style.Stylesheet;
import ej.style.background.SimpleImageBackground;
import ej.style.font.FontProfile;
import ej.style.font.FontProfile.FontSize;
import ej.style.outline.SimpleOutline;
import ej.style.selector.ClassSelector;
import ej.style.selector.StateSelector;
import ej.style.selector.TypeSelector;
import ej.style.selector.combinator.AndCombinator;
import ej.style.util.EditableStyle;
import ej.style.util.StyleHelper;
import ej.widget.composed.Button;
import fr.fonsatnicolas.esgi.common.BBColors;

public class HomeStyleSheet {

	public static void Initialize() {
		GlobalStyleSheet.Initialize();
		InitializeStyleTitle();
		InitializeStyleButton();
		InitializeStyleButtonClass();
	}
	
	private static void InitializeStyleTitle() {
		Stylesheet css = StyleHelper.getStylesheet();
		
		EditableStyle style =  new EditableStyle();
		
		FontProfile font = new FontProfile();
		font.setSize(FontSize.LARGE);
		style.setFontProfile(font);
		
		SimpleOutline spacing = new SimpleOutline(10);
		style.setPadding(spacing);

		style.setForegroundColor(Colors.NAVY);
		
		style.setAlignment(GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		
		css.addRule(new ClassSelector("home-title-label"), style);
	}
	
	private static void InitializeStyleButton() {
		Stylesheet css = StyleHelper.getStylesheet();
		
		EditableStyle styleButton =  new EditableStyle();
		
		SimpleOutline spacing = new SimpleOutline(5);
		styleButton.setPadding(spacing);
		
		FontProfile font = new FontProfile();
		font.setSize(FontSize.SMALL);
		styleButton.setFontProfile(font);
		
		styleButton.setForegroundColor(BBColors.BLACK);
		styleButton.setAlignment(GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		
		AndCombinator selector = new AndCombinator(new TypeSelector(Button.class), new ClassSelector("home-btn"));
		css.addRule(selector, styleButton);
	}
	
	private static void InitializeStyleButtonClass() {
		
		InitializeStylePlayButtonClassBase("green");
		InitializeStyleButtonClassActive("green");
		InitializeStylePlayButtonClassBase("yellow");
		InitializeStyleButtonClassActive("yellow");
		InitializeStylePlayButtonClassBase("red");
		InitializeStyleButtonClassActive("red");
	}
	
	private static void InitializeStylePlayButtonClassBase(String color) {
		Stylesheet css = StyleHelper.getStylesheet();
		
		EditableStyle styleBase =  new EditableStyle();
		
		try {
			SimpleImageBackground images = new SimpleImageBackground(Image.createImage("/images/btn-"+color+".png"), GraphicsContext.HCENTER | GraphicsContext.VCENTER, true);
			styleBase.setBackground(images);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		css.addRule(new ClassSelector("home-btn-"+color), styleBase);
	}
	
	private static void InitializeStyleButtonClassActive(String color) {
		Stylesheet css = StyleHelper.getStylesheet();
		
		EditableStyle styleActive =  new EditableStyle();
		
		try {
			SimpleImageBackground images = new SimpleImageBackground(Image.createImage("/images/btn-"+color+"-pressed.png"),
					GraphicsContext.HCENTER | GraphicsContext.VCENTER, true);
			styleActive.setBackground(images);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		AndCombinator selectorActive = new AndCombinator(new ClassSelector("home-btn-"+color), new StateSelector(State.Active));
		css.addRule(selectorActive, styleActive);
	}
}
