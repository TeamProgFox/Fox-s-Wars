package fr.ProgFox.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import fr.ProgFox.Game.Game;
import fr.ProgFox.Game.Entities.Entity;
import fr.ProgFox.Math.Vec3;

public class NetworkClient implements Runnable {
	private int port;
	private boolean isRunning = false;
	private InetAddress address;
	private DatagramSocket socket;
	private Game game;

	public NetworkClient(Game game, String address, int port) {
		try {
			this.address = InetAddress.getByName(address);
			this.port = port;
			this.game = game;
			socket = new DatagramSocket();
			isRunning = true;
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}

		new Thread(this).start();
	}

	public void run() {
		System.out.println("Client running: " + address.getHostAddress() + ":" + port);
		while (isRunning) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);

			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}

			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}

	public void parsePacket(byte[] data, InetAddress address, int port) {
		String msg = new String(data);
		System.out.println(msg);
		int indexOfTab = 0;
		String[] controle = new String[4];
		controle[0] = "";
		controle[1] = "";
		controle[2] = "";
		controle[3] = "";
		
		for (int i = 0; i < msg.length(); i++) {
			if (msg.charAt(i) != ';') {
				controle[indexOfTab] += msg.charAt(i);
			} else {
				indexOfTab++;
			}
		}
		String name = controle[0];
		float x = Float.parseFloat(controle[1]);
		float y = Float.parseFloat(controle[2]);
		float z = Float.parseFloat(controle[3]);
		
		System.out.println(name);
		System.out.println(x);
		System.out.println(y);
		System.out.println(z);
		game.controleEntity(name, x, y, z);
		
	}

	public void send(byte[] data) {
		new Thread("Send") {
			public void run() {
				try {
					DatagramPacket packet = new DatagramPacket(data, data.length, address, port);

					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static void main(String[] args) {
		new NetworkClient(null, "localhost", 2009);
	}
}
