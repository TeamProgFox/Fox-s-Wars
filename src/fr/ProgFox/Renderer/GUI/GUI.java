package fr.ProgFox.Renderer.GUI;

import static org.lwjgl.opengl.GL11.*;

import fr.ProgFox.Main;
import fr.ProgFox.Math.Mat4;
import fr.ProgFox.Math.Vec2;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Renderer.Display;
import fr.ProgFox.Renderer.VertexBuffer2D.VBO2D;

public class GUI {
	protected int sizeX, sizeY, posX, posY;
	private VBO2D gui;
	private Vec3 color;
	protected boolean isButton = false;

	public void setSize(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	public void setPos(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public Vec2 getPos() {
		return new Vec2(posX, posY);
	}

	public void setColor(Vec3 color) {
		this.color = color;
	}

	public Vec2 getSize() {
		return new Vec2(sizeX, sizeY);
	}

	public void end() {
		gui = new VBO2D();

		gui.init(4);
		gui.addVertex(posX, posY, color);
		gui.addVertex(posX + sizeX, posY, color);
		gui.addVertex(posX + sizeX, posY + sizeY, color);
		gui.addVertex(posX, posY + sizeY, color);
		gui.end();
	}

	public void render() {

		gui.render(GL_QUADS, Main.getMain().getShader(),
				Mat4.orthographic(Display.getWidth(), 0, 0, Display.getHeight(), -1, 1),
				Main.getMain().getCamera().getTransform(new Vec3(), new Vec3()));
	}

}
