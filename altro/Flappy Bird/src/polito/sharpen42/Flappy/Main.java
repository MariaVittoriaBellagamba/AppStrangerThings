package polito.sharpen42.Flappy;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import polito.sharpen42.Flappy.graphics.Shader;
import polito.sharpen42.Flappy.input.Input;
import polito.sharpen42.Flappy.level.Level;
import polito.sharpen42.Flappy.math.Matrix4f;

public class Main implements Runnable /* implementa solo il metodo 'run()' */{
	private int width = 1200;
	private int height = 720;
	
	private Thread thread;
	private boolean running = true;
	
	private long window;
	
	private Level level;
	
	public void start() {
		running = true;
		// si crea una thread propria del gioco così da evitare conflitti con quelle già presenti
		thread = new Thread(this, "Game"); 
		thread.start();
	}
	
	private void init() {
		if(!glfwInit()) {
			// TODO: handle error
			return;
		}
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		window = glfwCreateWindow(width, height, "Flappy Bird", NULL, NULL);
		if(window == 0) {
			// TODO: handle error
			return;
		}
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		
		glfwSetKeyCallback(window, new Input());
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		GL.createCapabilities();
		
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		System.out.println("OpenGL: " + glGetString(GL_VERSION));
		// inizializziamo gli shader
		Shader.loadAll();
		// bisogna legare ciascuno shader al contesto
		Shader.BACKGROUND.enable();
		// la matrice di proiezione funziona in modo che l'estrema sinistra visualizzata sia a -10.0f, la destra a 10.0f
		// la aspect ratio in 16:9 è normalizzata su questi numeri, quindi l'altezza è normalizzata sulla largezza
		Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f);
		// la matrice di proiezione deve essere settata per ogni shader
		Shader.BACKGROUND.setUniformMat4f("pr_matrix", pr_matrix);
		// viene passata allo Shader come matrice 4x4, variabile uniforme
		Shader.BACKGROUND.disable();
		
		level = new Level();
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
		level.render();
		int error = glGetError();
		if(error != GL_NO_ERROR) {
			System.out.println(error);
		}
		glfwSwapBuffers(window);
	}

	public static void main(String[] args) {
		new Main().start();

	}

}
