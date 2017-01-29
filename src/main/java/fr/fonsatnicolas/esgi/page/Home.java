package fr.fonsatnicolas.esgi.page;

import java.io.IOException;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.container.Dock;
import ej.container.List;
import ej.exit.ExitHandler;
import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.navigation.page.Page;
import ej.style.State;
import ej.style.Stylesheet;
import ej.style.background.NoBackground;
import ej.style.background.SimpleImageBackground;
import ej.style.font.FontProfile;
import ej.style.font.FontProfile.FontSize;
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
import fr.fonsatnicolas.esgi.common.BBColors;

public class Home extends Page {
	
	private Dock container;
	
	public Home() {
		InitializeStyle();
		
		this.container = new Dock(false);
		this.setWidget(this.container);
		
		this.addTitle("BlockBricks");
		this.loadMenu();
	}
	
	private Label addTitle(String name) {
		Label title = new Label(name);
		title.addClassSelector("home-title-label");
		this.container.setFirst(title);
		return title;
	}
	
	private void loadMenu() {
		Button playBtn = new Button("JOUER");
		playBtn.addClassSelector("home-btn");
		playBtn.addClassSelector("home-btn-green");
		playBtn.addOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				System.out.println("JOUER");
			}
		});
		
		Button statsBtn = new Button("CLASSEMENT");
		statsBtn.addClassSelector("home-btn");
		statsBtn.addClassSelector("home-btn-yellow");
		statsBtn.addOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				System.out.println("CLASSEMENT");
			}
		});
		
		Button exitBtn = new Button("QUITTER");
		exitBtn.addClassSelector("home-btn");
		exitBtn.addClassSelector("home-btn-red");
		exitBtn.addOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				System.out.println("QUITTER");
				ExitHandler exithandler = ServiceLoaderFactory.getServiceLoader().getService(ExitHandler.class);
				if (exithandler !=  null) {
					exithandler.exit();
				}
			}
		});
		
		List list = new List(false);
		list.add(playBtn);
		list.add(statsBtn);
		list.add(exitBtn);
		this.container.setCenter(list);
	}
	
	private static void InitializeStyle() {
		Stylesheet css = StyleHelper.getStylesheet();
		EditableStyle style =  new EditableStyle();
		style.setBackgroundColor(BBColors.BLACK);
		style.setForegroundColor(BBColors.CYAN);
		css.addRule(new TypeSelector(Page.class), style);

		InitializeStyleTitle();
		InitializeStyleButton();
		InitializeStyleButtonClass();
		InitializeStyleTransp();
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
	
	private static void InitializeStyleTransp() {
		Stylesheet css = StyleHelper.getStylesheet();
		
		EditableStyle transpStyle =  new EditableStyle();
		transpStyle.setBackground(NoBackground.NO_BACKGROUND);
		
		TypeSelector labelSel = new TypeSelector(Label.class);
		TypeSelector buttonSel = new TypeSelector(Button.class);
		
		css.addRule(new ChildCombinator(buttonSel, labelSel), transpStyle);
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
