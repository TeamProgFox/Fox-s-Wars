package fr.ProgFox.Utils.VertexBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import fr.ProgFox.Game.Variables.Var;
import fr.ProgFox.Math.Mat4;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Renderer.Shader.Shader;

public class VBO {
	private FloatBuffer buffer;
	private int vbo;
	private int length = 0;

	public void init(int vertexLenght) {
		buffer = BufferUtils.createFloatBuffer(vertexLenght * 6);
		length = vertexLenght * 6;
	}

	public void addVertex(float x, float y, float z, Vec3 color) {
		buffer.put(x).put(y).put(z).put(color.x).put(color.y).put(color.z);
	}
	public void clearBuffer(){
		buffer.clear();
	}
	public void end() {
			buffer.flip();
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}

	public void update(float x, float y, float z, Vec3 color) {
		buffer.put(x).put(y).put(z).put(color.x).put(color.y).put(color.z);
		
	}
	public void updateEnd() {
		buffer.flip();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		for(int i  = 0; i < length; i++){
		glBufferSubData(GL_ARRAY_BUFFER, i, buffer);
		}
	}

	public void render(int mode, Mat4 perspective, Vec3 pos, Shader shader) {
		shader.bind();
		shader.setUniform("perspective", perspective);
		shader.setUniform("perspectivePosition", pos);
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
