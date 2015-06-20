package org.project.mw.threeD.models;

import org.project.mw.threeD.textures.ModelTexture;

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
