package org.project.mw.threeD.entities;

import org.lwjgl.util.vector.Vector3f;

/**
 * Lighting class containing position and color of the light source. 
 * 
 * @author Philipp Seﬂner
 *
 */
public class Light {
	private Vector3f colour;
	private Vector3f position;
	
	public Light(Vector3f position, Vector3f colour) {
		super();
		this.position = position;
		this.colour = colour;
	}

	public Vector3f getColour() {
		return colour;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setColour(Vector3f colour) {
		this.colour = colour;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	
}
