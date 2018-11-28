package org.flyjaky.HAHAProxy.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;

import org.flyjaky.HAHAProxy.util.HttpIOUtil;
import org.flyjaky.HAHAProxy.util.IOUtil;

public class HttpProxyServer implements Runnable {

	private ByteBuffer inputBuffer = ByteBuffer.allocate(100);

	private ByteBuffer outputBuffer = ByteBuffer.allocate(100);

	private int port = 80;

	private boolean close = true;

	public HttpProxyServer(int port) {
		this.port = port;
	}

	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while (close) {
				Socket socket = serverSocket.accept();

				OutputStream outputStream = socket.getOutputStream();
				InputStream inputStream = socket.getInputStream();
				byte[] requesetData = HttpIOUtil.getHttpRequestData(inputStream);
				String requestString = new String(requesetData);
				System.out.println(requestString);

				HttpRequestClient proxyClient = new HttpRequestClient();

				Socket proxySocket = proxyClient.socketFactory("10.10.212.212", 8110);

				OutputStream proxyOutputStream = proxySocket.getOutputStream();
				InputStream porxyInputStream = proxySocket.getInputStream();
				String type="";
				if(null != requestString && requestString.length() > 0) {
					type = requestString.substring(0, requestString.indexOf(" "));
				}

				if ("CONNECT".equalsIgnoreCase(type)) {// https先建立隧道
					outputStream.write("HTTP/1.1 200 Connection Established\r\n\r\n".getBytes());
					outputStream.flush();
				} else {// http直接将请求头转发
					proxyOutputStream.write(requesetData);
				}

				ProxyHandleThread pht = new ProxyHandleThread(porxyInputStream, outputStream);
				pht.start();
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void close() {
		this.close = false;
	}

	public static void main(String[] args) {
		HttpProxyServer ps = new HttpProxyServer(8093);

		Thread serverThread = new Thread(ps);
		serverThread.setName("proxyServerThread");
		serverThread.start();
	}

	class ProxyHandleThread extends Thread {

		private InputStream input;
		private OutputStream output;

		public ProxyHandleThread(InputStream inputStream, OutputStream output) {
			this.input = inputStream;
			this.output = output;
		}

		@Override
		public void run() {
			try {
				while(true) {
					int i=input.read();
					if(i == -1) {break;}
					output.write(i);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
