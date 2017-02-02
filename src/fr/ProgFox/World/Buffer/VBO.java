package fr.ProgFox.World.Buffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import fr.ProgFox.Game.Entity.Player;
import fr.ProgFox.Shader.Shader;
import fr.ProgFox.newMath.Vector3f;

public class VBO {
	private FloatBuffer buffer;
	private int vbo;
	private int length = 0;
	private Shader shader;

	public void init(int vertexLenght, Shader shader) {
		this.shader = shader;
		buffer = BufferUtils.createFloatBuffer(vertexLenght * 6);
		length = vertexLenght * 6;
	}

	public void addVertex(float x, float y, float z, Vector3f color) {
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

	public void update(float x, float y, float z, Vector3f color) {
		buffer.put(x).put(y).put(z).put(color.x).put(color.y).put(color.z);
		
	}

	public void updateVBO() {
		buffer.flip();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}

	public void render(Player player, int mode) {
		shader.bind();
		shader.setUniform("perspective", player.getPerspectiveProjection());
		shader.setUniform("perspectivePosition", player.position);
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
