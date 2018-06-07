package org.flyjaky.socketlen.message.messageparser;

import java.net.SocketAddress;

public class FKMessage {
	
	private SocketAddress socketAddress;
	
	private byte[] data;

	public SocketAddress getSocketAddress() {
		return socketAddress;
	}

	public void setSocketAddress(SocketAddress socketAddress) {
		this.socketAddress = socketAddress;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
