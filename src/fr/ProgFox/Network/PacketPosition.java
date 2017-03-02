package fr.ProgFox.Network;

public class PacketPosition {

	public PacketPosition(String pseudo, float x, float y, float z, NetworkClient net) {
		net.send("controle;" + pseudo + ";" + x + ";" + y + ";" + z + ";");
	}

}
