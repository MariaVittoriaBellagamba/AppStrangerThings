package renderEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.Mesh;
import models.RawModel;

public class OBJLoader {
	private static Map<String, Mesh> objects = new HashMap<String, Mesh>();

	public static RawModel loadObjModel(String fileName, Loader loader) {
		FileReader fr = null;
		
		try {
			fr = new FileReader(new File("res/"+fileName+".obj"));
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't load file");
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(fr);
		String line;
		
		float[] verticesArray = new float[65536]; // 2^16
		float[] normalsArray = new float[65536];
		float[] textureArray = new float[65536];
		int[] indicesArray = new int[65536];
		
		int totalVertices = 0;
		int totalTextures = 0;
		int totalIndices = 0;
		
		try { 
//			String nameO;  
//			while(!(line = reader.readLine()).startsWith("o "));
//			String[] words = line.split(" ");
//			nameO = words[1]; //nome primo oggetto
//			
//			Mesh mesh = new Mesh(nameO); 
//			objects.put(nameO, mesh); //inserimento primo oggetto
			Mesh mesh = null;
			
			while((line = reader.readLine()) != null) {
				String[] currentLine;
				
				if(line.startsWith("o ")) {
					currentLine = line.split(" ");	
					String name1 = currentLine[1];
					objects.put(name1, new Mesh(name1));
					mesh = objects.get(name1);
					line = reader.readLine();
				}
				
				while(line != null && line.startsWith("v ")) {
					currentLine = line.split(" ");
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					mesh.addVertex(vertex);
					totalVertices++;
					line = reader.readLine();
				}
				
				while(line != null && line.startsWith("vt ")) {
					currentLine = line.split(" ");
					Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
					mesh.addTexture(texture);
					totalTextures++;
					line = reader.readLine();
				} 
				
				while(line != null && line.startsWith("vn ")) {
					currentLine = line.split(" ");
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					mesh.addNormal(normal);
					line = reader.readLine();					
				}
				
				while(line != null && line.startsWith("f ")) {
					currentLine = line.split(" ");
					//[Vi, Ti, Ni]
					String[] vertex1 = currentLine[1].split("/");
					String[] vertex2 = currentLine[2].split("/");
					String[] vertex3 = currentLine[3].split("/");
					
					processVertex(vertex1, mesh, textureArray, normalsArray);
					processVertex(vertex2, mesh, textureArray, normalsArray);
					processVertex(vertex3, mesh, textureArray, normalsArray);
					
					line = reader.readLine();
				}
			}
			reader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		int currentVertex = 0;
		int currentTexture = 0;
		int currentNormal = 0;
		for(Mesh m : objects.values()) {			
			for(Vector3f vertex: m.getVertices()) {
				verticesArray[currentVertex++] = vertex.x;
				verticesArray[currentVertex++] = vertex.y;
				verticesArray[currentVertex++] = vertex.z;
			}
			for(int i = 0; i < m.getIndices().size(); i++) {
				indicesArray[totalIndices + i] = m.getIndices().get(i); // qua vengono assegnati gli indici
			}
			totalIndices += m.getIndices().size();
			
		}
		// verticesArray = Arrays.copyOfRange(verticesArray, 0, totalVertices * 3);
		// textureArray = Arrays.copyOfRange(textureArray, 0, totalVertices * 2);
		// normalsArray = Arrays.copyOfRange(normalsArray, 0, totalVertices * 3);
		// indicesArray = Arrays.copyOfRange(indicesArray, 0, totalVertices * 3);
		return loader.loadToVAO(Arrays.copyOfRange(verticesArray, 0, totalVertices * 3), 
								Arrays.copyOfRange(textureArray, 0, totalTextures * 2), 
								Arrays.copyOfRange(normalsArray, 0, totalVertices * 3), 
								Arrays.copyOfRange(indicesArray, 0, totalIndices));
	}
	
	private static void processVertex(String[] vertexData, Mesh mesh, float[] textureArray, float[] normalsArray) {
		
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		mesh.addIndex(currentVertexPointer);
		Vector2f currentTex = mesh.getTextures().get(Integer.parseInt(vertexData[1]) - 1);
		textureArray[currentVertexPointer * 2] = currentTex.x;
		textureArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y;
		Vector3f currentNorm = mesh.getNormals().get(Integer.parseInt(vertexData[2]) - 1);
		normalsArray[currentVertexPointer * 3] = currentNorm.x;
		normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
		normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;
	}
}
	
