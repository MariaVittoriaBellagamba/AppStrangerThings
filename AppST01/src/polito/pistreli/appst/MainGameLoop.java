package polito.pistreli.appst;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import polito.pistreli.appst.engine.Window;

public class MainGameLoop {
	
	private Window window;
	// private Scene scene;
	private boolean running = true;
	
	public void start() {
		window = new Window("App", 1280, 720);
		window.create();
		window.show();
		
		while(running) {
			update();
			render();
			if(window.close())
				running = false;
		}
		window.destroy();
	}
	
	private void update() {
		glfwPollEvents();
	}
	
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		// scene.render();
		int error = glGetError();
		if(error != GL_NO_ERROR) System.out.println(error);
		glfwSwapBuffers(window.getID());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainGameLoop().start();
	}

}
