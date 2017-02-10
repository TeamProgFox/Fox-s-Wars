package fr.ProgFox.Network.Packets;

import java.net.InetAddress;

import fr.ProgFox.Network.NetworkClient;
import fr.ProgFox.Network.Server.Server;

public class PacketConnect extends Packet {

	private int id;
	private String name;
	private float x, y, z;

	public PacketConnect() {
		super(0);

		write(id);
		write(name);
		write(x);
		write(y);
		write(z);
	}

	public void read(String data) {
		readInt(data);
		
		id = readInt(data);
		name = readString(data);
		x = readFloat(data);
		y = readFloat(data);
		z = readFloat(data);
	}

	public void process(Server server, InetAddress address, int port) {
		
	}

	public void process(NetworkClient client, InetAddress address, int port) {
		
	}

}
