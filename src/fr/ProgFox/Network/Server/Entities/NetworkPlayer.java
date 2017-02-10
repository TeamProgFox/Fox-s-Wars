package fr.ProgFox.Network.Server.Entities;

import java.net.InetAddress;

public class NetworkPlayer {
	public float x, y, z;
	public int id;

	public InetAddress address;
	public int port;

	public NetworkPlayer(int id, float x, float y, float z, InetAddress address, int port) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.address = address;
		this.port = port;
		

	}
}
