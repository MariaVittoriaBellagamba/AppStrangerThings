package polito.sharpen42.Flappy.level;

import polito.sharpen42.Flappy.graphics.Shader;
import polito.sharpen42.Flappy.graphics.VertexArray;

public class Level {
	private VertexArray background;
	
	public Level() {
		float a = -10.0f, b = 10.0f * 9.0f / 16.0f;
		// dichiariamo prima 4 vertici in coord. xyz
		float[] vertices = new float[] {
			a, 	-b, 0.0f, 		// 0
			a, 	b, 	0.0f,		// 1
			0.0f, 	b, 	0.0f,	// 2
			0.0f, 	-b, 0.0f	// 3
		};
		// poi due triangoli per fare il quadrato
		// gli indici permettono di non rendere ridondante la dichiarazione dei poligoni
		byte[] indices = new byte[] {
			0, 1, 2,
			2, 3, 0
			/* 1 	2
			 * 
			 * 0 	3
			 *   */
		};
		// poi le texture coordinates
		// ogni due cifre rappresentano le coord. UV del vertice sulla texture
		float[] tcs = new float[] {
			0, 1, 	// sinistra, giù
			0, 0, 	// sinistra, su
			1, 0, 	// destra, su
			1, 1 	// destra, giù
		};
		
		background = new VertexArray(vertices, indices, tcs);
	}
	
	public void render() {
		Shader.BACKGROUND.enable(); // attiva lo Shader
		background.render();		// indica il VertexArray
		Shader.BACKGROUND.disable();// disattiva lo Shader
		
	}
}
