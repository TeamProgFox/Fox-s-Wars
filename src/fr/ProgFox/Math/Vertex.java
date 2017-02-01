package fr.ProgFox.Math;



public class Vertex {
	
	private Vec3 position;
	private Color color;
	private Vec2 textureUV;
	
	public Vertex(Vec3 p, Color c, Vec2 uv) {
		position=p;
		color=c;
		textureUV = uv;
	}

	public Vec2 getTextureUV() {
		return textureUV;
	}

	public Vec3 getPosition() {
		return position;
	}

	public Color getColor() {
		return color;
	}
}
