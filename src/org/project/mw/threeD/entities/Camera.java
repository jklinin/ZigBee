package org.project.mw.threeD.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

/**
 * The camera is a pseudo object in the 3D room. It affects the rendering of all objects in the 3D room.
 * 
 * @author Philipp Seﬂner
 *
 */
public class Camera {
	
	private static final float MOVEMENT_SPEED = 0.6f;
	
	protected float pitch = 45;
	protected Vector3f position = new Vector3f(50,50,50);
	protected float roll;
	protected float yaw = 0;
	
	public float getPitch() {
		return pitch;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getRoll() {
		return roll;
	}

	public float getYaw() {
		return yaw;
	}
	
	/**
	 * Catches the Keyboard and Mouse inputs and "moves the camera".
	 * This method must be called in the main loop.
	 */
	public void move() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.z -= MOVEMENT_SPEED;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z -= -MOVEMENT_SPEED;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x += MOVEMENT_SPEED;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x -= MOVEMENT_SPEED;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			position.y += MOVEMENT_SPEED/2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			position.y -= MOVEMENT_SPEED/2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			changeYaw(-(MOVEMENT_SPEED/2f));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			changeYaw(MOVEMENT_SPEED/2f);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			changePitch(MOVEMENT_SPEED/2f);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			changePitch(-(MOVEMENT_SPEED/2f));
		}
		
		if(Mouse.isButtonDown(1)) {
			changePitch(Mouse.getDY() * 0.1f);
			changeYaw(Mouse.getDX() * 0.3f);
		}
	}
	
	private void changePitch(float pitchChange) {
		float newPitch = pitch - pitchChange;
		if(newPitch <= 85) {
			pitch = newPitch;
		}
	}
	private void changeYaw(float angleChange) {
		yaw -= angleChange;
	}

}
