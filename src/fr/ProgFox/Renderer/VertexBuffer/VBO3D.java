package fr.ProgFox.Renderer.VertexBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Math.Mat4;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Renderer.Shader.Shader;

public class VBO3D {
	public FloatBuffer buffer;
	private int vbo;
	private int length = 0;

	public void init(int vertexLenght) {
		buffer = BufferUtils.createFloatBuffer(vertexLenght * 6);
		length = vertexLenght * 6;
	}

	public void addVertex(float x, float y, float z, Vec3 color) {
		buffer.put(x).put(y).put(z).put(color.x).put(color.y).put(color.z);
	}

	public void clearBuffer() {
		if(buffer != null)
		buffer.clear();
	}

	public void end() {
		buffer.flip();
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		buffer = null;
	}

	public void end2() {
		buffer.flip();
		
	}
	
	public void createBuffer() {
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		buffer = null;
		
	}
	
	public void update(float x, float y, float z, Vec3 color) {
		buffer.put(x).put(y).put(z).put(color.x).put(color.y).put(color.z);

	}

	public void updateEnd() {
		buffer.flip();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		for (int i = 0; i < length; i++) {
			glBufferSubData(GL_ARRAY_BUFFER, i, buffer);
		}
	}
	
	public void addQuads(float x, float y, float z, Vec3 color, float s) {
		if(buffer == null)
			buffer = BufferUtils.createFloatBuffer(length);
		
		buffer.put(x + s).put(y).put(z).put(color.x).put(color.y).put(color.z);    //FRONT
		buffer.put(x).put(y).put(z).put(color.x).put(color.y).put(color.z);
		buffer.put(x).put(y + s).put(z).put(color.x).put(color.y).put(color.z);
		buffer.put(x + s).put(y + s).put(z).put(color.x).put(color.y).put(color.z);
		
		buffer.put(x).put(y).put(z + s).put(color.x).put(color.y).put(color.z);
		buffer.put(x + s).put(y).put(z + s).put(color.x).put(color.y).put(color.z);    //BACK
		buffer.put(x + s).put(y + s).put(z + s).put(color.x).put(color.y).put(color.z);
		buffer.put(x).put(y + s).put(z + s).put(color.x).put(color.y).put(color.z);
		
		buffer.put(x + s).put(y + s).put(z).put(color.x * 0.8f).put(color.y * 0.8f).put(color.z * 0.8f);		//UP
		buffer.put(x).put(y + s).put(z).put(color.x * 0.8f).put(color.y * 0.8f).put(color.z * 0.8f);
		buffer.put(x).put(y + s).put(z + s).put(color.x * 0.8f).put(color.y * 0.8f).put(color.z * 0.8f);
		buffer.put(x + s).put(y + s).put(z + s).put(color.x * 0.8f).put(color.y * 0.8f).put(color.z * 0.8f);
		
		buffer.put(x).put(y).put(z).put(color.x * 0.8f).put(color.y * 0.8f).put(color.z * 0.8f);
		buffer.put(x + s).put(y).put(z).put(color.x * 0.8f).put(color.y * 0.8f).put(color.z * 0.8f);		//DOWN
		buffer.put(x + s).put(y).put(z + s).put(color.x * 0.8f).put(color.y * 0.8f).put(color.z * 0.8f);
		buffer.put(x).put(y).put(z + s).put(color.x * 0.8f).put(color.y * 0.8f).put(color.z * 0.8f);
		
		buffer.put(x).put(y).put(z).put(color.x * 0.75f).put(color.y * 0.75f).put(color.z * 0.75f);
		buffer.put(x).put(y).put(z + s).put(color.x * 0.75f).put(color.y * 0.75f).put(color.z * 0.75f);		//LEFT
		buffer.put(x).put(y + s).put(z + s).put(color.x * 0.75f).put(color.y * 0.75f).put(color.z * 0.75f);
		buffer.put(x).put(y + s).put(z).put(color.x * 0.75f).put(color.y * 0.75f).put(color.z * 0.75f);
		
		buffer.put(x + s).put(y).put(z + s).put(color.x * 0.75f).put(color.y * 0.75f).put(color.z * 0.75f);		//RIGHT
		buffer.put(x + s).put(y).put(z).put(color.x * 0.75f).put(color.y * 0.75f).put(color.z * 0.75f);
		buffer.put(x + s).put(y + s).put(z).put(color.x * 0.75f).put(color.y * 0.75f).put(color.z * 0.75f);
		buffer.put(x + s).put(y + s).put(z + s).put(color.x * 0.75f).put(color.y * 0.75f).put(color.z * 0.75f);
		
	}

	public void render(int mode, Mat4 perspective, Mat4 transform, Shader shader) {
		shader.bind();
		shader.setUniform("projectionMatrix", perspective);
		shader.setUniform("modelViewMatrix", transform);
		shader.setUniform("light", Var.light);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * 4, 0);
		glVertexAttribPointer(1, 3, GL_FLOAT, true, 6 * 4, 12);
		glDrawArrays(mode, 0, length);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);
	}

}
