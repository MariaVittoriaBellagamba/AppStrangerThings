package polito.pistreli.appst.engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {
	private long window;
	private int width, height;
	private String name;
	
	public Window(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
	}
	
	public void create() {
		if(!glfwInit()) {
			// TODO: handle error
			return;
		}
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		window = glfwCreateWindow(width, height, name, NULL, NULL);
		if(window == 0) {
			// TODO: handle error
			return;
		}
	}
	
	public void show() {
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		glfwSetKeyCallback(window, new Input());
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		GL.createCapabilities();
		
		glClearColor(.0f, .0f, .0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
	}
	
	public boolean close() {
		return glfwWindowShouldClose(window);
	}
	
	public void destroy() {
		glfwDestroyWindow(window);
		glfwTerminate();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public long getID() {
		return window;
	}
}
