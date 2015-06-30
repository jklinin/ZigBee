package org.project.mw.threeD.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private static final float MOVEMENT_SPEED = 0.6f;
	
	protected Vector3f position = new Vector3f(0,50,50);
	protected float pitch = 45;
	protected float yaw = 0;
	protected float roll;
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
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
