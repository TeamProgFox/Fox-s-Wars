package fr.ProgFox.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import fr.ProgFox.Game.Game;

public class NetworkClient implements Runnable {

	private DatagramSocket socket;
	private InetAddress address;
	private int port;
	private Game game;
	private boolean running = false;

	public NetworkClient(Game game, String address, int port) {
		this.game = game;

		try {
			this.address = InetAddress.getByName(address);
			this.port = port;
			socket = new DatagramSocket();
			running = true;
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}

		new Thread(this).start();
	}

	public void run() {
		System.out.println("Client running: " + address.getHostAddress() + ":" + port);

		send("MDR je vien du clien".getBytes());

		while (running) {
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
