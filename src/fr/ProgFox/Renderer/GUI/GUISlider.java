package fr.ProgFox.Renderer.GUI;

import fr.ProgFox.*;
import fr.ProgFox.Math.*;

public class GUISlider extends GUI {
	public boolean isClicked = false;
	private int slideX;
	private int slideY;
	private Vec3 axe;
	public Vec3 defaultColor;

	public GUISlider(int posX, int posY, int sizeX, int sizeY, Vec3 axe, Vec3 color) {
		setSize(sizeX, sizeY);
		setPos(posX, posY);
		setColor(color);
		this.axe = axe;
		this.defaultColor = color;
		end();
	}

	public void renderGUI() {
		if (Main.getMain().getInput().getMouse().getX() > posX
				&& Main.getMain().getInput().getMouse().getX() < posX + sizeX
				&& Main.getMain().getInput().getMouse().getY() > posY
				&& Main.getMain().getInput().getMouse().getY() < posY + sizeY) {
			setColor(defaultColor.copy().div(1.25f));
			updateEnd();
			if (Main.getMain().getInput().getMouse().getButton(0)) {
				if (axe.x == 1) {
					slideX = Main.getMain().getInput().getMouse().getX() - posX - sizeX / 2;
					posX += slideX;
				}
				if (axe.y == 1) {
					slideY = Main.getMain().getInput().getMouse().getY() - posY - sizeY / 2;
					posY += slideY;
				}
				updateEnd();
			}
		} else {
			if (getColor().x != defaultColor.x && getColor().y != defaultColor.y && getColor().z != defaultColor.z) {
				setColor(defaultColor);
				updateEnd();
			}
		}

		render();

	}

}
