package renderEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		List<Float> verticesArray = new ArrayList<Float>();
		List<Float> normalsArray = new ArrayList<Float>();
		List<Float> textureArray = new ArrayList<Float>();
		List<Integer> indicesArray = new ArrayList<Integer>();
		
		try { 
			String nameO;  
			while(!(line = reader.readLine()).startsWith("o "));
			String[] words = line.split(" ");
			nameO = words[1]; //nome primo oggetto
			
			Mesh mesh = new Mesh(nameO); 
			objects.put(nameO, mesh); //inserimento primo oggetto
			
			while((line = reader.readLine())!= null) {
				String[] currentLine = line.split(" ");
				
				if(line.startsWith("v ")) {
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					mesh.addVertex(vertex);
				} else if(line.startsWith("vt ")) {
					Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
					mesh.addTexture(texture);
					
				}else if(line.startsWith("vn ")) {
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					mesh.addNormal(normal);
					
				}else if(line.startsWith("o ")) {
					String name1 = currentLine[1];
					mesh = new Mesh(name1);
					objects.put(name1, mesh);
					
				}else if(line.startsWith("f ")) {
					currentLine = line.split(" ");
				
					String[] vertex1 = currentLine[1].split("/"); //[Vi, Ti, Ni]
					String[] vertex2 = currentLine[2].split("/");
					String[] vertex3 = currentLine[3].split("/");
					
					processVertex(vertex1, mesh, textureArray, normalsArray);
					processVertex(vertex2, mesh, textureArray, normalsArray);
					processVertex(vertex3, mesh, textureArray, normalsArray);
					
					line = reader.readLine();
					
				}	
			}
			
			/* while(true) {
				line = reader.readLine();
				String[] currentLine = line.split(" ");
				
				if(line.startsWith("v ")) {
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					vertices.add(vertex);
					
				} else if(line.startsWith("vt ")) {
					Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
					textures.add(texture);
					
				}else if(line.startsWith("vn ")) {
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					normals.add(normal);
					
				}else if(line.startsWith("f ")) {
					textureArray = new float[vertices.size()*2];
					normalsArray = new float[vertices.size()*3];
					break;
					
				}
			}
			
			while(line != null) {
				if (!line.startsWith("f ")) {
					line = reader.readLine();
					continue;
				}
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/"); //[Vi, Ti, Ni]
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				
				processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
				processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
				processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
				line = reader.readLine();
				
			} */
			
		reader.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		for(Mesh m : objects.values()) {
			int vertexPointer = 0;
			for(Vector3f vertex: m.getVertices()) {
				verticesArray.add(vertex.x);
				verticesArray.add(vertex.y);
				verticesArray.add(vertex.z);
			}
			for(int i=0; i< m.getIndices().size(); i++) {
				indicesArray.add(i,m.getIndices().get(i));
			}
			
		}
		
		float[] va = new float[verticesArray.size()];
		float[] tc = new float[textureArray.size()];
		float[] vn = new float[normalsArray.size()];
		int[] id = new int[indicesArray.size()];
		
		int i=0; 
		for( float elem : verticesArray ) {
			va[i] = elem; 
			i++;
		}
		
		i=0; 
		for( float elem : textureArray ) {
			tc[i] = elem; 
			i++;
		}
		
		i=0; 
		for( float elem : normalsArray ) {
			vn[i] = elem; 
			i++;
		}
		
		i=0; 
		for( int elem : indicesArray ) {
			id[i] = elem; 
			i++;
		}
		
		return loader.loadToVAO( va, tc, vn, id);
	}
	
	private static void processVertex(String[] vertexData, Mesh object, List<Float> textureArray, List<Float> normalsArray) {
		
		int currentVertexPointer = Integer.parseInt(vertexData[0]) -1;
		object.getIndices().add(currentVertexPointer);
		Vector2f currentTex = object.getTextures().get(Integer.parseInt(vertexData[1])-1);
		textureArray.add(currentVertexPointer*2, currentTex.x);
		textureArray.add(currentVertexPointer*2+1, 1 - currentTex.y);
		Vector3f currentNorm = object.getNormals().get(Integer.parseInt(vertexData[2])-1);
		normalsArray.add(currentVertexPointer*3, currentNorm.x);
		normalsArray.add(currentVertexPointer*3+1, currentNorm.y);
		normalsArray.add(currentVertexPointer*3+2, currentNorm.z);


	}
}
	
