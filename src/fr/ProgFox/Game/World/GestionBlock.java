package fr.ProgFox.Game.World;

public class GestionBlock {

	private float x, y, z;
	private String block;
	private boolean remove;

	public GestionBlock(float x, float y, float z, String block, boolean remove) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		this.remove = remove;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public String getBlock() {
		return block;
	}

	public boolean getAction() {
		return remove;
	}

}
