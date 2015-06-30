package org.project.mw.threeD;

import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.project.mw.threeD.entities.Camera;
import org.project.mw.threeD.entities.Entity;
import org.project.mw.threeD.entities.Light;
import org.project.mw.threeD.fonts.FontRenderer;
import org.project.mw.threeD.fonts.FontModel;
import org.project.mw.threeD.guis.GuiRenderer;
import org.project.mw.threeD.guis.GuiTexture;
import org.project.mw.threeD.models.TexturedModel;
import org.project.mw.threeD.textures.ModelTexture;
import org.project.mw.threeD.toolbox.MousePicker;
import org.project.mw.util.Util;

public class DisplayManager implements Runnable {
	
	private Thread thread;
	private boolean running = false;
	
	private static final float PIPE_TILE_SIZE = 13.05f;
	
	private static final String DISPLAY_TITLE = "Pipes";
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 120;
	
	private static long lastFrameTime;
	private static float delta;
	
	/**
	 * Creates the 3D Display of LWJGL
	 */
	public static void createDisplay() {
		
		ContextAttribs attribs = new ContextAttribs(3,2)
		.withForwardCompatible(true)
		.withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
			//Display.create(new PixelFormat(), attribs);
			Display.create(new PixelFormat());
			//Display.create();
			Display.setTitle(DISPLAY_TITLE);
			//Display.setIcon(icons);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
			
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime = getCurrentTime();
	}
	
	/**
	 * Call this method after each iteration of the endless loop of the 3D-rendering
	 * to update the display
	 */
	public static void updateDisplay() {
		Display.sync(FPS_CAP);
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime)/1000f;
		lastFrameTime = currentFrameTime;
	}
	
	/**
	 * Returns the difference between the System times between the currently and the previously rendered frame
	 * @return the time between current and previous frame
	 */
	public static float getFrameTimeSeconds() {
		return delta;
	}
	
	/**
	 * closes the 3D-Window
	 */
	public static void closeDisplay() {
		Display.destroy();	
	}
	
	/**
	 * Returns the current system time. Needed i.e. for real time movement
	 * @return the current system time
	 */
	private static long getCurrentTime() {
		return Sys.getTime()*1000 / Sys.getTimerResolution();
	}
	
	/**
	 * Initializes the seperate thread and starts the 3D-Display. 
	 */
	public void start() {
		running = true;
		thread = new Thread(this, "Pipes");
		thread.start();
	}
	
	/**
	 * Contains creation of every Model and the rendering loop of the 3D-Window
	 */
	public void run() {
		
		createDisplay();
		
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer();
		//GuiRenderer guiRenderer = new GuiRenderer(loader);
		FontRenderer fontRenderer = new FontRenderer();
		
		TexturedModel PipeI = new TexturedModel(OBJLoader.loadObjModel("pipeIFinal", loader),
				new ModelTexture(loader.loadTexture("dragonTexture")));
		TexturedModel PipeL = new TexturedModel(OBJLoader.loadObjModel("pipeLFinal", loader),
				new ModelTexture(loader.loadTexture("dragonTexture")));
		TexturedModel PipeT = new TexturedModel(OBJLoader.loadObjModel("pipeTFinal", loader),
				new ModelTexture(loader.loadTexture("dragonTexture")));
		TexturedModel Pump = new TexturedModel(OBJLoader.loadObjModel("pumpFinal", loader),
				new ModelTexture(loader.loadTexture("dragonTexture")));
		
		PipeI.getTexture().setShineDamper(10);
		PipeI.getTexture().setReflectivity(1);
		PipeI.getTexture().setHasTransparency(true);
		PipeL.getTexture().setShineDamper(10);
		PipeL.getTexture().setReflectivity(1);
		PipeL.getTexture().setHasTransparency(true);
		PipeT.getTexture().setShineDamper(10);
		PipeT.getTexture().setReflectivity(1);
		PipeT.getTexture().setHasTransparency(true);
		Pump.getTexture().setHasTransparency(true);
		
		List<Entity> pipeModel = new ArrayList<Entity>();
		Map<Point, JButton> elements = Util.getInstance().getElementsCollection();
		/*
		for(Map.Entry<Point, JButton> tile : elements.entrySet()) {
			
			//tile rotation
			float rotationDeg = 0f;
			switch(tile.getValue().getName()) {
			case "DOWN":
				rotationDeg = 0f;
				break;
			case "UP":
				rotationDeg = 90f;
				break;
				
			case "UPSIDE_DOWN":
				rotationDeg = 270f;
				break;
			case "ABOUT_CENTER":
				rotationDeg = 180f;
					break;
			}
			//tile position
			float xPos = (float) tile.getKey().getX();
			float yPos = (float) tile.getKey().getY();
			//type of tile, does this get the icon name?
			String tileName = tile.getValue().getIcon().toString();
			 
			switch(tileName) {
			case "IPartImage":
				pipeModel.add(new Entity(PipeI, new Vector3f(xPos*PIPE_TILE_SIZE, 0, yPos*PIPE_TILE_SIZE), 0, rotationDeg, 0, 1.0f));
				break;
			case "LPartImage":
				pipeModel.add(new Entity(PipeL, new Vector3f(xPos*PIPE_TILE_SIZE, 0, yPos*PIPE_TILE_SIZE), 0, rotationDeg, 0, 1.0f));
				break;
			case "tPartImage":
				pipeModel.add(new Entity(PipeT, new Vector3f(xPos*PIPE_TILE_SIZE, 0, yPos*PIPE_TILE_SIZE), 0, rotationDeg, 0, 1.0f));
				break;
			case "pumpPartImage":
				pipeModel.add(new Entity(Pump, new Vector3f(xPos*PIPE_TILE_SIZE, 0, yPos*PIPE_TILE_SIZE), 0, rotationDeg, 0, 1.0f));
				break;
			}
		}
		*/
		
		pipeModel.add(new Entity(PipeI, new Vector3f(0, 0, 0), 0, 0, 0, 1.0f));
		//pipeModel.add(new Entity(PipeI, new Vector3f(13.05f, 0, 0), 0, 0, 0, 1.0f));
		
		Light light = new Light(new Vector3f(20000,40000,20000),new Vector3f(1, 1, 1));
		
		Camera camera = new Camera();
		
		//List<GuiTexture> guis = new ArrayList<GuiTexture>();
		//GuiTexture gui = new GuiTexture(loader.loadTexture("cobble"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		//guis.add(gui);

		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix());
	
		
		//Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		//TrueTypeFont ttfont = new TrueTypeFont(awtFont, false);
		//FontModel font = new FontModel(ttfont, 100, 50, "Hello", Color.yellow);
		
		//List<FontModel> fonts = new ArrayList<FontModel>();
		//fonts.add(font);
		
		while(running) {
			camera.move();
			
			picker.update();
			
			//TODO: Text von Sensor-Wert bekommen und Variable anstelle von "Hello" einfügen
			List<Entity> stringEntityList = fontRenderer.getStringEntityList("Hello", new Vector3f(2,2,2), 0, 45f, 45f, 1f);
			pipeModel.addAll(stringEntityList);
			for(Entity entity : pipeModel) {
				renderer.processEntity(entity);
			}
			
			renderer.render(light, camera);
			//guiRenderer.render(guis);
			//fontRenderer.renderTTF(fonts);
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