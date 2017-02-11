package fr.ProgFox.Network.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Server implements Runnable {

	private int port;
	private boolean isRunning = false;

	private DatagramSocket socket;
	private Scanner scanner;

	public Server(int port, Scanner scanner) {
		try {

			socket = new DatagramSocket(port);
			this.scanner = scanner;
			this.port = port;
		} catch (SocketException e) {
			System.err.println("Server already listening on port: " + port);
			System.err.println("Terminating...");
			System.exit(1);
		}

		isRunning = true;
		new Thread(this, "Server").start();
	}

	public void run() {
		System.out.println("Server started on port: " + port);

		receive();
		while (isRunning) {
			String msg = scanner.nextLine();
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
					while (isRunning) {
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
		// System.out.println(msg);

		String send = msg;
		send(send.getBytes(), address, port);
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

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Port: ");
		int port = sc.nextInt();

		new Server(port, sc);

	}
}
