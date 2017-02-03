package fr.ProgFox.Game;

import fr.ProgFox.Game.Entity.Player;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Shader.Shader;
import fr.ProgFox.Utils.VertexBuffer.Cube;
import static org.lwjgl.opengl.GL11.*;
public class SkyBox {
	private Shader shader;
	private Cube skybox;
	public SkyBox(Shader shader){
		this.shader = shader;
		skybox = new Cube(new Vec3(1, 1, 1));
		
	}
	public void render(Player player){
		float x, y, z;
		x = -player.position.x;
		y = -player.position.y;
		z = -player.position.z;
		skybox.add(x, y, z, 64, true);
		skybox.render(player, GL_QUADS);
	}
}
