package fr.ProgFox.Network.Packets;

import java.net.InetAddress;

import fr.ProgFox.Network.NetworkClient;
import fr.ProgFox.Network.Server.Server;

public abstract class Packet {
	protected String data;
	private int readIndex = 0;
	public Packet(int packetType) {
		data = "" + packetType;

	}

	public abstract void read(String data);

	public abstract void process(Server server, InetAddress address, int port);

	public abstract void process(NetworkClient client, InetAddress address, int port);

	protected void write(int data) {
		String m = " " + data;
		this.data += m;
	}

	protected void write(float data) {
		String m = " " + data;
		this.data += m;
	}

	protected void write(long data) {
		String m = " " + data;
		this.data += m;
	}

	protected void write(String data) {
		String m = " " + data;
		this.data += m;
	}

	protected int readInt(String data) {
		int value = Integer.parseInt(data.split(" ")[readIndex].trim());
		readIndex++;
		return value;
	}
	protected long readLong(String data) {
		long value = Long.parseLong(data.split(" ")[readIndex].trim());
		readIndex++;
		return value;
	}
	protected float readFloat(String data) {
		float value = Float.parseFloat(data.split(" ")[readIndex].trim());
		readIndex++;
		return value;
	}
	protected String readString(String data) {
		String value = (data.split(" ")[readIndex].trim());
		readIndex++;
		return value;
	}

	public byte[] getData() {
		return data.getBytes();
	}
}
