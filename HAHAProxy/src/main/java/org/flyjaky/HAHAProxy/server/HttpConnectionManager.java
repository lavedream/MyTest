package org.flyjaky.HAHAProxy.server;

import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpConnectionManager {
	
	private static Map<String,Socket> connectionMap=new ConcurrentHashMap<String,Socket>();
	
	public static boolean addConnection(String name,Socket socket) {
		connectionMap.put(name, socket);
		return true;
	}
	
	public static boolean removeConnection(String name) {
		connectionMap.remove(name);
		return true;
	}

	public static int getConnectionLength() {
		return connectionMap.size();
	}
	
}
