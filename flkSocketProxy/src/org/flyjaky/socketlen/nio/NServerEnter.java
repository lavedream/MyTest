package org.flyjaky.socketlen.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class NServerEnter {

	private int startPort;

	public NServerEnter(int port) {
		this.startPort = port;
	}

	public void startServer() throws IOException {

		ServerSocketChannel acceptSvr = ServerSocketChannel.open();
		acceptSvr.socket().bind(new InetSocketAddress(InetAddress.getByName("IP"), startPort));
		acceptSvr.configureBlocking(false);
		Selector selector = Selector.open();
//		new Thread(new ReactorTask() ).start();
//		
//		SelectionKey key= acceptSvr.register(selector, SelectionKey.OP_ACCEPT, ioHandler);
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
