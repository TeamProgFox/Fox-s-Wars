package fr.ProgFox.Utils.Textures;

import static org.lwjgl.opengl.GL11.*;

import fr.ProgFox.Renderer.Shader.Shader;

public class Texture
{
	private int	id;
	private int	width, height;

	public Texture(int id, int width, int height)
	{
		this.id = id;
		this.width = width;
		this.height = height;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public void bind(Shader shader)
	{
		glBindTexture(GL_TEXTURE_2D, id);
	}

	public static void unbind(Shader shader)
	{
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}