package polito.sharpen42.Flappy;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import org.lwjgl.opengl.*;
import org.lwjgl.glfw.GLFWVidMode;

import polito.sharpen42.Flappy.graphics.Shader;
import polito.sharpen42.Flappy.input.Input;
import polito.sharpen42.Flappy.math.Matrix4f;

public class Main implements Runnable /* implementa solo il metodo 'run()'*/{
	private int width = 1200;
	private int height = 720;
	
	private Thread thread;
	private boolean running = true;
	
	private long window;
	
	public void start() {
		running = true;
		// si crea una thread propria del gioco così da evitare conflitti con quelle già presenti
		thread = new Thread(this, "Game"); 
		thread.start();
	}
	
	private void init() {
		if(!glfwInit()) {
			// TODO: handle error
			glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
			glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
			return;
		}
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(width, height, "Flappy Bird", 0, 0);
		if(window == 0) {
			// TODO: handle error
			return;
		}
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height)/2);
		
		glfwSetKeyCallback(window, new Input());
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
		GL.createCapabilities();
		
		glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		System.out.println("OpenGL: " + glGetString(GL_VERSION));
		Shader.loadAll();
		
		Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f);
		Shader.BACKGROUND.setUniformMat4f("pr_matrix", pr_matrix);
		
		}
	
	public void run() {
		// tutto questo succede nella nuova thread
		init(); // init e render devono essere eseguiti sulla stessa thread
		while(running) {
			update();
			render();
			
			if(glfwWindowShouldClose(window)) 
				running = false;
		}
	}
	
	private void update() {
		glfwPollEvents();
		if(Input.keys[GLFW_KEY_SPACE]) {
			System.out.println("FLAP!");
		}
		
	}
	
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glfwSwapBuffers(window);
	}

	public static void main(String[] args) {
		new Main().start();

	}

}
