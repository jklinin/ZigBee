package org.project.mw.threeD.models;

/**
 * This Model class contains the VertexArrayObject of the model which contains the VertexBufferObjects.
 * It basically contains all information about the construction of the model and it's triangles.
 * 
 * @author Philipp Seﬂner
 *
 */
public class RawModel {

	private int vaoID;
	private int vertexCount;
	
	public RawModel(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}
	
	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}
}
