package org.flyjaky.socketlen.socketKeepAline;

import org.flyjaky.socketlen.event.Event;
import org.flyjaky.socketlen.event.SocketEventInterface;
import org.flyjaky.socketlen.nio.TimeClientHandle;

/**
 * @author Administrator
 * @date 2018-03-20 10:40
 * @desc 客户端保活
 * 
 **/
public class ClientKeepAline implements Runnable {

	// 是否在丝 false:不在线 true在线
	// private boolean isOnline = false;

	private TimeClientHandle tch;
	
	private SocketEventInterface seif;

	public ClientKeepAline(TimeClientHandle tch) {
		this.tch = tch;
	}
	
	
	public ClientKeepAline(TimeClientHandle tch,SocketEventInterface seif) {
		this.tch = tch;
		this.seif=seif;
	}

	@Override
	public void run() {
		if(null  == tch) {
			if(null != seif) {
				Event event=new Event(100000,"保活对象未初始化");
				seif.handler(event);
			}else {
				System.err.println("保活对象未初始化！");
			}
		}
		while (true) {
			try {
				Thread.sleep(10000);
				System.out.println("test keep alive");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			boolean isConnectioned=tch.isConnectioned();
			System.out.println("链接是否成功："+isConnectioned);
			if(!isConnectioned) {
				tch.doReconnection();
				Event event=new Event(100001,"重连失败");
				seif.handler(event);
			}
			
			
		}
	}

}
