package fr.ProgFox.Network;

public class PacketBlock {

	public PacketBlock(String action, float x, float y, float z, String block, NetworkClient net) {
		net.send(action + ";" + x + ";" + y + ";" + z + ";" + block);
	}

}
