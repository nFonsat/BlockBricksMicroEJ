package fr.fonsatnicolas.esgi.page;

import ej.container.Dock;
import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.mwt.Widget;
import ej.navigation.page.Page;
import ej.style.Stylesheet;
import ej.style.font.FontProfile;
import ej.style.font.FontProfile.FontSize;
import ej.style.outline.SimpleOutline;
import ej.style.selector.ClassSelector;
import ej.style.selector.TypeSelector;
import ej.style.util.EditableStyle;
import ej.style.util.StyleHelper;
import ej.widget.basic.Label;
import ej.widget.composed.Button;
import ej.widget.listener.OnClickListener;
import fr.fonsatnicolas.esgi.BlockBricksActivity;
import fr.fonsatnicolas.esgi.common.BBColors;

public class Stats extends Page {
	
	private Dock container;
	
	
	public Stats() {
		InitializeStyle();
		
		this.container = new Dock(false);
		this.setWidget(this.container);
		this.container.setFirst(this.initTopBar());
	}
	
	private Widget initTopBar() {
		Dock topBar = new Dock();
		
		Button back = new Button("BACK");
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
	
	private static void InitializeStyle() {
		InitializeStyleTitle();
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

}
