package org.flyjaky.socketlen.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TimeClientHandle implements Runnable {

	private String host;

	private int port;

	private Selector selector;

	private SocketChannel socketChannel;

	private volatile boolean stop;

	public TimeClientHandle(String host, int port) {
		this.host = host == null ? "127.0.0.1" : host;
		this.port = port;

		try {
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("client出现异常退出！");
			System.exit(1);
		}

	}

	@Override
	public void run() {
		try {
			doConnect();
		} catch (IOException e) {
			e.printStackTrace();
//			System.exit(1);
			stop=true;
		}

		while (!stop) {
			try {

				selector.select(1000);

				Set<SelectionKey> selectedKyes = selector.selectedKeys();
				Iterator<SelectionKey> it = selectedKyes.iterator();

				SelectionKey key = null;
				while (it.hasNext()) {
					key = it.next();
					it.remove();
					try {
						handleInput(key);
					} catch (Exception e) {
						e.printStackTrace();
						if (key != null) {
							key.cancel();
							if (key.channel() != null)
								key.channel().close();
						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
				stop=true;
			}
		}

		System.out.println("线程已经关闭!");

	}

	private void handleInput(SelectionKey key) throws ClosedChannelException, IOException {

		if (key.isValid()) {

			SocketChannel sc = (SocketChannel) key.channel();
			//
			if (key.isConnectable()) {
				if (sc.finishConnect()) {
					sc.register(selector, SelectionKey.OP_READ);
				} else {
					System.exit(1);
				}
			}
			if (key.isReadable()) {
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if (readBytes > 0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes, "UTF-8");
					System.out.println("服务端返回,Now is :" + body);

					this.stop = true;

				} else if (readBytes < 0) {
					// 当读取到-1时，关闭连接
					key.cancel();
					sc.close();
				} else {
					;
				}
			}

		}

	}

	/**
	 * 链接服务器
	 */
	private boolean doConnect() throws IOException {
		boolean isConnectioned = false;
		socketChannel.connect(new InetSocketAddress(host, port));
		if (socketChannel.isConnected()) {
			socketChannel.register(selector, SelectionKey.OP_READ);
		} else {
			System.out.println(socketChannel.isConnected() + "--");
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
			while (!socketChannel.finishConnect()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("test connection");
			}
			// System.out.println(socketChannel.isConnected());
			isConnectioned = socketChannel.isConnected();
			doWrite(socketChannel);
		}
		return isConnectioned;
	}
	
	
	/**
	 * @author Administrator
	 * @date 2018-03-20 15:04
	 * @param retryNum 尝试链接次数
	 * @return 是否链接成功
	 */
	public boolean doReconnection() {
		return doReConnection(0);
	}

	/**
	 * @author Administrator
	 * @date 2018-03-20 15:04
	 * @param retryNum 尝试链接次数
	 * @return 是否链接成功
	 */
	public boolean doReConnection(int retryNum) {
		if (retryNum == 0) {
			retryNum = 3;
		}
		boolean isConnectioned = false;
		try {
			for (int i = 0; i < retryNum; i++) {
				socketChannel.connect(new InetSocketAddress(host, port));

				if (socketChannel.isConnected()) {
					socketChannel.register(selector, SelectionKey.OP_READ);
				} else {
					socketChannel.register(selector, SelectionKey.OP_CONNECT);
					while (!socketChannel.finishConnect()) {
						System.out.println("test connection");
					}
					isConnectioned = socketChannel.isConnected();
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return isConnectioned;

	}

	/**
	 * @author liushuaic
	 * @date 2018-03-20 11:29
	 * @desc 返回是否链接成功
	 */
	public boolean isConnectioned() {
		ByteBuffer buffer=ByteBuffer.allocate(1);
		try {
			socketChannel.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return stop;
	}

	private void doWrite(SocketChannel sc) throws IOException {
		byte[] req = "QUERY TIME ORDER".getBytes();
		ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
		writeBuffer.put(req);
		writeBuffer.flip();
		sc.write(writeBuffer);
		if (!writeBuffer.hasRemaining()) {
			System.out.println("Sendd order 2 server succeed.");
		}
	}

	public static void main(String[] args) {
		TimeClientHandle tch = new TimeClientHandle("127.0.0.1", 8099);
		Thread thread = new Thread(tch);
		thread.setName("thread-time-client-01");
		thread.start();
	}
}
