package org.project.mw.threeD;

import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
//import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Vector3f;
import org.project.mw.gui.Element;
import org.project.mw.serial.WaterFlowSensorReader;
import org.project.mw.threeD.entities.Camera;
import org.project.mw.threeD.entities.Entity;
import org.project.mw.threeD.entities.Light;
import org.project.mw.threeD.fonts.FontRenderer;
import org.project.mw.threeD.models.TexturedModel;
import org.project.mw.threeD.textures.ModelTexture;
import org.project.mw.util.Util;


/**
 * Main class for the 3D view. Implements a runnable for calculation in a separate task.
 * Here the window gets created, all 3D-Objects get created and the rendering loop gets executed.
 * After everything is finished, the DisplayManager cleans up.
 * 
 * @author Philipp Seﬂner
 *
 */
public class DisplayManager implements Runnable {
	
	private static float delta;
	private static final String DISPLAY_TITLE = "Pipes";
	
	private static final int FPS_CAP = 120;
	
	private static final int HEIGHT = 600;
	private static long lastFrameTime;
	private static final float PIPE_TILE_SIZE = 14f;
	private static final int WIDTH = 800;
	
	/**
	 * closes the 3D-Window
	 */
	public static void closeDisplay() {
		Display.destroy();	
	}
	/**
	 * Creates the 3D Display of LWJGL
	 */
	public static void createDisplay() {
		
		//ContextAttribs attribs = new ContextAttribs(3,2)
		//.withForwardCompatible(true)
		//.withProfileCore(true);
		
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
	 * Returns the difference between the System times between the currently and the previously rendered frame
	 * @return the time between current and previous frame
	 */
	public static float getFrameTimeSeconds() {
		return delta;
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
	 * Returns the current system time. Needed i.e. for real time movement
	 * @return the current system time
	 */
	private static long getCurrentTime() {
		return Sys.getTime()*1000 / Sys.getTimerResolution();
	}
	
	private boolean running = false;
	
	private Thread thread;
	
	/**
	 * Contains creation of every Model and the rendering loop of the 3D-Window
	 */
	public void run() {
		
		createDisplay();
		
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer();
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
				float rotationDeg = tile.getValue().getRotationCounterClockWise();
				float xPos = (float) tile.getKey().getX();
				float zPos = (float) tile.getKey().getY();
				String tileName = tile.getValue().getNameElement();
				switch(tileName) {
				case "./Resources/Images/IPartImage_1_50.png":
					
					pipeModel.add(new Entity(PipeI, new Vector3f(xPos*PIPE_TILE_SIZE, 0, zPos*PIPE_TILE_SIZE), 0, rotationDeg, 0, 1.0f));
					break;
				case "./Resources/Images/LPartImage_50.png":
					rotationDeg -= 45f;
					pipeModel.add(new Entity(PipeL, new Vector3f(xPos*PIPE_TILE_SIZE, 0, zPos*PIPE_TILE_SIZE), 0, rotationDeg, 0, 1.0f));
					break;
				case "./Resources/Images/tPartImage_50.png":
					pipeModel.add(new Entity(PipeT, new Vector3f(xPos*PIPE_TILE_SIZE, 0, zPos*PIPE_TILE_SIZE), 0, rotationDeg, 0, 1.0f));
					break;
				case "./Resources/Images/pumpPartImage_50.png":
					pipeModel.add(new Entity(Pump, new Vector3f(xPos*PIPE_TILE_SIZE, 0, zPos*PIPE_TILE_SIZE), 0, rotationDeg, 0, 2.5f));
					break;
				case "./Resources/Images/sensorPartImage.png":
					pipeModel.add(new Entity(Sensor, new Vector3f(xPos*PIPE_TILE_SIZE, 0, zPos*PIPE_TILE_SIZE), 0, rotationDeg-90, 0, 1.0f));
					sensorIdMap.put(tile.getValue().getSensorID(), tile);
					break;
				case "./Resources/Images/faucPartImage_50.png":
					rotationDeg -= 90f;
					pipeModel.add(new Entity(Faucet, new Vector3f(xPos*PIPE_TILE_SIZE, 0, zPos*PIPE_TILE_SIZE), 0, rotationDeg, 0, 1.0f));
					break;
				}
			}
		}
	
		
		Light light = new Light(new Vector3f(20000,40000,20000),new Vector3f(1, 1, 1));
		
		Camera camera = new Camera();
		
		WaterFlowSensorReader sensorReader = new WaterFlowSensorReader();
		sensorReader.start();
		while(running) {
			camera.move();
			
			List<Entity> stringEntityList = new ArrayList<Entity>();
			int sensorId = sensorReader.getLastSensorId();
			Entry<Point, Element> sensorWithId = sensorIdMap.get(sensorId);
			
			if(sensorWithId != null) {
				DecimalFormat f = new DecimalFormat("#0.00");
				sensorWithId.getValue().setCurrentSensorValue(f.format(sensorReader.getLastFlowSpeed())+("l/m"));
			}
			
			for(Entry<Integer, Entry<Point, Element>> sensorIdMapEntry : sensorIdMap.entrySet()) {
				if(sensorIdMapEntry.getValue().getValue().getCurrentSensorValue() != null) {
					float xPos = (float) sensorIdMapEntry.getValue().getKey().getX();
					float zPos = (float) sensorIdMapEntry.getValue().getKey().getY();
					
					float adjustmentFactorX = 0;
					float adjustmentFactorY = PIPE_TILE_SIZE/2.8f;
					float adjustmentFactorZ = 0;
					switch(sensorIdMapEntry.getValue().getValue().getRotationCounterClockWise()) {
					case 0:
					case 360:
						adjustmentFactorX = -PIPE_TILE_SIZE*0.15f;
						adjustmentFactorZ = 0.2f;
						break;
					case 90:
						adjustmentFactorX = 0.2f;
						adjustmentFactorZ = PIPE_TILE_SIZE*0.15f;
						break;
					case 180:
						adjustmentFactorX = PIPE_TILE_SIZE*0.15f;
						adjustmentFactorZ = -0.2f;
						break;
					case 270:
						adjustmentFactorX = -0.2f;
						adjustmentFactorZ = -PIPE_TILE_SIZE*0.15f;
						break;
					}
					
					stringEntityList.addAll(
							fontRenderer.getStringEntityList(
									sensorIdMapEntry.getValue().getValue().getCurrentSensorValue(),
									new Vector3f( 
											xPos*PIPE_TILE_SIZE + adjustmentFactorX, adjustmentFactorY, zPos*(PIPE_TILE_SIZE + adjustmentFactorZ) 
									), 0, sensorIdMapEntry.getValue().getValue().getRotationCounterClockWise(), 0, 0.5f
							)
					);
				}
			}
			
			for(Entity entity : pipeModel) {
				renderer.processEntity(entity);
			}
			
			for(Entity character : stringEntityList) {
				renderer.processEntity(character);
			}
			
			renderer.render(light, camera);
			updateDisplay();
			
			if(Display.isCloseRequested())
				running = false;
		}
		
		sensorReader.stop();
		renderer.cleanUp();
		loader.cleanUp();
		closeDisplay();
	}
	
	/**
	 * Initializes the seperate thread and starts the 3D-Display. 
	 */
	public void start() {
		running = true;
		thread = new Thread(this, "Pipes");
		thread.start();
	}
}