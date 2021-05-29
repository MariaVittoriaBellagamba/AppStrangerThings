package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;



public class MainGameLoop {
	
	/**
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();

		//RawModel model = OBJLoader.loadObjModel("stanzaLowPoly_s", loader);
		RawModel model = OBJLoader.loadObjModel("cube", loader);
		//RawModel model = OBJLoader.loadObjModel("tavolino_candela", loader);

		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("Flowers")));
		//TexturedModel cubeModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("white")));

		ModelTexture texture = staticModel.getTexture();
		//texture.setShineDamper(10);
		//texture.setReflectivity(1);
	
		
		Entity entity = new Entity(staticModel, new Vector3f(0,0,0),0,90,0,10);
		Light light = new Light(new Vector3f(3000, 2000, 3000), new Vector3f(1,1,1));
		Camera camera = new Camera();

		/*List<Entity> allCubes = new ArrayList<Entity>();
		Random random = new Random();
		
		for(int i=0; i < 200; i++) {
			float x =  random.nextFloat() * 100 - 50;
			float y =  random.nextFloat() * 100 - 50;
			float z =  random.nextFloat() * -300;
			allCubes.add(new Entity(cubeModel, new Vector3f(x, y, z), random.nextFloat()*180f, random.nextFloat()*180f, 0f, 1f));

		}*/
		
		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()) {
			//game logic
			//render
			camera.move();
			
			
			/*for(Entity cube:allCubes) {
				cube.increaseRotation(0, 1, 0);
				renderer.processEntity(cube);
			}*/
			
			renderer.processEntity(entity);
			//entity.increaseRotation(0, 1, 0);
			
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
