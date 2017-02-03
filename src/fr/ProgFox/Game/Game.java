package fr.ProgFox.Game;
//TESTE
import fr.ProgFox.Game.Entity.EntityManager;
import fr.ProgFox.Math.Vec3;
import fr.ProgFox.Shader.ColorShader;
import fr.ProgFox.Shader.Shader;
import fr.ProgFox.Utils.VertexBuffer.Cube;
import fr.ProgFox.Utils.VertexBuffer.VBO;
import fr.ProgFox.World.World;
import fr.ProgFox.renderer.Camera;
import static org.lwjgl.opengl.GL11.*;
public class Game {
	private Camera cam;
	private World world;
	private VBO crosser;
	private Cube cube;
	private Shader shader;
	private EntityManager entityManager;

	public Game(Camera cam, World world) {
		this.cam = cam;
		this.world = world;
		this.shader = new ColorShader();
		crosser = new VBO();
		entityManager = new EntityManager();
		entityManager.add(cam.player);
		this.cube = new Cube(new Vec3(1, 1, 1));
	}

	public void update() {
		entityManager.update();
		world.update();
	}

	public void input() {
		entityManager.input();
	}

	public void render() {
		entityManager.render();
		world.render(cam.player);

	}

	public void renderGUI() {

		float x = (float) (-cam.player.position.x + cam.player.getDirection().x);
		float y = (float) (-cam.player.position.y - cam.player.getDirection().y);
		float z = (float) (-cam.player.position.z + cam.player.getDirection().z);
		
		cube.add(x, y, z, 0.005f, true);
		cube.render(cam.player, GL_QUADS);
	}
}
