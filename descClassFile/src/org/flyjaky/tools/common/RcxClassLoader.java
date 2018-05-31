package org.flyjaky.tools.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

public class RcxClassLoader extends URLClassLoader {

	public RcxClassLoader() {
		super(new URL[0]);
	}

	public RcxClassLoader(URL[] urls) {
		super(urls);
	}

	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String fileName = name.replace('.', File.separatorChar).concat(".class");
		InputStream ins = null;
		try {
			for (;;) {
				ins = getResourceAsStream(fileName);
				if ((ins != null) || (fileName.indexOf(File.separatorChar) < 0)) {
					break;
				}
				int lastDot = fileName.lastIndexOf(File.separatorChar);
				fileName = fileName.substring(0, lastDot) + "$" + fileName.substring(lastDot + 1);
				name = name.substring(0, lastDot) + "$" + name.substring(lastDot + 1);
			}
			if ((ins != null) && (findLoadedClass(name) == null)) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int bufferSize = 1024;
				byte[] buffer = new byte[bufferSize];
				int length = 0;
				while ((length = ins.read(buffer)) != -1) {
					baos.write(buffer, 0, length);
				}
				byte[] bytes = baos.toByteArray();
				baos.close();
				bytes = CodeHandler.decode(bytes);
				return defineClass(name, bytes, 0, bytes.length);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}