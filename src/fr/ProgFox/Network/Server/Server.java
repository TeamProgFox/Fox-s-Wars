package fr.ProgFox.Network.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.ProgFox.Network.Server.Entities.NetworkPlayer;

public class Server implements Runnable {

	private DatagramSocket socket;
	private int port;

	private List<NetworkPlayer> players = new ArrayList<NetworkPlayer>();

	private Scanner sc;
	private boolean running = false;

	public Server(int port, Scanner scanner) {
		try {
			socket = new DatagramSocket(port);
			this.sc = scanner;
			this.port = port;
		} catch (SocketException e) {
			System.err.println("Server already listening on port: " + port);
			System.err.println("Terminating....");
			System.exit(1);
		}
		running = true;
		new Thread(this, "Server").start();
	}

	public void run() {
		System.out.println("Serter started on port " + port);
		receive();

		while (running) {
			String msg = sc.nextLine();
			if (msg.equals("stop")) {
				socket.close();
				System.exit(0);
			}
		}
	}

	public void receive() {
		new Thread("Receive") {
			public void run() {
				try {
					while (running) {
						byte[] data = new byte[1024];
						DatagramPacket packet = new DatagramPacket(data, data.length);
						socket.receive(packet);

						parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void parsePacket(byte[] data, InetAddress address, int port) {
		String msg = new String(data);
		System.out.println("[CLIENT]" + msg);

	}

	public void send(byte[] data, InetAddress address, int port) {
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

	public void add(NetworkPlayer player) {
		players.add(player);
	}
	public void remove(NetworkPlayer player) {
		players.remove(player);
	}
	public void log(){
		
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Port: ");
		int port = scanner.nextInt();

		new Server(port, scanner);
	}

}
