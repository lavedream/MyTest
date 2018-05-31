package org.flyjaky.socketlen.hypocriticalasynsocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



/**
 * 伪异步io
 * 
 * 收到请求
 * 测试收到最大连接能到20000以上
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
				System.out.println("连接数量"+connectNumber++);
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
