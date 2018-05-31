package org.flyjaky.socketlen.hypocriticalasynsocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



/**
 * α�첽io
 * 
 * �յ�����
 * �����յ���������ܵ�20000����
 * */
public class ServerSocketAsync {

	public static void main(String[] args) throws IOException {

		ThreadPoolExcuter tpe=new ThreadPoolExcuter(100, 1000000);
		ServerSocket ss = null;
		int connectNumber=0;
		try {
			ss = new ServerSocket(8888);
			while (true) {
				Socket socket = ss.accept();
				tpe.excute(new ServerSocketHandler(socket));
				System.out.println("��������"+connectNumber++);
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
