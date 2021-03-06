package fr.ProgFox.Renderer.GUI;

import com.sun.org.apache.bcel.internal.generic.*;

import fr.ProgFox.Main;
import fr.ProgFox.Math.Vec3;

public class GUIButton extends GUI {

	public boolean isClicked = false;
	public Vec3 defaultColor;
	public SimpleText text;

	public GUIButton(int posX, int posY, int sizeX, int sizeY, Vec3 color, String msg, Vec3 colorMessage) {
		setSize(sizeX, sizeY);
		setPos(posX, posY);
		setColor(color);
		end();
		this.defaultColor = color;
		text = new SimpleText(msg, posX + (sizeX / 2 / 2), posY + (sizeY / 2), colorMessage);
	}

	public void renderGUI() {
		if (Main.getMain().getInput().getMouse().getX() > posX
				&& Main.getMain().getInput().getMouse().getX() < posX + sizeX
				&& Main.getMain().getInput().getMouse().getY() > posY
				&& Main.getMain().getInput().getMouse().getY() < posY + sizeY) {
			Main.getMain().pointButton = true;
			setColor(defaultColor.copy().div(1.25f));
			updateEnd();
			if (Main.getMain().getInput().getMouse().getButtonDown(0)) {
				isClicked = true;
			} else {
				isClicked = false;
			}
		} else {
			Main.getMain().pointButton = false;
			if (getColor().x != defaultColor.x && getColor().y != defaultColor.y && getColor().z != defaultColor.z) {
				setColor(defaultColor);
				updateEnd();
			}

		}
		text.render();
		render();
	}

}
