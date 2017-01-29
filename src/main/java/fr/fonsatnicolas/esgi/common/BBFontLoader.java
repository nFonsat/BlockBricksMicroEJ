package fr.fonsatnicolas.esgi.common;

import ej.style.font.FontProfile;
import ej.style.font.FontProfile.FontSize;
import ej.style.font.loader.AbstractFontLoader;

public class BBFontLoader extends AbstractFontLoader {
	
	private static final int LARGE_HEIGHT  = 50;
	private static final int MEDIUM_HEIGHT = 30;
	private static final int SMALL_HEIGHT  = 15;

	@Override
	protected int getFontHeight(FontProfile fontProfile) {
		FontSize size = fontProfile.getSize();
		System.out.println("BBFontLoader SIZE -- " + size);
		
		switch(size) {
		case LENGTH:
			System.out.println("use BBFontLoader_LENGTH");
			return fontProfile.getSizeValue();
		case LARGE:
			System.out.println("use BBFontLoader_LARGE_HEIGHT");
			return LARGE_HEIGHT;
		case MEDIUM:
			System.out.println("use BBFontLoader_MEDIUM");
			return MEDIUM_HEIGHT;
		case SMALL:
		default:
			System.out.println("use BBFontLoader_SMALL");
			return SMALL_HEIGHT;
		}
	}

}
