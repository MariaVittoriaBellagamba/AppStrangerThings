package polito.sharpen42.Flappy.graphics;

import polito.sharpen42.Flappy.math.*;
import polito.sharpen42.Flappy.utils.ShaderUtility;
import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;

public class Shader {
	// gli attributi sono settati pe rogni singolo vertice
	public static final int VERTEX_ATTRIB = 0;
	public static final int TCOORD_ATTRIB = 1;
	
	public static Shader BACKGROUND;
	
	private final int ID;
	
	private Map<String, Integer> locationCache = new HashMap<String, Integer>();
	
	public Shader(String vertex, String fragment) {
		ID = ShaderUtility.load(vertex, fragment);
	}
	
	public static void loadAll() {
		BACKGROUND = new Shader("shaders/bg.vert", "shaders/bg.frag");
	}
	
	public int getUniform(String name) {
		if(locationCache.containsKey(name))
			return locationCache.get(name);
		// le variabili Uniform permettono di passare dati dalla CPU alla GPU
		// si cerca di ottimizzare il programma limitando lo scambio di informazioni fra CPU e GPU, molto lento.
		int result = glGetUniformLocation(ID, name);
		// le posizioni vengono memorizzate in 'locationCache' in modo da non dover cercare i file ogni volta
		if(result == -1) 
			System.err.println("Could not find uniform variable '" + name + "'!");
		else locationCache.put(name, result);
		
		return result;
	}
	
	public void setUniform1i(String name, int value) {
		glUniform1i(getUniform(name), value);
	}
	
	public void setUniform1f(String name, float value) {
		glUniform1f(getUniform(name), value);
	}
	
	public void setUniform2f(String name, float v1, float v2) {
		glUniform2f(getUniform(name), v1, v2);
	}
	
	public void setUniform3f(String name, Vector3f vector) {
		glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
	}
	
	public void setUniformMat4f(String name, Matrix4f matrix) {
		glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
	}
	
	public void enable() {
		glUseProgram(ID);
	}
	
	public void disable() {
		glUseProgram(0);
	}

}
