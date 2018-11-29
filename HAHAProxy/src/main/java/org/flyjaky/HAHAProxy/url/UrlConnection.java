package org.flyjaky.HAHAProxy.url;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlConnection {

	
	public static void main(String[] args) {
		try {
			URL url=new URL("http", "10.10.212.212", 8110, null);
			URLConnection urlConnection=url.openConnection();
			urlConnection.getHeaderFields();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
