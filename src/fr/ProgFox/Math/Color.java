package fr.ProgFox.Math;
import static org.lwjgl.opengl.GL11.glColor4f;

public class Color {
	float red, green, blue, alpha;
	
	public Color(int r, int g, int b) {
		this((float)r/255f, (float)g/255f, (float)b/255f, 1f);
	}
	
	public Color(float r, float g, float b) {
		this(r, g, b, 1f);
	}
	
	public Color(float r, float g, float b, float a) {
		red = r;
		green = g;
		blue = b;
		alpha = a;
	}
	
	public float[] toArray() {
		return new float[] {red, green, blue, alpha};
	}

	public float getRed() {
		return red;
	}

	public void setRed(float red) {
		this.red = red;
	}

	public float getGreen() {
		return green;
	}

	public void setGreen(float green) {
		this.green = green;
	}

	public float getBlue() {
		return blue;
	}

	public void setBlue(float blue) {
		this.blue = blue;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	public void bind() {
		glColor4f(red, green, blue, alpha);
	}
	
	public void bind(int t) {
		float p = (float) t/255f;
		bind(p);
	}
	public void bind(float t) {
		if(t<0) t=0;
		if(t>1) t=1;
		glColor4f(red*t, green*t, blue*t, alpha);
	}
	public void bind(float t, float a) {
		if(t<0) t=0;
		if(t>1) t=1;
		glColor4f(red*t, green*t, blue*t, a);
	}
}
