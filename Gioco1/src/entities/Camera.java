package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch = 0f;
	private float yaw = 0f;
	private float roll = 0f;
	
	public Camera() {
	}

	public void move() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			float ds = .8f;
			float y = (float) Math.toRadians(yaw);
			//System.out.println("prima = yaw: " + yaw + ", x: " + position.x + ", z: " + position.z);
			position.x -= (float) (-ds * Math.sin(y));
			position.z -= (float) (ds * Math.cos(y));
			//System.out.println("dopo = yaw: " + yaw + ", x: " + position.x + ", z: " + position.z);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			float ds = .8f;
			float y = (float) Math.toRadians(yaw);
			//System.out.println("prima = yaw: " + yaw + ", x: " + position.x + ", z: " + position.z);
			position.x += (float) (-ds * Math.sin(y));
			position.z += (float) (ds * Math.cos(y));
			//System.out.println("dopo = yaw: " + yaw + ", x: " + position.x + ", z: " + position.z);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			float ds = .8f;
			float y = (float) Math.toRadians(yaw);
			//System.out.println("prima = yaw: " + yaw + ", x: " + position.x + ", z: " + position.z);
			position.x -= (float) (ds * Math.cos(y));
			position.z -= (float) (ds * Math.sin(y));
			//System.out.println("dopo = yaw: " + yaw + ", x: " + position.x + ", z: " + position.z);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			float ds = .8f;
			float y = (float) Math.toRadians(yaw);
			//System.out.println("prima = yaw: " + yaw + ", x: " + position.x + ", z: " + position.z);
			position.x += (float) (ds * Math.cos(y));
			position.z += (float) (ds * Math.sin(y));
			//System.out.println("dopo = yaw: " + yaw + ", x: " + position.x + ", z: " + position.z);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			position.y += .8f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
			position.y -= .8f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			pitch += 0.5;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			pitch -= 0.5;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			yaw += 0.5;
			System.out.println("yaw: " + yaw);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			yaw -= 0.5;
			//System.out.println("yaw: " + yaw);
		}
	}
	
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
	
	

}
