package org.project.mw.threeD;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.project.mw.gui.Element;
import org.project.mw.threeD.entities.Camera;
import org.project.mw.threeD.entities.Entity;
import org.project.mw.threeD.entities.Light;
import org.project.mw.threeD.guis.GuiRenderer;
import org.project.mw.threeD.guis.GuiTexture;
import org.project.mw.threeD.models.TexturedModel;
import org.project.mw.threeD.textures.ModelTexture;
import org.project.mw.threeD.toolbox.MousePicker;

public class DisplayManager implements Runnable {
	
	private Thread thread;
	private boolean running = false;
	
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 120;
	
	private static long lastFrameTime;
	private static float delta;
	
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
		lastFrameTime = getCurrentTime();
	}
	
	public static void updateDisplay() {
		Display.sync(FPS_CAP);
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime)/1000f;
		lastFrameTime = currentFrameTime;
	}
	
	public static float getFrameTimeSeconds() {
		return delta;
	}
	
	public static void closeDisplay() {
		Display.destroy();	
	}
	
	private static long getCurrentTime() {
		return Sys.getTime()*1000 / Sys.getTimerResolution();
	}
	
	
	public void start() {
		running = true;
		thread = new Thread(this, "Pipes");
		thread.start();
	}
	
	public void run() {
		
		createDisplay();
		
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer();
		//GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		//*************TERRAIN TEXTURE STUFF*************
		
		//TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		//TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		//TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		//TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		//TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		//TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		
		//***********************************************
		
		TexturedModel dragon = new TexturedModel(OBJLoader.loadObjModel("dragon", loader),
				new ModelTexture(loader.loadTexture("dragonTexture")));
		dragon.getTexture().setShineDamper(10);
		dragon.getTexture().setReflectivity(1);
		
		List<Element> Model2d = new ArrayList<Element>();
		
		TexturedModel PipeI = new TexturedModel(OBJLoader.loadObjModel("pipeI", loader),
				new ModelTexture(loader.loadTexture("bunny")));
		TexturedModel PipeL = new TexturedModel(OBJLoader.loadObjModel("pipeL", loader),
				new ModelTexture(loader.loadTexture("bunny")));
		TexturedModel PipeT = new TexturedModel(OBJLoader.loadObjModel("pipeT", loader),
				new ModelTexture(loader.loadTexture("bunny")));
		TexturedModel Pump = new TexturedModel(OBJLoader.loadObjModel("pumpe", loader),
				new ModelTexture(loader.loadTexture("bunny")));
		
		PipeI.getTexture().setShineDamper(10);
		PipeI.getTexture().setReflectivity(1);
		PipeL.getTexture().setShineDamper(10);
		PipeL.getTexture().setReflectivity(1);
		PipeT.getTexture().setShineDamper(10);
		PipeT.getTexture().setReflectivity(1);
		
		List<Entity> pipeModel = new ArrayList<Entity>();
		for(Element tile: Model2d) {
			int rotationDeg=0;
			switch(tile.getRotation()) {
			case ABOUT_CENTER:
				
				break;
			case DOWN:
				rotationDeg = 90;
				break;
			case UP:
				rotationDeg = 270;
				break;
			case UPSIDE_DOWN:
				rotationDeg = 180;
				break;
			default:
				rotationDeg=0;
				break;
			}
			if(tile.getFileIconName() == "") {
				pipeModel.add(new Entity(PipeI, new Vector3f(tile.getPositionX(), 0, tile.getPositionY()), 0, rotationDeg, 0, 1.0f));
			}
			if(tile.getFileIconName() == "") {
				pipeModel.add(new Entity(PipeL, new Vector3f(tile.getPositionX(), 0, tile.getPositionY()), 0, rotationDeg, 0, 1.0f));
			}
			if(tile.getFileIconName() == "") {
				pipeModel.add(new Entity(PipeT, new Vector3f(tile.getPositionX(), 0, tile.getPositionY()), 0, rotationDeg, 0, 1.0f));
			}
			if(tile.getFileIconName() == "") {
				pipeModel.add(new Entity(Pump, new Vector3f(tile.getPositionX(), 0, tile.getPositionY()), 0, rotationDeg, 0, 1.0f));
			}
		}
		
		pipeModel.add(new Entity(dragon, new Vector3f(0, 0, 0), 0, 0, 0, 1.0f));
		
		Light light = new Light(new Vector3f(20000,40000,20000),new Vector3f(1, 1, 1));
		
		//Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap);
		//Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap);
		
		Camera camera = new Camera();
		
		//List<GuiTexture> guis = new ArrayList<GuiTexture>();
		//GuiTexture gui = new GuiTexture(loader.loadTexture("cobble"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		//guis.add(gui);

		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix());
	
		while(running) {
			camera.move();
			
			picker.update();
			System.out.println(picker.getCurrentRay());
			
			//renderer.processTerrain(terrain);
			//renderer.processTerrain(terrain2);
			
			//this code for each object
			for(Entity entity : pipeModel) {
				renderer.processEntity(entity);
			}
			
			renderer.render(light, camera);
			//guiRenderer.render(guis);
			updateDisplay();
			
			if(Display.isCloseRequested())
				running = false;
		}
		
		//guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		closeDisplay();
	}
}