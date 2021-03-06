package org.project.mw.threeD.entities;

import org.lwjgl.util.vector.Vector3f;
import org.project.mw.threeD.models.TexturedModel;

/**
 * An Entity contains the position, rotation and scale of a TexturedModel for rendering it in 3D.
 * 
 * @author Philipp Se�ner
 *
 */
public class Entity {

	private TexturedModel model;
	private Vector3f position;
	private float rotX,rotY,rotZ;
	private float scale;
	
	private int textureIndex = 0;
	
	public Entity(TexturedModel model, int index, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.textureIndex = index;
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public TexturedModel getModel() {
		return model;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public float getRotX() {
		return rotX;
	}
	
	public float getRotY() {
		return rotY;
	}
	
	public float getRotZ() {
		return rotZ;
	}

	public float getScale() {
		return scale;
	}

	/**
	 * Returns the texture x offset. Necessary when using texture atlases (means multiple textures in one image)
	 * @return
	 */
	public float getTextureXOffset() {
		int column = textureIndex % model.getTexture().getNumberOfRows();
		return (float) column / (float) model.getTexture().getNumberOfRows();
	}

	/**
	 * Returns the texture y offset. Necessary when using texture atlases (means multiple textures in one image)
	 * @return
	 */
	public float getTextureYOffset() {
		int row = textureIndex/model.getTexture().getNumberOfRows();
		return (float) row / (float) model.getTexture().getNumberOfRows();
	}

	/**
	 * Increases the position of the entity. Therefore simulates the movement of the entity in the 3D display when
	 * called in the main loop
	 * @param dx
	 * @param dy
	 * @param dz
	 */
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	/**
	 * Increases the rotation value of the entity. Therefore simulates a rotating entity in the 3D display when
	 * called in the main loop
	 * @param dx
	 * @param dy
	 * @param dz
	 */
	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	
	
}
