package org.flyjaky.socketlen.hypocriticalasynsocket;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExcuter {

	private ThreadPoolExecutor tpe = null;

	public ThreadPoolExcuter(int max, int queueSize) {

		tpe = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), max, 500, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
	}

	public void excute(Runnable task) {
		tpe.execute(task);
	}

}
