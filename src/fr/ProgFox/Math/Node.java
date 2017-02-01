package fr.ProgFox.Math;

public class Node {
	int x, y;

	public Node() {
		this(0, 0);
	}

	public Node(Node n) {
		this(n.x, n.y);
	}

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String toString() {
		return x + ", " + y;
	}
}
