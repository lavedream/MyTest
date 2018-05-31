package org.flyjaky.socketlen.syncsocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



/**
 * ͬ��io
 * 
 * �յ�����
 * 
 * */
public class ServerSocketSync {

	public static void main(String[] args) throws IOException {

		
		ServerSocket ss = null;
		
		try {
			ss = new ServerSocket(8888);
			while (true) {
				Socket socket = ss.accept();
				new Thread(new ServerSocketHandler(socket)).start();
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (ss != null) {
				System.out.println(" The time server close");
				ss.close();
				ss =null;
			}
		}

	}

}
