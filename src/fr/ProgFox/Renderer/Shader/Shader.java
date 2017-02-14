package fr.ProgFox.Renderer.Shader;

import static org.lwjgl.opengl.GL20.*;  
import fr.ProgFox.Math.Mat4;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Utils.Buffer;

public abstract class Shader {
	protected int id;

	public Shader(int id) {
		this.id = id;
	}

	public void bind() {
		glUseProgram(id);
	}

	public void unbind() {
		glUseProgram(0);
	}

	public int getUniform(String name) {
		return glGetUniformLocation(id, name);
	}

	public void setUniform(String name, Mat4 value) {
		glUniformMatrix4(getUniform(name), true, Buffer.createBuffer(value));

	}

	public void setUniform(String name, float value) {
		glUniform1f(getUniform(name), value);

	}

	public void setUniform(String name, Vec3 v) {
		glUniform3f(getUniform(name), v.x, v.y, v.z);
	}

	public int getId() {
		return id;
	}
}
