package org.project.mw.threeD.models;

import org.project.mw.threeD.textures.ModelTexture;

/**
 * Model class combining a RawModel with a ModelTexture.
 * This Model can be rendered in the 3D view.
 * 
 * @author Philipp Seﬂner
 *
 */
public class TexturedModel {

	private RawModel rawModel;
	private ModelTexture texture;
	
	public TexturedModel(RawModel model, ModelTexture texture) {
		this.rawModel = model;
		this.texture = texture;
	}

	public RawModel getRawModel() {
		return rawModel;
	}

	public ModelTexture getTexture() {
		return texture;
	}
	
	
}
