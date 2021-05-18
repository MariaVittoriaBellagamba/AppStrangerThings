package polito.sharpen42.Flappy.graphics;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import static org.lwjgl.opengl.GL11.*;

import javax.imageio.ImageIO;

import polito.sharpen42.Flappy.utils.BufferUtility;

public class Texture {

	private int width, height;
	private int texture;
	
	public Texture(String path) {
		texture = load(path);
	}

	private int load(String path) {
		int a, r, g, b;
		int[] pixels = null;
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0,  0, width, height, pixels, 0, width);
		} catch(IOException e) {
			e.printStackTrace();
		}
		int[] data = new int[width * height];
		for(int i = 0; i < width * height; i++) {
			// il vettore va riordinato e separato per canali
			a = (pixels[i] & 0xff000000) >> 24; // eg: #ff010101 (A, R, G, B )
			r = (pixels[i] & 0xff0000) >> 16;
			g = (pixels[i] & 0xff00) >> 8;
			b = (pixels[i] & 0xff);
			// OpenGL richiede il seguente formato
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}
		int result = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, result);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR); 
		// controlla l'operazione eseguita nello scalamento della finestra
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtility.createIntBuffer(pixels));
		
		glBindTexture(GL_TEXTURE_2D, 0);
		
		return result;
	}
	
	public void bind() {
		// carica la texture nel contesto
		glBindTexture(GL_TEXTURE_2D, texture);
		
	}
	
	public void unbind() {
		// slega la texture dal contesto
		glBindTexture(GL_TEXTURE_2D, 0); 
	}
}
