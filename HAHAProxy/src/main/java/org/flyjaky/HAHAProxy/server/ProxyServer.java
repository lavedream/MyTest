package org.flyjaky.HAHAProxy.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

import org.flyjaky.HAHAProxy.util.IOUtil;

public class ProxyServer implements Runnable {

	private ByteBuffer inputBuffer = ByteBuffer.allocate(100);

	private ByteBuffer outputBuffer = ByteBuffer.allocate(100);

	private int port = 80;

	private boolean close = true;

	public ProxyServer(int port) {
		this.port = port;
	}

	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while (close) {
				Socket socket = serverSocket.accept();
				
				OutputStream outputStream=socket.getOutputStream();
				outputStream.write("i'm server".getBytes());
				outputStream.flush();
				InputStream inputStream = socket.getInputStream();

				byte[] requesetData = IOUtil.getDataTwo(inputStream);
				System.out.println(new String(requesetData));
				
//				outputStream.close();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void close() {
		this.close = false;
	}

	public static void main(String[] args) {
		ProxyServer ps = new ProxyServer(8092);

		try {
			Thread serverThread=new Thread(ps);
			serverThread.setName("proxyServerThread");
			serverThread.start();
//			Thread requestThread=new Thread();
//			TcpRequestClient trc = new TcpRequestClient();
//			byte[] responseData=trc.request("127.0.0.1", 8092, "helloworld!".getBytes());
//			System.out.println(new String(responseData));
			Thread.sleep(14000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
