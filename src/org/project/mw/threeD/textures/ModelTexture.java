package org.project.mw.threeD.textures;

/**
 * The ModelTexture contains information about the texture and the behavior of the texture of Models.
 * Each TexturedModel has a ModelTexture
 * 
 * @author Philipp Seﬂner
 *
 */
public class ModelTexture {

	private boolean hasTransparency = false;
	
	private int numberOfRows = 1;
	private float reflectivity = 0;
	
	private float shineDamper = 1;
	private int textureID;
	
	private boolean useFakeLighting = false;
	
	public ModelTexture(int id) {
		this.textureID = id;
	}
	
	public int getID() {
		return this.textureID;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public float getReflectivity() {
		return reflectivity;
	}


	public float getShineDamper() {
		return shineDamper;
	}


	public boolean isHasTransparency() {
		return hasTransparency;
	}

	public boolean isUseFakeLighting() {
		return useFakeLighting;
	}

	public void setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
	}
	
	
}
