package fr.ProgFox.Shader;

import static fr.ProgFox.Shader.ShaderLoader.*;

public class SkyBoxShader extends Shader {
	private static final String VERTEX_PATH = "res/shaders/skybox.vert";
	private static final String FRAGMENT_PATH = "res/shaders/skybox.frag";
	public SkyBoxShader() {
		super(loadShader(VERTEX_PATH, FRAGMENT_PATH));	}

}
