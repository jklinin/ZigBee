package org.project.mw.threeD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.project.mw.threeD.entities.Camera;
import org.project.mw.threeD.entities.Entity;
import org.project.mw.threeD.entities.Light;
import org.project.mw.threeD.models.TexturedModel;
import org.project.mw.threeD.shaders.StaticShader;

/**
 * The MasterRenderer renders the 3D world considering the light source and the camera position.
 * 
 * @author Philipp Seﬂner
 *
 */
public class MasterRenderer {

	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	
	private static final float RED = 0f;
	private static final float GREEN = 0f;
	private static final float BLUE = 0f;
	
	private Matrix4f projectionMatrix;
	
	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer;
	
	private Map<TexturedModel,List<Entity>> entities = new HashMap<TexturedModel,List<Entity>>();
	
	public MasterRenderer() {
		enableCulling();
		createProjectionMatrix();
		renderer = new EntityRenderer(shader,projectionMatrix);
	}
	
	/**
	 * Returns the projection matrix. Needed for correct rendering of objects considering
	 * <ul>
	 * 	<li>scaling due to distance of objects</li>
	 * 	<li>translating due to pseudo camera position</li>
	 * 	<li>translating due to object rotation</li>
	 * </ul>
	 * @return the projection matrix
	 */
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
	/**
	 * Enables face culling for 3D-Rendering.
	 * Tells OpenGL to not render triangles not facing to the camera and therefore increasing performance.
	 */
	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	/**
	 * Disables face culling for 3D-Rendering
	 */
	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	/**
	 * Renders registered entities considering camera and light position
	 * @param sun the main light source
	 * @param camera the camera
	 */
	public void render(Light sun, Camera camera) {
		prepare();
		shader.start();
		shader.loadSkyColour(RED, GREEN, BLUE);
		shader.loadLight(sun);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		entities.clear();
	}
	
	/**
	 * registers entities in the MasterRenderer. Takes into account to not add TexturedModels multiple times.
	 * @param entity
	 */
	public void processEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
	
	/**
	 * Cleans up after rendering is finished.
	 * Should be called after the main loop is exited.
	 */
	public void cleanUp() {
		shader.cleanUp();
	}
	
	/**
	 * prepares OpenGL for entity rendering.
	 * Includes depth test and clearing from previous frames.
	 * Sets the background color.
	 */
	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(RED, GREEN, BLUE, 1);
	}
	
	private void createProjectionMatrix() {
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1 / Math.tan(Math.toRadians(FOV / 2))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}
}
