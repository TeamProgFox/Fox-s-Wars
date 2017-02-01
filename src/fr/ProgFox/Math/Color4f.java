package fr.ProgFox.Math;



import java.util.Random;

public class Color4f {
    public static final Color4f WHITE = new Color4f(1f, 1f, 1f, 1f);
    public static final Color4f NULL = new Color4f(-1f, -1f, -1f, -1f);
    public static final Color4f BLACK = new Color4f(0f, 0f, 0f, 1f);

    public static final Color4f GRAY = new Color4f(.5f, .5f, .5f, 1f);
    public static final Color4f DARK_GRAY = new Color4f(.25f, .25f, .25f, 1f);
    public static final Color4f LIGHT_GRAY = new Color4f(.75f, .75f, .75f, 1f);

    public static final Color4f RED = new Color4f(1f, 0f, 0f, 1f);
    public static final Color4f LIGHT_RED = new Color4f(1f, .5f, .5f, 1f);
    public static final Color4f DARK_RED = new Color4f(.25f, 0f, 0f, 1f);

    public static final Color4f GREEN = new Color4f(0f, 1f, 0f, 1f);
    public static final Color4f LIGHT_GREEN = new Color4f(.5f, 1f, .5f, 1f);
    public static final Color4f DARK_GREEN = new Color4f(0f, 0.5f, 0f, 1f);

    public static final Color4f BLUE = new Color4f(0f, 0f, 1f, 1f);
    public static final Color4f LIGHT_BLUE = new Color4f(.5f, .5f, 1, 1f);
    public static final Color4f DARK_BLUE = new Color4f(0f, 0f, .5f, 1f);

    public static final Color4f CYAN = new Color4f(0f, 1f, 1f, 1f);
    public static final Color4f LIGHT_CYAN = new Color4f(.5f, 1f, 1f, 1f);
    public static final Color4f DARK_CYAN = new Color4f(0f, .5f, .5f, 1f);

    public static final Color4f PINK = new Color4f(1f, 0f, 1f, 1f);
    public static final Color4f LIGHT_PINK = new Color4f(1f, .5f, 1f, 1f);
    public static final Color4f DARK_PINK = new Color4f(.5f, 0f, .5f, 1f);

    public static final Color4f YELLOW = new Color4f(1f, 1f, 0f, 1f);
    public static final Color4f LIGHT_YELLOW = new Color4f(1f, 1f, .5f, 1f);
    public static final Color4f DARK_YELLOW = new Color4f(.5f, .5f, 0f, 1f);

    public static final Color4f ORANGE = new Color4f(1f, .5f, 0f, 1f);
    public static final Color4f LIGHT_ORANGE = new Color4f(1f, .75f, .5f, 1f);
    public static final Color4f DARK_ORANGE = new Color4f(.5f, .25f, 0f, 1f);

    public static final Color4f PURPLE = new Color4f(.5f, 0f, 1f, 1f);
    public static final Color4f LIGHT_PURPLE = new Color4f(.75f, .5f, 1f, 1f);
    public static final Color4f DARK_PURPLE = new Color4f(.25f, 0f, .5f, 1f);

