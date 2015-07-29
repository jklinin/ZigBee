package org.project.mw.threeD.fonts;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 * The FontModel contains a TrueTypeFont (from the slick library), the text, color and position.
 * The object makes it easier to change 2D-Text during the rendering process.
 * 
 * @author Philipp Seﬂner
 *
 */
public class FontModel {
	
	Color color;
	TrueTypeFont font;
	String text;
	float x, y;

	public FontModel(TrueTypeFont font) {
		this.font = font;
	}
		
	public FontModel(TrueTypeFont font, float x, float y, String text,
			Color color) {
		this.font = font;
		this.x = x;
		this.y = y;
		this.text = text;
		this.color = color;
	}

	/**
	 * draws the prepared true type font string
	 */
	public void drawString() {
		font.drawString(x, y, text, color);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setFont(TrueTypeFont font) {
		this.font = font;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setX(float x) {
		this.x = x;
	}


	public void setY(float y) {
		this.y = y;
	}
}
