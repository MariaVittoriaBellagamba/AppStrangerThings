package models;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Mesh {
	private String name; 
	private List<Vector3f> vertices;
	private List<Vector2f> textures;
	private List<Vector3f> normals;
	private List<Integer> indices;
	

	public Mesh(String name) {
		super();
		this.name = name;
		this.vertices = new ArrayList<Vector3f>(); 
		this.textures = new ArrayList<Vector2f>(); 
		this.normals = new ArrayList<Vector3f>(); 
		this.indices = new ArrayList<Integer>(); 
	}
	
	public void addVertex(Vector3f vertex) {
		vertices.add(vertex);
	}
	
	public void addTexture(Vector2f texture) {
		textures.add(texture);
	}
	
	public void addNormal(Vector3f normal) {
		normals.add(normal);
	}
	
	public void addIndex(int index) {
		indices.add(index);
	}
	

	public String getName() {
		return name;
	}

	public List<Vector3f> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vector3f> vertices) {
		this.vertices = vertices;
	}

	public List<Vector2f> getTextures() {
		return textures;
	}

	public void setTextures(List<Vector2f> textures) {
		this.textures = textures;
	}

	public List<Vector3f> getNormals() {
		return normals;
	}

	public void setNormals(List<Vector3f> normals) {
		this.normals = normals;
	}

	public List<Integer> getIndices() {
		return indices;
	}

	public void setIndices(List<Integer> indices) {
		this.indices = indices;
	}
}
