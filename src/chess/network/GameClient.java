package chess.network;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class GameClient {
	
	private List<GameInfo> games;
	private int sendPort, receivePort;
	private MulticastSocket multiSocket;
	private Socket socket;
	private GameClientThread thread;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;
	private GameClientListener listener;
	private boolean connected;
	
	public class GameClientThread extends Thread {
		private boolean running;
		
		public GameClientThread() {
			
		}
		
		@Override
		public void run() {
			running = true;
			byte[] buf = new byte[4096];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			ByteArrayInputStream bis;
			ObjectInputStream in;
			GameInfo gameInfo;
//			System.out.println("client: started thread");
			while (running) {
				gameInfo = null;
				try {
					try {
						multiSocket.receive(packet);
						bis = new ByteArrayInputStream(buf);
						in = new ObjectInputStream(bis);
						gameInfo = (GameInfo) in.readObject();
						gameInfo.startTimer();
					} catch (SocketTimeoutException ste) {
//						System.out.println("client: timed out");
					} 
					synchronized (games) {
						if (gameInfo != null && !games.contains(gameInfo)) {
//							System.out.println("onAddGame(): " + gameInfo.toString());
							games.add(gameInfo);
							if (listener != null) listener.onAddGame(gameInfo);
						} else {
//							System.out.println("checking out " + games.size() + " games");
							for (int i = 0; i < games.size();) {
								GameInfo info = games.get(i);
								if (gameInfo != null && info.getId() == gameInfo.getId()) {
//									System.out.println("refreshed game info: " + info.toString());
//									games.remove(info);
//									games.add(i, gameInfo);
									info.startTimer();
									i++;
								} else if (!info.isValid()) {
//									System.out.println("onRemoveGame(): " + info.toString());
									games.remove(info);
									if (listener != null) listener.onRemoveGame(info);
								}
							}
						}
						Thread.sleep(100);
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
//			System.out.println("client: stopped thread");
		}
		
		public void terminate() {
			running = false;
		}
		
		public boolean isRunning() {
			return running;
		}
	}
	
	public GameClient(InetAddress group, int sendPort, int receivePort) {
		this.sendPort = sendPort;
		this.receivePort = receivePort;
		games = new ArrayList<GameInfo>();
		try {
			multiSocket = new MulticastSocket(receivePort);
			multiSocket.joinGroup(group);
			multiSocket.setSoTimeout(100);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println("client: created multicast socket");
	}
	
	public void setGameClientListener(GameClientListener listener) {
		this.listener = listener;
	}
	
	public void scan() {
//		System.out.println("client: start scanning");
		thread = new GameClientThread();
		thread.start();
	}
	
	public List<GameInfo> getGames() {
		return games;
	}
	
	public void connect(GameInfo gameInfo) {
		try {
			thread.terminate();
			thread.join();
			
	//		System.out.println("client: connecting to " + gameInfo);
			byte[] buf = new byte[0];
			DatagramPacket packet = new DatagramPacket(buf, buf.length, gameInfo.getHost(), sendPort);
			multiSocket.send(packet);
			
	//		System.out.println("client: sent response packet");
			
	//		System.out.println("client: verified stopped thread");
			
			multiSocket.close();
			
			socket = new Socket(gameInfo.getHost(), sendPort);
	//		System.out.println("client: connected via a TCP socket");
	
			socketOut = new ObjectOutputStream(socket.getOutputStream());
			socketOut.flush();
			socketIn = new ObjectInputStream(socket.getInputStream());
			
			connected = true;
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, ioe.getMessage(), "Client Connect Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
	
	public ObjectInputStream getInputStream() {
		return socketIn;
	}
	
	public ObjectOutputStream getOutputStream() {
		return socketOut;
	}
	
	public boolean isConnected() {
		return connected;
	}
	
}
