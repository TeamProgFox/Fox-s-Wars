package fr.ProgFox.Renderer.GUI;

import static org.lwjgl.opengl.GL11.*;

import fr.ProgFox.*;
import fr.ProgFox.Math.*;
import fr.ProgFox.Renderer.*;
import fr.ProgFox.Renderer.VertexBuffer2D.*;

public class SimpleText extends VBO2D {

	public SimpleText(String s, int x, int y, Vec3 color) {
		
		init(s.length() * 50);
		
		int startX = x;
		for (char c : s.toLowerCase().toCharArray()) {
			if (c == 'a') {
				for (int i = 0; i < 8; i++) {
					addVertex(x + 1, y - i, color);
					addVertex(x + 7, y - i, color);
				}
				for (int i = 2; i <= 6; i++) {
					addVertex(x + i, y - 8, color);
					addVertex(x + i, y - 4, color);
				}
				x += 8;
			} else if (c == 'b') {
				for (int i = 0; i < 8; i++) {
					addVertex(x + 1, y - i, color);
				}
				for (int i = 1; i <= 6; i++) {
					addVertex(x + i, y, color);
					addVertex(x + i, y - 4, color);
					addVertex(x + i, y - 8, color);
				}
				addVertex(x + 7, y - 5, color);
				addVertex(x + 7, y - 7, color);
				addVertex(x + 7, y - 6, color);

				addVertex(x + 7, y - 1, color);
				addVertex(x + 7, y - 2, color);
				addVertex(x + 7, y - 3, color);
				x += 8;
			} else if (c == 'c') {
				for (int i = 1; i <= 7; i++) {
					addVertex(x + 1, y - i, color);
				}
				for (int i = 2; i <= 5; i++) {
					addVertex(x + i, y, color);
					addVertex(x + i, y - 8, color);
				}
				addVertex(x + 6, y - 1, color);
				addVertex(x + 6, y - 2, color);

				addVertex(x + 6, y - 6, color);
				addVertex(x + 6, y - 7, color);

				x += 8;
			} else if (c == 'd') {
				for (int i = 0; i <= 8; i++) {
					addVertex(x + 1, y - i, color);
				}
				for (int i = 2; i <= 5; i++) {
					addVertex(x + i, y, color);
					addVertex(x + i, y - 8, color);
				}
				addVertex(x + 6, y - 1, color);
				addVertex(x + 6, y - 2, color);
				addVertex(x + 6, y - 3, color);
				addVertex(x + 6, y - 4, color);
				addVertex(x + 6, y - 5, color);
				addVertex(x + 6, y - 6, color);
				addVertex(x + 6, y - 7, color);

				x += 8;
			} else if (c == 'e') {
				for (int i = 0; i <= 8; i++) {
					addVertex(x + 1, y - i, color);
				}
				for (int i = 1; i <= 6; i++) {
					addVertex(x + i, y - 0, color);
					addVertex(x + i, y - 8, color);
				}
				for (int i = 2; i <= 5; i++) {
					addVertex(x + i, y - 4, color);
				}
				x += 8;
			} else if (c == 'f') {
				for (int i = 0; i <= 8; i++) {
					addVertex(x + 1, y - i, color);
				}
				for (int i = 1; i <= 6; i++) {
					addVertex(x + i, y - 8, color);
				}
				for (int i = 2; i <= 5; i++) {
					addVertex(x + i, y - 4, color);
				}
				x += 8;
			} else if (c == 'g') {
				for (int i = 1; i <= 7; i++) {
					addVertex(x + 1, y - i, color);
				}
				for (int i = 2; i <= 5; i++) {
					addVertex(x + i, y, color);
					addVertex(x + i, y - 8, color);
				}
				addVertex(x + 6, y - 1, color);
				addVertex(x + 6, y - 2, color);
				addVertex(x + 6, y - 3, color);
				addVertex(x + 5, y - 3, color);
				addVertex(x + 7, y - 3, color);

				addVertex(x + 6, y - 6, color);
				addVertex(x + 6, y - 7, color);

				x += 8;
			} else if (c == 'h') {
				for (int i = 0; i <= 8; i++) {
					addVertex(x + 1, y - i, color);
					addVertex(x + 7, y - i, color);
				}
				for (int i = 2; i <= 6; i++) {
					addVertex(x + i, y - 4, color);
				}
				x += 8;
			} else if (c == 'i') {
				for (int i = 0; i <= 8; i++) {
					addVertex(x + 3, y - i, color);
				}
				for (int i = 1; i <= 5; i++) {
					addVertex(x + i, y - 0, color);
					addVertex(x + i, y - 8, color);
				}
				x += 7;
			} else if (c == 'j') {
				for (int i = 1; i <= 8; i++) {
					addVertex(x + 6, y - i, color);
				}
				for (int i = 2; i <= 5; i++) {
					addVertex(x + i, y - 0, color);
				}
				addVertex(x + 1, y - 3, color);
				addVertex(x + 1, y - 2, color);
				addVertex(x + 1, y - 1, color);
				x += 8;
			} else if (c == 'k') {
				for (int i = 0; i <= 8; i++) {
					addVertex(x + 1, y - i, color);
				}
				addVertex(x + 6, y - 8, color);
				addVertex(x + 5, y - 7, color);
				addVertex(x + 4, y - 6, color);
				addVertex(x + 3, y - 5, color);
				addVertex(x + 2, y - 4, color);
				addVertex(x + 2, y - 3, color);
				addVertex(x + 3, y - 4, color);
				addVertex(x + 4, y - 3, color);
				addVertex(x + 5, y - 2, color);
				addVertex(x + 6, y - 1, color);
				addVertex(x + 7, y, color);
				x += 8;
			} else if (c == 'l') {
				for (int i = 0; i <= 8; i++) {
					addVertex(x + 1, y - i, color);
				}
				for (int i = 1; i <= 6; i++) {
					addVertex(x + i, y, color);
				}
				x += 7;
			} else if (c == 'm') {
				for (int i = 0; i <= 8; i++) {
					addVertex(x + 1, y - i, color);
					addVertex(x + 7, y - i, color);
				}
				addVertex(x + 3, y - 6, color);
				addVertex(x + 2, y - 7, color);
				addVertex(x + 4, y - 5, color);

				addVertex(x + 5, y - 6, color);
				addVertex(x + 6, y - 7, color);
				addVertex(x + 4, y - 5, color);
				x += 8;
			} else if (c == 'n') {
				for (int i = 0; i <= 8; i++) {
					addVertex(x + 1, y - i, color);
					addVertex(x + 7, y - i, color);
				}
				addVertex(x + 2, y - 7, color);
				addVertex(x + 2, y - 6, color);
				addVertex(x + 3, y - 5, color);
				addVertex(x + 4, y - 4, color);
				addVertex(x + 5, y - 3, color);
				addVertex(x + 6, y - 2, color);
				addVertex(x + 6, y - 1, color);
				x += 8;
			} else if (c == 'o' || c == '0') {
				for (int i = 1; i <= 7; i++) {
					addVertex(x + 1, y - i, color);
					addVertex(x + 7, y - i, color);
				}
				for (int i = 2; i <= 6; i++) {
					addVertex(x + i, y - 8, color);
					addVertex(x + i, y - 0, color);
				}
				x += 8;
			} else if (c == 'p') {
				for (int i = 0; i <= 8; i++) {
					addVertex(x + 1, y - i, color);
				}
				for (int i = 2; i <= 5; i++) {
					addVertex(x + i, y - 8, color);
					addVertex(x + i, y - 4, color);
				}
				addVertex(x + 6, y - 7, color);
				addVertex(x + 6, y - 5, color);
				addVertex(x + 6, y - 6, color);
				x += 8;
			} else if (c == 'q') {
				for (int i = 1; i <= 7; i++) {
					addVertex(x + 1, y - i, color);
					if (i != 1)
						addVertex(x + 7, y - i, color);
				}
				for (int i = 2; i <= 6; i++) {
					addVertex(x + i, y - 8, color);
					if (i != 6)
						addVertex(x + i, y - 0, color);
				}
				addVertex(x + 4, y - 3, color);
				addVertex(x + 5, y - 2, color);
				addVertex(x + 6, y - 1, color);
				addVertex(x + 7, y, color);
				x += 8;
			} else if (c == 'r') {
				for (int i = 0; i <= 8; i++) {
					addVertex(x + 1, y - i, color);
				}
				for (int i = 2; i <= 5; i++) {
					addVertex(x + i, y - 8, color);
					addVertex(x + i, y - 4, color);
				}
				addVertex(x + 6, y - 7, color);
				addVertex(x + 6, y - 5, color);
				addVertex(x + 6, y - 6, color);

				addVertex(x + 4, y - 3, color);
				addVertex(x + 5, y - 2, color);
				addVertex(x + 6, y - 1, color);
				addVertex(x + 7, y, color);
				x += 8;
			} else if (c == 's') {
				for (int i = 2; i <= 7; i++) {
					addVertex(x + i, y - 8, color);
				}
				addVertex(x + 1, y - 7, color);
				addVertex(x + 1, y - 6, color);
				addVertex(x + 1, y - 5, color);
				for (int i = 2; i <= 6; i++) {
					addVertex(x + i, y - 4, color);
					addVertex(x + i, y, color);
				}
				addVertex(x + 7, y - 3, color);
				addVertex(x + 7, y - 2, color);
				addVertex(x + 7, y - 1, color);
				addVertex(x + 1, y - 1, color);
				addVertex(x + 1, y - 2, color);
				x += 8;
			} else if (c == 't') {
				for (int i = 0; i <= 8; i++) {
					addVertex(x + 4, y - i, color);
				}
				for (int i = 1; i <= 7; i++) {
					addVertex(x + i, y - 8, color);
				}
				x += 7;
			} else if (c == 'u') {
				for (int i = 1; i <= 8; i++) {
					addVertex(x + 1, y - i, color);
					addVertex(x + 7, y - i, color);
				}
				for (int i = 2; i <= 6; i++) {
					addVertex(x + i, y - 0, color);
				}
				x += 8;
			} else if (c == 'v') {
				for (int i = 2; i <= 8; i++) {
					addVertex(x + 1, y - i, color);
					addVertex(x + 6, y - i, color);
				}
				addVertex(x + 2, y - 1, color);
				addVertex(x + 5, y - 1, color);
				addVertex(x + 3, y, color);
				addVertex(x + 4, y, color);
				x += 7;
			} else if (c == 'w') {
				for (int i = 1; i <= 8; i++) {
					addVertex(x + 1, y - i, color);
					addVertex(x + 7, y - i, color);
				}
				addVertex(x + 2, y, color);
				addVertex(x + 3, y, color);
				addVertex(x + 5, y, color);
				addVertex(x + 6, y, color);
				for (int i = 1; i <= 6; i++) {
					addVertex(x + 4, y - i, color);
				}
				x += 8;
			} else if (c == 'x') {
				for (int i = 1; i <= 7; i++)
					addVertex(x + i, y - i, color);
				for (int i = 7; i >= 1; i--)
					addVertex(x + i, y - 8 - i, color);
				x += 8;
			} else if (c == 'y') {
				addVertex(x + 4, y, color);
				addVertex(x + 4, y - 1, color);
				addVertex(x + 4, y - 2, color);
				addVertex(x + 4, y - 3, color);
				addVertex(x + 4, y - 4, color);

				addVertex(x + 3, y - 5, color);
				addVertex(x + 2, y - 6, color);
				addVertex(x + 1, y - 7, color);
				addVertex(x + 1, y - 8, color);

				addVertex(x + 5, y - 5, color);
				addVertex(x + 6, y - 6, color);
				addVertex(x + 7, y - 7, color);
				addVertex(x + 7, y - 8, color);
				x += 8;
			} else if (c == 'z') {
				for (int i = 1; i <= 6; i++) {
					addVertex(x + i, y, color);
					addVertex(x + i, y - 8, color);
					addVertex(x + i, y - i, color);
				}
				addVertex(x + 6, y - 7, color);
				x += 8;
			} else if (c == '1') {
				for (int i = 2; i <= 6; i++) {
					addVertex(x + i, y, color);
				}
				for (int i = 1; i <= 8; i++) {
					addVertex(x + 4, y - i, color);
				}
				addVertex(x + 3, y - 7, color);
				x += 8;
			} else if (c == '2') {
				for (int i = 1; i <= 6; i++) {
					addVertex(x + i, y, color);
				}
				for (int i = 2; i <= 5; i++) {
					addVertex(x + i, y - 8, color);
				}
				addVertex(x + 1, y - 7, color);
				addVertex(x + 1, y - 6, color);

				addVertex(x + 6, y - 7, color);
				addVertex(x + 6, y - 6, color);
				addVertex(x + 6, y - 5, color);
				addVertex(x + 5, y - 4, color);
				addVertex(x + 4, y - 3, color);
				addVertex(x + 3, y - 2, color);
				addVertex(x + 2, y - 1, color);
				x += 8;
			} else if (c == '3') {
				for (int i = 1; i <= 5; i++) {
					addVertex(x + i, y - 8, color);
					addVertex(x + i, y, color);
				}
				for (int i = 1; i <= 7; i++) {
					addVertex(x + 6, y - i, color);
				}
				for (int i = 2; i <= 5; i++) {
					addVertex(x + i, y - 4, color);
				}
				x += 8;
			} else if (c == '4') {
				for (int i = 2; i <= 8; i++) {
					addVertex(x + 1, y - i, color);
				}
				for (int i = 2; i <= 7; i++) {
					addVertex(x + i, y - 1, color);
				}
				for (int i = 0; i <= 4; i++) {
					addVertex(x + 4, y - i, color);
				}
				x += 8;
			} else if (c == '5') {
				for (int i = 1; i <= 7; i++) {
					addVertex(x + i, y - 8, color);
				}
				for (int i = 4; i <= 7; i++) {
					addVertex(x + 1, y - i, color);
				}
				addVertex(x + 1, y - 1, color);
				addVertex(x + 2, y, color);
				addVertex(x + 3, y, color);
				addVertex(x + 4, y, color);
				addVertex(x + 5, y, color);
				addVertex(x + 6, y, color);

				addVertex(x + 7, y - 1, color);
				addVertex(x + 7, y - 2, color);
				addVertex(x + 7, y - 3, color);

				addVertex(x + 6, y - 4, color);
				addVertex(x + 5, y - 4, color);
				addVertex(x + 4, y - 4, color);
				addVertex(x + 3, y - 4, color);
				addVertex(x + 2, y - 4, color);
				x += 8;
			} else if (c == '6') {
				for (int i = 1; i <= 7; i++) {
					addVertex(x + 1, y - i, color);
				}
				for (int i = 2; i <= 6; i++) {
					addVertex(x + i, y, color);
				}
				for (int i = 2; i <= 5; i++) {
					addVertex(x + i, y - 4, color);
					addVertex(x + i, y - 8, color);
				}
				addVertex(x + 7, y - 1, color);
				addVertex(x + 7, y - 2, color);
				addVertex(x + 7, y - 3, color);
				addVertex(x + 6, y - 4, color);
				x += 8;
			} else if (c == '7') {
				for (int i = 0; i <= 7; i++)
					addVertex(x + i, y - 8, color);
				addVertex(x + 7, y - 7, color);
				addVertex(x + 7, y - 6, color);

				addVertex(x + 6, y - 5, color);
				addVertex(x + 5, y - 4, color);
				addVertex(x + 4, y - 3, color);
				addVertex(x + 3, y - 2, color);
				addVertex(x + 2, y - 1, color);
				addVertex(x + 1, y, color);
				x += 8;
			} else if (c == '8') {
				for (int i = 1; i <= 7; i++) {
					addVertex(x + 1, y - i, color);
					addVertex(x + 7, y - i, color);
				}
				for (int i = 2; i <= 6; i++) {
					addVertex(x + i, y - 8, color);
					addVertex(x + i, y - 0, color);
				}
				for (int i = 2; i <= 6; i++) {
					addVertex(x + i, y - 4, color);
				}
				x += 8;
			} else if (c == '9') {
				for (int i = 1; i <= 7; i++) {
					addVertex(x + 7, y - i, color);
				}
				for (int i = 5; i <= 7; i++) {
					addVertex(x + 1, y - i, color);
				}
				for (int i = 2; i <= 6; i++) {
					addVertex(x + i, y - 8, color);
					addVertex(x + i, y - 0, color);
				}
				for (int i = 2; i <= 6; i++) {
					addVertex(x + i, y - 4, color);
				}
				addVertex(x + 1, y - 0, color);
				x += 8;
			} else if (c == '.') {
				addVertex(x + 1, y, color);
				x += 2;
			} else if (c == ',') {
				addVertex(x + 1, y, color);
				addVertex(x + 1, y - 1, color);
				x += 2;
			} else if (c == '\n') {
				y -= 10;
				x = startX;
			} else if (c == ' ') {
				x += 8;
			}
		}
		end();

	}

	public void render() {
		glPointSize(1);
		render(GL_POINTS, Main.getMain().getShader(),
				Mat4.orthographic(Display.getWidth(), 0, 0, Display.getHeight(), -1, 1),
				Main.getMain().getCamera().getModelViewMatrix(new Vec3(), new Vec3()));
	}

}
