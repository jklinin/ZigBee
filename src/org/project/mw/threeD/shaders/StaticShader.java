package org.project.mw.threeD.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.project.mw.threeD.entities.Camera;
import org.project.mw.threeD.entities.Light;
import org.project.mw.threeD.toolbox.Maths;

/**
 * The StaticShader is needed for the OpenGL shader programs (vertexShader and fragmentShader).
 * It loads uniform variables the the vertex- and fragment shaders.
 * 
 * @author Philipp Seﬂner
 *
 */
public class StaticShader extends ShaderProgram {
	
	public static final String VERTEX_FILE = "src/org/project/mw/threeD/shaders/vertexShader.txt";
	public static final String FRAGMENT_FILE = "src/org/project/mw/threeD/shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColour;
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_useFakeLighting;
	private int location_skyColour;
	private int location_numberOfRows;
	private int location_offset;
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_lightPosition = super.getUniformLocation("lightPosition");
		location_lightColour = super.getUniformLocation("lightColour");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_useFakeLighting = super.getUniformLocation("useFakeLighting");
		location_skyColour = super.getUniformLocation("skyColour");
		location_numberOfRows = super.getUniformLocation("numberOfRows");
		location_offset = super.getUniformLocation("offset");
	}
	
	/**
	 * Loads the number of rows of the texture atlas into the shader program
	 * @param numberOfRows
	 */
	public void loadNumberOfRows(int numberOfRows) {
		super.loadFloat(location_numberOfRows, numberOfRows);
	}
	
	/**
	 * Loads the x and y offset of the texture in the texture atlas into the shader program
	 * @param x
	 * @param y
	 */
	public void loadOffset(float x, float y) {
		super.load2DVector(location_offset, new Vector2f(x, y));
	}
	
	/**
	 * Loads the red, green and blue values for the sky colour into the shader program
	 * @param r
	 * @param g
	 * @param b
	 */
	public void loadSkyColour(float r, float g, float b) {
		super.loadVector(location_skyColour, new Vector3f(r,g,b));
	}
	
	/**
	 * Specifies weather or not an entity uses fake lighting and loads it into the shader program
	 * @param useFake
	 */
	public void loadFakeLightingVariable(boolean useFake) {
		super.loadBoolean(location_useFakeLighting, useFake);
	}
	
	/**
	 * Loads the shine variables, shine damper and reflectivity, into the shader program
	 * @param damper
	 * @param reflectivity
	 */
	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	
	/**
	 * Loads the transformation matrix into the shader program
	 * @param matrix
	 */
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	/**
	 * Loads the lighting position and colour into the shader program
	 * @param light
	 */
	public void loadLight(Light light) {
		super.loadVector(location_lightPosition, light.getPosition());
		super.loadVector(location_lightColour, light.getColour());
	}
	
	/**
	 * Loads the view matrix into the shader program
	 * @param camera
	 */
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	/**
	 * Loads the projection matrix into the shader program
	 * @param projection
	 */
	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}
}
