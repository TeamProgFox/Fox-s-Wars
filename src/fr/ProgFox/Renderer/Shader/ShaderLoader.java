package fr.ProgFox.Renderer.Shader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ShaderLoader {

	public static int loadShader(String vertexPath, String fragmentPath) {
		int id;
		String vertexSource;
		String fragmentSource;

		id = glCreateProgram();
		if (id == GL_FALSE)
			throw new RuntimeException("Create Shader program error !");
		vertexSource = loadFile(vertexPath);
		fragmentSource = loadFile(fragmentPath);
		glAttachShader(id, createShader(fragmentSource, GL_FRAGMENT_SHADER));
		glAttachShader(id, createShader(vertexSource, GL_VERTEX_SHADER));

		glLinkProgram(id);
		glValidateProgram(id);
		return id;
	}

	private static int createShader(String source, int type) {
		int shader;

		shader = glCreateShader(type);
		if (shader == GL_FALSE)
			throw new RuntimeException("Create Shader error !");

		glShaderSource(shader, source);
		glCompileShader(shader);

		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println(glGetShaderInfoLog(shader, 2048));
			System.exit(1);
		}
		return shader;
	}

	private static String loadFile(String path) {
		BufferedReader reader;
		String buffer;
		String result;

		result = "";
		try {
			reader = new BufferedReader(new FileReader(path));
			while ((buffer = reader.readLine()) != null)
				result += buffer + "\n";
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
