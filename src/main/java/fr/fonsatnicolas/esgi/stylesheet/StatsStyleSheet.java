package fr.fonsatnicolas.esgi.stylesheet;

import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.style.Stylesheet;
import ej.style.font.FontProfile;
import ej.style.font.FontProfile.FontSize;
import ej.style.outline.SimpleOutline;
import ej.style.selector.ClassSelector;
import ej.style.selector.TypeSelector;
import ej.style.selector.combinator.AndCombinator;
import ej.style.util.EditableStyle;
import ej.style.util.StyleHelper;
import ej.widget.composed.Button;

public class StatsStyleSheet {
	public static void Initialize() {
		GlobalStyleSheet.Initialize();
		InitializeStyleTitle();
		InitializeStyleButtonBack();
		InitializeStyleItemScore();
	}
	
	private static void InitializeStyleButtonBack() {
		Stylesheet css = StyleHelper.getStylesheet();
		
		EditableStyle styleButton =  new EditableStyle();
		
		SimpleOutline spacing = new SimpleOutline(20);
		styleButton.setPadding(spacing);
		
		AndCombinator selector = new AndCombinator(new TypeSelector(Button.class), new ClassSelector("back-button"));
		css.addRule(selector, styleButton);
	}
	
	private static void InitializeStyleTitle() {
		Stylesheet css = StyleHelper.getStylesheet();
		
		EditableStyle style =  new EditableStyle();
		
		FontProfile font = new FontProfile();
		font.setSize(FontSize.LARGE);
		style.setFontProfile(font);
		
		SimpleOutline spacing = new SimpleOutline(10);
		style.setPadding(spacing);

		style.setForegroundColor(Colors.RED);
		
		style.setAlignment(GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		
		css.addRule(new ClassSelector("stats-title-label"), style);
	}
	
	private static void InitializeStyleItemScore() {
		Stylesheet css = StyleHelper.getStylesheet();
		
		EditableStyle style =  new EditableStyle();
		
		FontProfile font = new FontProfile();
		font.setSize(FontSize.SMALL);
		style.setFontProfile(font);
		
		SimpleOutline spacing = new SimpleOutline(10);
		style.setPadding(spacing);
		
		style.setAlignment(GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		
		css.addRule(new ClassSelector("score-item"), style);
	}
}
