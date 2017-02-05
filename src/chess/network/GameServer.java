package chess.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;

public class GameServer {

	private InetAddress group;
	private int sendPort, receivePort;
	private String name;
	
	private DatagramSocket dataSocket;
	private Socket socket;
	
	private ObjectInputStream socketIn;
	private ObjectOutputStream socketOut;
	
	public class GameServerThread extends Thread {
		
		private boolean running;
		
		@Override
		public void run() {
			running = true;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out;
			try {
				out = new ObjectOutputStream(bos);
				out.writeObject(new GameInfo(name, InetAddress.getLocalHost()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			byte[] buf = bos.toByteArray();
			DatagramPacket packet = new DatagramPacket(buf, buf.length, group, sendPort);
//			System.out.println("server: thread started");
			while (running) {
				try {
					dataSocket.send(packet);
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
//			System.out.println("server: thread stopped");
		}
		
		public void terminate() {
			running = false;
		}
	}
	
	public GameServer(InetAddress group, int sendPort, int receivePort, String name) {
		this.group = group;
		this.sendPort = sendPort;
		this.receivePort = receivePort;
		this.name = name;
		try {
			dataSocket = new DatagramSocket(receivePort);
		} catch (SocketException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Server Socket Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
//		System.out.println("server: initialized datagram socket");
	}
	
	public void listen() {
		try {
	//		System.out.println("server: listening");
			GameServerThread thread = new GameServerThread();
			thread.start();
			
			byte[] buf = new byte[0];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			dataSocket.receive(packet);
			
	//		System.out.println("server: got response packet");
			
			thread.terminate();
			thread.join();
			
			dataSocket.close();
			
			ServerSocket serverSocket = new ServerSocket(receivePort);
	//		System.out.println("server: created server socket");
			socket = serverSocket.accept();
	//		System.out.println("server: accepted connection");
			serverSocket.close();
			
	//		System.out.println("server: " + (socket.isConnected() ? "connected" : "disconnected"));
			
			socketOut = new ObjectOutputStream(socket.getOutputStream());
			socketOut.flush();
			socketIn = new ObjectInputStream(socket.getInputStream());
	//		System.out.println("server: got socket streams");
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, ioe.getMessage(), "Server Listen Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
	
	public ObjectOutputStream getOutputStream() {
		return socketOut;
	}
	
	public ObjectInputStream getInputStream() {
		return socketIn;
	}

}
