package org.flyjaky.HAHAProxy.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.lang3.ArrayUtils;
import org.flyjaky.HAHAProxy.util.HttpIOUtil;

public class HttpProxyServer implements Runnable {


	private int port = 80;

	private boolean close = true;
	
	private ServerSocket serverSocket;

	public HttpProxyServer(int port) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket(port);
	}

	public void run() {
		try {
			while (close) {
				Socket socket = serverSocket.accept();

				OutputStream outputStream = socket.getOutputStream();
				InputStream inputStream = socket.getInputStream();
				byte[] requesetData = HttpIOUtil.getHttpRequestDataTwo(inputStream);
				
				String requestString = new String(requesetData);
				
				System.out.println(requestString);

				HttpRequestClient proxyClient = new HttpRequestClient();

				Socket proxySocket = proxyClient.socketFactory("10.10.212.212", 8110);

				OutputStream proxyOutputStream = proxySocket.getOutputStream();
				InputStream porxyInputStream = proxySocket.getInputStream();
				String type = "";
				if (null != requestString && requestString.length() > 0) {
					type = requestString.substring(0, requestString.indexOf(" "));
				}

				if ("CONNECT".equalsIgnoreCase(type)) {// https先建立隧道
					outputStream.write("HTTP/1.1 200 Connection Established\r\n\r\n".getBytes());
					outputStream.flush();
				} else {// http直接将请求头转发
					proxyOutputStream.write(requesetData);
					proxyOutputStream.flush();
				}

				ProxyHandleThread pht = new ProxyHandleThread(porxyInputStream, outputStream);
				pht.start();
//				inputStream.close();
//				proxyOutputStream.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void close() {
		this.close = false;
	}

	public static void main(String[] args) {
		HttpProxyServer ps;
		try {
			ps = new HttpProxyServer(8093);
			Thread serverThread = new Thread(ps);
			serverThread.setName("proxyServerThread");
			serverThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	
	
	class SocketHandler extends Thread{
		
		private Socket socket;
		
		public SocketHandler(Socket socket) {
			this.socket=socket;
		}
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
				while (true) {
					byte[] buffer=new byte[30];
					int readLength=0;
					if ((readLength=input.read(buffer)) == -1) {
						break;
					}
					System.out.println(new String(buffer));
//					HttpIOUtil.getHttpResponseData(input);
				
//					int i = input.read();
//					output.write(HttpIOUtil.getHttpResponseData(input));
//					if (i == -1) {
//						break;
//					}
					if(readLength < 30) {
						output.write(ArrayUtils.subarray(buffer, 0, readLength%30));
					}else {
						output.write(buffer);
					}
					
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					input.close();
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					output.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
