package fr.fonsatnicolas.esgi.common;

import ej.style.font.FontProfile;
import ej.style.font.loader.AbstractFontLoader;

public class BBFontLoader extends AbstractFontLoader {
	
	private static final int LARGE_HEIGHT = 50;
	private static final int MEDIUM_HEIGHT = 30;

	@Override
	protected int getFontHeight(FontProfile fontProfile) {
		switch(fontProfile.getSize()) {
		case LENGTH:
			System.out.println("use BBFontLoader_LENGTH");
			return fontProfile.getSizeValue();
		case LARGE:
			System.out.println("use BBFontLoader_LARGE_HEIGHT");
			return LARGE_HEIGHT;
		case MEDIUM:
			System.out.println("use BBFontLoader_MEDIUM");
		default:
			return MEDIUM_HEIGHT;
		}
	}

}
