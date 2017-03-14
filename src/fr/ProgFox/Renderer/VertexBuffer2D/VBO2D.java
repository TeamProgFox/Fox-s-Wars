package fr.ProgFox.Renderer.VertexBuffer2D;

import static org.lwjgl.opengl.GL11.*; 
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Math.Mat4;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Renderer.Shader.Shader;

public class VBO2D {
	private FloatBuffer buffer;
	private int vbo;
	private int length = 0;

	public void init(int vertexLenght) {
		if(buffer != null) {
			buffer.clear();
		}
		buffer = BufferUtils.createFloatBuffer(vertexLenght * 6);
		length = vertexLenght * 6;
	}

	public void addVertex(float x, float y, Vec3 color) {
		buffer.put(x).put(y).put(0).put(color.x).put(color.y).put(color.z);
	}
	
	public void clearBuffer() {
		buffer.clear();
	}

	public void end() {
		buffer.flip();
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		buffer = null;
	}

	public void update(float x, float y, Vec3 color) {
		if (buffer == null) {
			buffer = BufferUtils.createFloatBuffer(length);
		}
		buffer.put(x).put(y).put(0).put(color.x).put(color.y).put(color.z);

	}

	public void updateEnd() {
		buffer.flip();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		for (int i = 0; i < length; i++) {
			glBufferSubData(GL_ARRAY_BUFFER, i, buffer);
		}
	}

	public void render(int mode, Shader shader, Mat4 perspective, Mat4 transform) {
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
