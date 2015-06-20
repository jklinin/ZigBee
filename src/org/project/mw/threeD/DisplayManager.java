package org.project.mw.threeD;

import static org.lwjgl.opengl.GL11.GL_TRUE;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Vector3f;
import org.project.mw.threeD.entities.Camera;
import org.project.mw.threeD.entities.Entity;
import org.project.mw.threeD.entities.Light;
import org.project.mw.threeD.models.RawModel;
import org.project.mw.threeD.models.TexturedModel;
import org.project.mw.threeD.shaders.StaticShader;
import org.project.mw.threeD.textures.ModelTexture;

public class DisplayManager implements Runnable {
	
	private Thread thread;
	private boolean running = false;
	
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 120;
	
	public static void createDisplay() {
		
		ContextAttribs attribs = new ContextAttribs(3,2)
		.withForwardCompatible(true)
		.withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Pipes");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
			
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		
	}
	
	public static void updateDisplay() {
		
		Display.sync(FPS_CAP);
		Display.update();
	}
	
	public static void closeDisplay() {
		
		Display.destroy();
		
	}
	
	public void start() {
		running = true;
		thread = new Thread(this, "Pipes");
		thread.start();
	}
	
	public void run() {
		
		
		createDisplay();
		
		Loader loader = new Loader();
		
		
		RawModel model = OBJLoader.loadObjModel("dragon", loader);

		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("dragonTexture")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-50),0,0,0,1);
		Light light = new Light(new Vector3f(0,0,-20),new Vector3f(1,1,1));
		
		Camera camera = new Camera();
		
		MasterRenderer renderer = new MasterRenderer();
		while(running) {
			entity.increaseRotation(0, 1, 0);
			camera.move();
			
			//this code for each object
			renderer.processEntity(entity);
			
			renderer.render(light, camera);
			updateDisplay();
			
			if(Display.isCloseRequested())
				running = false;
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		closeDisplay();
	}
}
