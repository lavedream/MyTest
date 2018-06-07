package org.flyjaky.socketlen.connection;

import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 连接管理类
 * 用于获取远程连接
 **/
public class ConnectionManager {
	
	
	private ConcurrentMap<SocketAddress,SocketChannel> connections=new ConcurrentHashMap<SocketAddress,SocketChannel>();
	
	public SocketChannel getConnection(SocketAddress socketAddress) {
		return connections.get(socketAddress);
	}
	
	public void addConnection(SocketAddress socketAddress,Socket socket,SocketChannel socketChannel ) {
		connections.put(socketAddress,socketChannel);
	}
	
	
}
