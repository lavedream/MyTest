package org.flyjaky.socketlen.hypocriticalasynsocket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ServerSocketHandler implements Runnable {

	private Socket serverSocket;

	public ServerSocketHandler(Socket serverSocket) {
		this.serverSocket = serverSocket;
	}

	@Override
	public void run() {

		try {
			InputStream inputStream = serverSocket.getInputStream();

			ByteBuffer readByteAll = null;

			int isReturn = 1;
			int ReadSum = 0;
			while (isReturn != -1) {

				// int available=inputStream.available();
				byte[] buffer = new byte[1024];
				isReturn = inputStream.read(buffer);

				if (isReturn != -1) {
					ReadSum += isReturn;
					byte[] oldByteArray = new byte[0];
					if(readByteAll != null) {
						 oldByteArray = readByteAll.array();
					}
					
					readByteAll = ByteBuffer.allocate(ReadSum);
					
					readByteAll.put(oldByteArray);
					byte[] readBytes=Arrays.copyOf(buffer, isReturn);
					readByteAll.put(readBytes);
				}
				
			}

			String str = new String(readByteAll.array(),"UTF-8");
			System.out.println(str);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
