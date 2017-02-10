package fr.ProgFox.Renderer.Shader;

import static fr.ProgFox.Renderer.Shader.ShaderLoader.*;

public class ColorShader extends Shader {

	private static final String VERTEX_PATH = "res/shaders/color.vert";
	private static final String FRAGMENT_PATH = "res/shaders/color.frag";

	public ColorShader() {
		super(loadShader(VERTEX_PATH, FRAGMENT_PATH));
	}
}
