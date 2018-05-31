package org.flyjaky.socketlen.hypocriticalasynsocket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {

	public static void main(String[] args) throws InterruptedException {
		
		while (true) {
			Socket socket;
			try {
				Thread.sleep(50);
				
				socket = new Socket("127.0.0.1", 8888);
				OutputStream outputStream=socket.getOutputStream();
				outputStream.write("ÄãºÃ".getBytes("UTF-8"));
				outputStream.flush();
				outputStream.close();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
