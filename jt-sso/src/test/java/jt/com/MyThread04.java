package jt.com;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * FixThreadPool(int n); 固定大小的线程池
 */
public class MyThread04 {
	public static void main(String[] args) {
		ExecutorService ex = Executors.newFixedThreadPool(5);
		
		for (int i = 0; i < 5; i++) {
			ex.submit(new Runnable() {
				
				@Override
				public void run() {
					for (int j = 0; j < 10; j++) {
						System.out.println(Thread.currentThread().getName()+j);
					}
				}
			});
		}
		ex.shutdown();
	}

}
