package polito.sharpen42.Flappy.math;

import java.nio.FloatBuffer;

import polito.sharpen42.Flappy.utils.BufferUtility;

public class Matrix4f {
	public static final int SIZE = 4 * 4;
	public float[] elements = new float[SIZE];
	
	public Matrix4f() {
		for(int i = 0; i < SIZE; elements[i++] = 0.0f);
	}
	
	public static Matrix4f identity() {
		Matrix4f result = new Matrix4f();
		for(int i = 0; i < SIZE; i++) {
			if(i % 5 == 0) result.elements[i] = 1.0f;
			else result.elements[i] = 0.0f;
		}
		return result;
	}
	
	static public Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
		Matrix4f result = identity();
		
		result.elements[0 + 0 * 4] = 2.0f / (right-left);
		result.elements[1 + 0 * 4] = 2.0f / (top-bottom);
		result.elements[2 + 0 * 4] = 2.0f / (near-far);
		
		result.elements[0 + 3 * 4]=(left + right)/(left - right);
		result.elements[1 + 3 * 4]=(top + bottom)/(bottom - top);
		result.elements[2 + 3 * 4]=(far + near)/(far - near);
		
		return result;
	}
	
	public static Matrix4f translate(Vector3f vector) {
		Matrix4f result = identity();
		
		result.elements[0 + 3 * 4] = vector.x;
		result.elements[1 + 3 * 4] = vector.y;
		result.elements[2 + 3 * 4] = vector.z;
		
		return result;
	}
	
	public static Matrix4f rotate(float angle) {
		Matrix4f result = identity();
		float r = (float) Math.toRadians(angle);
		float cos = (float) Math.cos(r);
		float sin = (float) Math.sin(r);
		
		result.elements[0 + 0 * 4] = cos;
		result.elements[1 + 0 * 4] = sin;
		
		result.elements[0 + 1 * 4] = -sin;
		result.elements[1 + 1 * 4] = cos;
		
		return result;
	}
	
	public Matrix4f multiply(Matrix4f matrix) {
		Matrix4f result = new Matrix4f();
		float sum = 0.0f;
		for(int x = 0; x < 4; x++) {
			for(int y = 0; y < 4; y++) {
				for(int e = 0; e < 4; e++) {
					sum += this.elements[ x + e * 4] * matrix.elements[e + y * 4];
				}
				result.elements[x + y * 4] = sum;
				sum = 0.0f;
			}
		}
		
		return result;
	}
	
	public FloatBuffer toFloatBuffer() {
		return BufferUtility.createFloatBuffer(elements);
	}
}

/* 0	1 	2 	3
 * 4	5 	6 	7
 * 8	9 	10 	11
 * 12	13 	14 	15
 * */
