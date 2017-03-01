package fr.ProgFox.Network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import fr.ProgFox.Game.Game;
import fr.ProgFox.Math.Vec3;

public class NetworkClient {
	String username;
	ArrayList<String> users = new ArrayList();
	Boolean isConnected = false;

	Socket sock;
	BufferedReader reader;
	PrintWriter writer;
	Game game;
	int size = 0;
	int realSize;

	public NetworkClient(String address, int port, String username, Game game) {
		if (isConnected == false) {
			this.username = username;
			this.game = game;

			try {
				sock = new Socket(address, port);
				InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(streamreader);
				writer = new PrintWriter(sock.getOutputStream());
				writer.println(username + ";connected");
				writer.flush();
				isConnected = true;
			} catch (Exception ex) {
				System.out.println("Cannot connect ! Try Again");
				System.exit(1);
			}

			receive();

		} else if (isConnected == true) {
			System.out.println("You arre already connected");
			System.exit(0);
		}

	}

	public void send(String message) {
		new Thread("Send") {
			public void run() {
				writer.println(message);
				writer.flush();
			}
		}.start();
	}

	public void receive() {
		new Thread("Receive") {
			public void run() {
				while (true) {
					String stream;
					String[] data;
					try {
						while ((stream = reader.readLine()) != null) {
							data = stream.split(";");
							controle(data);
						}
					} catch (Exception ex) {
					}
				}
			}
		}.start();
	}

	public void controle(String[] data) {
		if (data[0].equals("player")) {
			String name = data[1];
			float x = Float.parseFloat(data[2]);
			float y = Float.parseFloat(data[3]);
			float z = Float.parseFloat(data[4]);

			game.addClientPlayer(name, x, y, z);
		} else if (data[0].equals("controle")) {
			String name = data[1];
			float x = Float.parseFloat(data[2]);
			float y = Float.parseFloat(data[3]);
			float z = Float.parseFloat(data[4]);

			game.controlePlayer(name, x, y, z);
		} else if (data[0].equals("removeBlock")) {
			float x = Float.parseFloat(data[1]);
			float y = Float.parseFloat(data[2]);
			float z = Float.parseFloat(data[3]);

			game.removeBlock2(new Vec3(x, y, z));

		} else if (data[0].equals("addBlock")) {
			float x = Float.parseFloat(data[1]);
			float y = Float.parseFloat(data[2]);
			float z = Float.parseFloat(data[3]);
			String block = data[4];
			game.addBlock2(new Vec3(x, y, z), block);

		} else if (data[0].equals("nombre")) {
			realSize = Integer.parseInt(data[1]);

		} else if (data[0].equals("addBlockConnect")) {
			float x = Float.parseFloat(data[1]);
			float y = Float.parseFloat(data[2]);
			float z = Float.parseFloat(data[3]);
			String block = data[4];
			game.addBlock(new Vec3(x, y, z), block);
			size++;
		} else if (data[0].equals("removeBlockConnect")) {
			float x = Float.parseFloat(data[1]);
			float y = Float.parseFloat(data[2]);
			float z = Float.parseFloat(data[3]);

			game.removeBlock(new Vec3(x, y, z));
			size++;
		}

		if (realSize == size) {
			game.canContinue = true;
		}
	}

}
