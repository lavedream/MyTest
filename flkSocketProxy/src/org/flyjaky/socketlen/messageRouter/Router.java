package org.flyjaky.socketlen.messageRouter;



/**
 * 消息路由中心
 * **/
public interface Router {
	
	public byte[] routeMessage(byte[] messsage);
	
	public byte[] routeMessage(String messsage);
	
	
}
