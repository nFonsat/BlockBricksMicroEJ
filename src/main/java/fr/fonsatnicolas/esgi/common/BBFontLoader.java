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
			return fontProfile.getSizeValue();
		case LARGE:
			return LARGE_HEIGHT;
		case MEDIUM:
		default:
			return MEDIUM_HEIGHT;
		}
	}

}
