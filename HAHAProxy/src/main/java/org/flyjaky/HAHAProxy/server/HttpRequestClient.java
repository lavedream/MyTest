package org.flyjaky.HAHAProxy.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.flyjaky.HAHAProxy.request.RequestHeader;
import org.flyjaky.HAHAProxy.request.modal.SimpleHAHARequestModal;
import org.flyjaky.HAHAProxy.util.IOUtil;

public class HttpRequestClient {

	public byte[] request(String host, int port, byte[] sendData) {

		try {

			Socket clientSocket = new Socket(host, port);

			clientSocket.setTcpNoDelay(true);
			clientSocket.setReceiveBufferSize(1024 * 1024 * 4);

			OutputStream outputStream = clientSocket.getOutputStream();

			outputStream.write(sendData, 0, sendData.length);
			outputStream.flush();

			InputStream inputStream = clientSocket.getInputStream();
			BufferedInputStream dis = new BufferedInputStream(clientSocket.getInputStream());

			byte[] readBuffer = IOUtil.getDataTwo(dis);
			inputStream.close();
			outputStream.close();
			clientSocket.close();
			return readBuffer;

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public InputStream request(String host, int port, InputStream inputStream) throws IOException {

		Socket clientSocket = new Socket(host, port);

		clientSocket.setTcpNoDelay(true);

		OutputStream outputStream = clientSocket.getOutputStream();
		byte[] sendData = getData(inputStream);
		outputStream.write(sendData);

		return clientSocket.getInputStream();

	}

	public Socket socketFactory(String host, int port) throws UnknownHostException, IOException {

		Socket clientSocket = new Socket(host, port);

		clientSocket.setTcpNoDelay(true);
		return clientSocket;
	}

	/**
	 * 输入转人输出
	 */
	public boolean request(InputStream inputStream, OutputStream outputStream) throws IOException {
		byte[] byteBuffer = new byte[1];
		while (inputStream.read(byteBuffer) != -1) {
			outputStream.write(byteBuffer);
		}
		outputStream.flush();   
		return true;

	}

	public byte[] getData(InputStream inputStream) throws IOException {

		byte[] readBuffer = new byte[1024];
		List<byte[]> readBufferArray = new ArrayList<byte[]>();
		int dataLength = 0;
		int readNumber = 0;
		while ((readNumber = inputStream.read(readBuffer)) != -1) {
			readBufferArray.add(readBuffer);
			dataLength = dataLength + readNumber;
		}

		ByteBuffer readData = ByteBuffer.allocateDirect(dataLength);
		for (int i = 0; i < readBufferArray.size(); i++) {
			readData.put(readBufferArray.get(i));
		}
		return readData.array();

	}
	

	public static void main(String[] args) {

		HttpRequestClient trc = new HttpRequestClient();
		byte[] responseData = trc.request("127.0.0.1", 8092, "helloworld".getBytes());
//		System.out.println(new String(responseData));

	}

}
