package org.flyjaky.HAHAProxy.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IOUtil {

	
	
	
	public static byte[] getData(InputStream inputStream) throws IOException {

		byte[] readBuffer = new byte[20];
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
	
	
	public static byte[] getDataTwo(InputStream inputStream) throws IOException {
		ByteBuffer readByteAll = null;

		int isReturn = 1;
		int ReadSum = 0;
		while (isReturn != -1) {

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
		return readByteAll.array();
	}
	
	
}
