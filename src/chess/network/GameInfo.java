package chess.network;

import java.io.Serializable;
import java.net.InetAddress;

@SuppressWarnings("serial")
public class GameInfo implements Serializable {
	
	private String name;
	private long id;
	private InetAddress host;
	private transient long receiveTime;
	
	public GameInfo(String name, InetAddress host) {
		this.host = host;
		this.name = name;
		this.id = System.currentTimeMillis();
	}
	
	public InetAddress getHost() {
		return host;
	}
	
	public String getName() {
		return name;
	}
	
	public long getId() {
		return id;
	}
	
	public void startTimer() {
		receiveTime = System.currentTimeMillis();
	}
	
	public boolean isValid() {
		return (System.currentTimeMillis() - receiveTime) < 2500;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof GameInfo) {
			return ((GameInfo) other).getId() == id;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
//		return String.format("'%s' (%d)", name, id);
		return name;
	}
	

}
