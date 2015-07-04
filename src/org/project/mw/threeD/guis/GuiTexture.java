package org.project.mw.threeD.guis;

import org.lwjgl.util.vector.Vector2f;

/**
 * The GuiTexture contains the texture, position and scale of a GUI object
 * 
 * @author Philipp Seﬂner
 *
 */
public class GuiTexture {

	private int texture;
	private Vector2f position;
	private Vector2f scale;
	
	public GuiTexture(int texture, Vector2f position, Vector2f scale) {
		this.texture = texture;
		this.position = position;
		this.scale = scale;
	}

	public int getTexture() {
		return texture;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getScale() {
		return scale;
	}
	
	
}
