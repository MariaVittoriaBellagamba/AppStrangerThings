package polito.sharpen42.Flappy.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*; // Shaders
import static org.lwjgl.opengl.GL30.*;

import polito.sharpen42.Flappy.utils.BufferUtility; 

public class VertexArray {
	/* vao: vertex array object,
	 * vbo: vertex buffer object, 
	 * ibo: index buffer object
	 * tbo: texture buffer object*/
	private int vao, vbo, ibo, tbo;
	private int count;
	
	public VertexArray(float[] vertices, byte[] indices, float[] textureCoordnates) {
		count = indices.length;
		
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, BufferUtility.createFloatBuffer(vertices), GL_STATIC_DRAW);
		glVertexAttribPointer(Shader.VERTEX_ATTRIB, 3, GL_FLOAT, false, 0, 0); // sono coordinate 3D float
		glEnableVertexAttribArray(Shader.VERTEX_ATTRIB);
		
		tbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, BufferUtility.createFloatBuffer(vertices), GL_STATIC_DRAW);
		glVertexAttribPointer(Shader.TCOORD_ATTRIB, 3, GL_FLOAT, false, 0, 0); // sono coordinate 3D float
		glEnableVertexAttribArray(Shader.TCOORD_ATTRIB);
		
		ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtility.createByteBuffer(indices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
	}
	
	public void bind() {
		glBindVertexArray(vao);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
	}
	
	public void unbind() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	public void draw() {
		glDrawElements(GL_TRIANGLES,  count, GL_UNSIGNED_BYTE, 0);
	}
	
	public void render() {
		bind();
		draw();
	}
}
