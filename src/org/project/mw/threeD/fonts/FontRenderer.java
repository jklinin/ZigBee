package org.project.mw.threeD.fonts;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.TextureImpl;
import org.project.mw.threeD.Loader;
import org.project.mw.threeD.entities.Entity;
import org.project.mw.threeD.models.RawModel;
import org.project.mw.threeD.models.TexturedModel;
import org.project.mw.threeD.textures.ModelTexture;
import org.project.mw.threeD.toolbox.Maths;


public class FontRenderer {
	
	private static final float CHARACTER_WIDTH = 3f;
	private static final TexturedModel fontTextureModel = getTexturedFontModel();
	
	public static TexturedModel getTexturedFontModel() {
		Loader loader = new Loader();
		ModelTexture calibriFontTexture = new ModelTexture(loader.loadTexture("CalibriBitmapFont"));
		calibriFontTexture.setNumberOfRows(16);
		calibriFontTexture.setUseFakeLighting(true);
		calibriFontTexture.setHasTransparency(true);
		calibriFontTexture.setReflectivity(0);
		
		/*simple square BEGIN*/
		float[] vertices = { 
			-1f,  1f,  0f, 
			-1f, -1f,  0f, 
			 1f, -1f,  0f, 
			 1f,  1f,  0f
		};
		int[] indices = {
			0,1,3,
			3,1,2
		};
		float[] normals = {
			0,1,0,
			0,1,0
		};
		float[] textureCoords = {
			0,0,
			0,1,
			1,1,
			1,0
		};
		/*simple square END*/
		
		RawModel calibriFontRawModel = loader.loadToVAO(vertices, textureCoords, normals, indices);
		TexturedModel calibriFontModel = new TexturedModel(calibriFontRawModel, calibriFontTexture);
		return calibriFontModel;
	}
	
	/**
	 * Shorthand method to get an entity list of all characters in the string.
	 * Sets Rotation to 0 and scaling to 1.0
	 * @param string
	 * @param startingPosition
	 * @return
	 */
	public List<Entity> getStringEntityList(String string, Vector3f startingPosition) {
		return getStringEntityList(string, startingPosition, 0, 0, 0, 1.0f);
	}
	
	/**
	 * Creates an entity list of all characters in the string.
	 * Takes into account rotation and scaling of the string.
	 * Therefore rendering in 3D!
	 * @param string
	 * @param startingPosition
	 * @param rotX
	 * @param rotY
	 * @param rotZ
	 * @param scale
	 * @return
	 */
	public List<Entity> getStringEntityList(String string, Vector3f startingPosition, float rotX, float rotY, float rotZ, float scale) {
		List<Entity> stringEntityList = new ArrayList<Entity>();
		for(int i=0; i < string.length(); i++) {
			int asciiCode = (int)string.charAt(i);
			
			float charPosInString = i * CHARACTER_WIDTH * scale / 3;

			Matrix4f transformationMatrix = Maths.createTransformationMatrix(startingPosition, rotX, rotY, rotZ, scale);
			Vector4f characterPosition = new Vector4f(startingPosition.x+charPosInString, startingPosition.y, startingPosition.z, scale);
			
			Vector4f translatedCharacterPosition = new Vector4f();
			Matrix4f.transform(transformationMatrix, characterPosition, translatedCharacterPosition);
			
			
			stringEntityList.add(new Entity(fontTextureModel, asciiCode, new Vector3f(translatedCharacterPosition.x,translatedCharacterPosition.y,translatedCharacterPosition.z), rotX, rotY, rotZ, translatedCharacterPosition.w));
		}
		return stringEntityList;
	}

	/**
	 * Renders a list of Font Models, consisting of a string in a TrueType font and font size, the x and y position.
	 * Rendering in 2D!
	 * @param fonts
	 */
	public void renderTTF(List<FontModel> fonts) {
		GL11.glDrawBuffer(GL11.GL_FRONT_AND_BACK);
		GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        
        GL11.glShadeModel(GL11.GL_SMOOTH);
        
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		TextureImpl.bindNone();
        
 
        GL11.glViewport(0,0,Display.getWidth(),Display.getHeight());
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
 
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		for(FontModel font: fonts) {
			font.drawString();
		}
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
	}
}
