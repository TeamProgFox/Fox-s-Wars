package fr.ProgFox.Renderer.Shader;

import static fr.ProgFox.Renderer.Shader.ShaderLoader.*;

public class TextureShader extends Shader {
	private static final String VERTEX_PATH = "res/shaders/textures.vert";
	private static final String FRAGMENT_PATH = "res/shaders/textures.frag";

	public TextureShader() {
		super(loadShader(VERTEX_PATH, FRAGMENT_PATH));
	}
}