    public static Color4f randomColor() {
        Random rand = new Random();
        return new Color4f(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
    }

    public float r, g, b, a;

    public Color4f(Color4f c) {
        this(c.r, c.g, c.b, c.a);
    }

    public Color4f(int rgb) {
        int R = (rgb & 0xff0000) >> 16;
        int G = (rgb & 0xff00) >> 8;
        int B = (rgb & 0xff);

        this.r = (float) R / 255f;
        this.g = (float) G / 255f;
        this.b = (float) B / 255f;
        this.a = 1;
    }

    public Color4f(int r, int g, int b) {
        this((float) r / 255f, (float) g / 255f, (float) b / 255f, 1f);
    }

    public Color4f(int r, int g, int b, int a) {
        this((float) r / 255f, (float) g / 255f, (float) b / 255f, a / 255f);
    }

    public Color4f(float r, float g, float b) {
        this(r, g, b, 1f);
    }

    public Color4f(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public static int getColor(int r, int g, int b, int a) {
        return a << 24 | r << 16 | g << 8 | b;
    }

    public static int getColor(float r, float g, float b, float a) {
        int rr = (int) (r * 255);
        int gg = (int) (g * 255);
        int bb = (int) (b * 255);
        int aa = (int) (a * 255);

        return getColor(rr, gg, bb, aa);
    }

    public static Color4f lerp(Color4f ca, Color4f cb, float t) {
        float r = Mathf.lerp(ca.r, cb.r, t);
        float g = Mathf.lerp(ca.g, cb.g, t);
        float b = Mathf.lerp(ca.b, cb.b, t);
        float a = Mathf.lerp(ca.a, cb.a, t);

        return new Color4f(r, g, b, a);
    }

    public static Color4f cLerp(Color4f ca, Color4f cb, float t) {
        float r = Mathf.cLerp(ca.r, cb.r, t);
        float g = Mathf.cLerp(ca.g, cb.g, t);
        float b = Mathf.cLerp(ca.b, cb.b, t);
        float a = Mathf.cLerp(ca.a, cb.a, t);

        return new Color4f(r, g, b, a);
    }

    public static Color4f sLerp(Color4f ca, Color4f cb, float t) {
        float r = Mathf.sLerp(ca.r, cb.r, t);
        float g = Mathf.sLerp(ca.g, cb.g, t);
        float b = Mathf.sLerp(ca.b, cb.b, t);
        float a = Mathf.sLerp(ca.a, cb.a, t);

        return new Color4f(r, g, b, a);
    }

    public static Color4f mix(Color4f ca, Color4f cb, float t) {
        float r = Math.abs(ca.r + (cb.r - ca.r) * t);
        float g = Math.abs(ca.g + (cb.g - ca.g) * t);
        float b = Math.abs(ca.b + (cb.b - ca.b) * t);

        return new Color4f(r, g, b, ca.getAlpha());
    }

    public float[] toArray() {
        return new float[]{r, g, b, a};
    }

    public Vec3 rgb() {
        return new Vec3(r, g, b);
    }

    public float getRed() {
        return r;
    }

    public void setRed(float red) {
        this.r = red;
    }

    public float getGreen() {
        return g;
    }

    public void setGreen(float green) {
        this.g = green;
    }

    public float getBlue() {
        return b;
    }

    public void setBlue(float blue) {
        this.b = blue;
    }

    public float getAlpha() {
        return a;
    }

    public void setAlpha(float alpha) {
        this.a = alpha;
    }

	/*public int getRGB() {
        int R = (int) (r*255f);
		int G = (int) (g*255f);
		int B = (int) (b*255f);
		int rgb = 0;
		
		rgb = R << 16 | G << 8 | B;
		return rgb;
	}*/

    public int getARGB() {
        int A = (int) (a * 127f);
        int R = (int) (r * 255f);
        int G = (int) (g * 255f);
        int B = (int) (b * 255f);
        int rgb = 0;

        rgb = A << 24 | R << 16 | G << 8 | B;
        return rgb;
    }

    public Color4f add(float v) {
        r += v;
        g += v;
        b += v;
        return this;
    }

    public Color4f add(Color4f v) {
        r += v.r;
        g += v.g;
        b += v.b;
        return this;
    }

    public Color4f sub(float v) {
        r -= v;
        g -= v;
        b -= v;
        return this;
    }

    public Color4f sub(Color4f v) {
        r -= v.r;
        g -= v.g;
        b -= v.b;
        return this;
    }

    public Color4f mul(float v) {
        r *= v;
        g *= v;
        b *= v;
        return this;
    }

    public Color4f mul(Color4f v) {
        r *= v.r;
        g *= v.g;
        b *= v.b;
        return this;
    }

    public static Color4f getColorFromARGB(int argb) {
        Color4f color = new Color4f(0, 0, 0, 0);
        int A = (argb & 0xff000000) >> 24;
        int R = (argb & 0xff0000) >> 16;
        int G = (argb & 0xff00) >> 8;
        int B = (argb & 0xff);

        color.r = (float) R / 255f;
        color.g = (float) G / 255f;
        color.b = (float) B / 255f;
        color.a = (float) A / 127f;

        return color;
    }

    public String toString() {
        return r + " " + g + " " + b + " " + a;
    }

    public Color4f copy() {
        return new Color4f(r, g, b, a);
    }

    public boolean equals(Color4f c) {
        return r == c.r && g == c.g && b == c.b && a == c.a;
    }
}
