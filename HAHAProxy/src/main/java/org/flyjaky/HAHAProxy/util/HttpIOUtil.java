package org.flyjaky.HAHAProxy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

public class HttpIOUtil {

	public static byte[] getHttpRequestData(InputStream inputStream) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

		List<String> readBufferArray = new ArrayList<String>();
		StringBuilder sbuilder = new StringBuilder();
		String line = null;
		boolean haveContentLength = false;
		int contentLength = 0;
		int readContentLength = 0;
		boolean contentStart = false;
		while (null != (line = br.readLine())) {
			readBufferArray.add(line);
			sbuilder.append(line + "\r\n");
			if (!haveContentLength) {
				haveContentLength = line.contains("content-length");
				if (haveContentLength) {
					contentLength = Integer.valueOf(line.split(" ")[1]);
				}
			}
			if (!contentStart) {
				String responseStr = sbuilder.toString();
				contentStart = responseStr.contains("\r\n\r\n");
			}
			if (haveContentLength && contentStart) {
				break;
			}

		}
		CharBuffer cb = CharBuffer.allocate(1024);
		int readNumber = 0;
		while ((readNumber = br.read(cb)) != -1) {
			sbuilder.append(cb.subSequence(0, readNumber-1));
			readContentLength = readContentLength + readNumber;
			if (readContentLength == contentLength) {
				break;
			}
		}
		String responseStr = sbuilder.toString();
		responseStr.replaceAll("localhost", "10.10.22.110");
		return responseStr.getBytes();
	}

}
