package org.flyjaky.socketlen.nio;

import org.flyjaky.socketlen.socketKeepAline.ClientKeepAline;

public class ClientEntry {
	
	public void init(String host,int port) {
		
		
		TimeClientHandle tch=new TimeClientHandle(host, port);
//		Thread thread = new Thread(tch);
//		thread.setName("thread-time-client-01");
//		thread.start();
		
		//保活进程
		ClientKeepAline cka=new ClientKeepAline(tch);
		Thread ckaThread = new Thread(cka);
		ckaThread.setName("thread-time-client-keep-alive-01");
		ckaThread.start();
		
	}
	
	public static void main(String[] args) {
		ClientEntry ce=new ClientEntry();
		ce.init("127.0.0.1", 8099);
	}

	
	
}
