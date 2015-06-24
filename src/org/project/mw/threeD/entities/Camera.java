package org.project.mw.threeD.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	protected Vector3f position = new Vector3f(0,0,0);
	protected float pitch = 20;
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
	
	public void move() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.z -= 1.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z -= -1.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x += 1.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x -= 1.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			position.y += 0.3f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			position.y -= 0.3f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			changeYaw(0.6f);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			changeYaw(-0.6f);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			changePitch(-0.6f);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			changePitch(0.6f);
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
