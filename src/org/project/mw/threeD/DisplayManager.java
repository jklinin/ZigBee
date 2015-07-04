package org.project.mw.threeD;

import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.project.mw.gui.Element;
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

import simpleread.SerPort;

/**
 * Main class for the 3D view. Implements a runnable for calculation in a separate task.
 * Here the window gets created, all 3D-Objects get created and the rendering loop gets executed.
 * After everything is finished, the DisplayManager cleans up.
 * 
 * @author Philipp Seﬂner
 *
 */
public class DisplayManager implements Runnable {
	
	private Thread thread;
	private boolean running = false;
	
	private static final float PIPE_TILE_SIZE = 14f;
	
	private static final String DISPLAY_TITLE = "Pipes";
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	private static final int FPS_CAP = 120;
	
	private static long lastFrameTime;
	private static float delta;

	private static String comPort = null;
	
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
				new ModelTexture(loader.loadTexture("texturePipe")));
		TexturedModel PipeL = new TexturedModel(OBJLoader.loadObjModel("pipeLFinal", loader),
				new ModelTexture(loader.loadTexture("texturePipe")));
		TexturedModel PipeT = new TexturedModel(OBJLoader.loadObjModel("pipeTFinal", loader),
				new ModelTexture(loader.loadTexture("texturePipe")));
		TexturedModel Pump = new TexturedModel(OBJLoader.loadObjModel("pumpFinal", loader),
				new ModelTexture(loader.loadTexture("texturePump")));
		TexturedModel Sensor = new TexturedModel(OBJLoader.loadObjModel("sensorShield", loader),
				new ModelTexture(loader.loadTexture("texturePipe")));
		TexturedModel Faucet = new TexturedModel(OBJLoader.loadObjModel("faucet", loader),
				new ModelTexture(loader.loadTexture("texturePipe")));
		
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
		Sensor.getTexture().setShineDamper(10);
		Sensor.getTexture().setReflectivity(1);
		Sensor.getTexture().setHasTransparency(true);
		Faucet.getTexture().setShineDamper(10);
		Faucet.getTexture().setReflectivity(1);
		Faucet.getTexture().setHasTransparency(true);
		
		List<Entity> pipeModel = new ArrayList<Entity>();
		Map<Integer, Entry<Point, Element>> sensorIdMap = new HashMap<Integer, Entry<Point, Element>>();
		Map<Point, Element> elements = Util.getInstance().getElementsCollection();
		
		for(Entry<Point, Element> tile : elements.entrySet()) {
			if(tile.getValue().getNameElement() != null) {
				float rotationDeg = tile.getValue().getRotation();
				System.out.println(rotationDeg);
				float xPos = (float) tile.getKey().getX();
				float zPos = (float) tile.getKey().getY();
				String tileName = tile.getValue().getNameElement();
				switch(tileName) {
				case "./Resources/Images/IPartImage_1_50.png":
					
					pipeModel.add(new Entity(PipeI, new Vector3f(xPos*PIPE_TILE_SIZE, 0, zPos*PIPE_TILE_SIZE), 0, rotationDeg, 0, 1.0f));
					break;
				case "./Resources/Images/LPartImage_50.png":
					System.out.println(rotationDeg + " " + (rotationDeg-45));
					rotationDeg -= 45f;
					pipeModel.add(new Entity(PipeL, new Vector3f(xPos*PIPE_TILE_SIZE, 0, zPos*PIPE_TILE_SIZE), 0, rotationDeg, 0, 1.0f));
					break;
				case "./Resources/Images/tPartImage_50.png":
					pipeModel.add(new Entity(PipeT, new Vector3f(xPos*PIPE_TILE_SIZE, 0, zPos*PIPE_TILE_SIZE), 0, rotationDeg, 0, 1.0f));
					break;
				case "./Resources/Images/pumpPartImage_50.png":
					pipeModel.add(new Entity(Pump, new Vector3f(xPos*PIPE_TILE_SIZE, 0, zPos*PIPE_TILE_SIZE), 0, rotationDeg, 0, 1.0f));
					break;
				case "./Resources/Images/sensorPartImage.png":
					pipeModel.add(new Entity(Sensor, new Vector3f(xPos*PIPE_TILE_SIZE, 0, zPos*PIPE_TILE_SIZE), 0, rotationDeg, 0, 1.0f));
					sensorIdMap.put(tile.getValue().getSensorID(), tile);
					break;
				case "./Resources/Images/faucPartImage_50.png":
					//TODO Ventil-Model verwenden
					pipeModel.add(new Entity(Faucet, new Vector3f(xPos*PIPE_TILE_SIZE, 0, zPos*PIPE_TILE_SIZE), 0, rotationDeg, 0, 1.0f));
					break;
				}
			}
		}
	
		
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
		
		
		//TODO comPort null
		//comPort = SerPort.setPort("COM4");
		float factor = 0;
		while(running) {
			camera.move();
			
			picker.update();
			
			if(Keyboard.isKeyDown(Keyboard.KEY_ADD)) {
				factor += 0.1;
				System.out.println(factor);
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) {
				factor -= 0.1;
				System.out.println(factor);
			}
			
			List<Entity> stringEntityList = new ArrayList<Entity>();
			//TODO: sensorId von Serial bekommen
			int sensorId = 2;
			Entry<Point, Element> sensorWithId = sensorIdMap.get(sensorId);
			if(sensorWithId != null) {
				float xPos = (float) sensorWithId.getKey().getX();
				float zPos = (float) sensorWithId.getKey().getY();
				
				float adjustmentFactorX = 0;
				float adjustmentFactorY = PIPE_TILE_SIZE/2.8f;
				float adjustmentFactorZ = 0;
				switch(sensorWithId.getValue().getRotation()) {
				case 0:
					adjustmentFactorX = -PIPE_TILE_SIZE*0.15f;
					adjustmentFactorZ = 0.2f;
					break;
				case 90:
					adjustmentFactorX = -0.2f;
					adjustmentFactorZ = -PIPE_TILE_SIZE*0.15f;
					break;
				case 180:
					adjustmentFactorX = PIPE_TILE_SIZE*0.15f;
					adjustmentFactorZ = -0.2f;
					break;
				case 270:
					adjustmentFactorX = 0.2f;
					adjustmentFactorZ = PIPE_TILE_SIZE*0.15f;
					break;
				}
				
				//String portValue = Double.toString(SerPort.getFlowSpeed(SerPort.getValue(SerPort.readPort(comPort))))+("l/m");
				stringEntityList.addAll(fontRenderer.getStringEntityList("Hello World My Name Is John", new Vector3f( xPos*PIPE_TILE_SIZE + adjustmentFactorX, adjustmentFactorY, zPos*(PIPE_TILE_SIZE + adjustmentFactorZ) ), 0, sensorWithId.getValue().getRotation(), 0, 1f));
			}
			
			for(Entity entity : pipeModel) {
				renderer.processEntity(entity);
			}
			for(Entity character : stringEntityList) {
				renderer.processEntity(character);
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