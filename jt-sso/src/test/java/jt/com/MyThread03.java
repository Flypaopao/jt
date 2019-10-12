package jt.com;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 覆写Callable接口实现多线程（JDK1.5）
 *	1)核心方法叫call()方法，有返回值
 */
public class MyThread03 implements Callable<String>{
	private int count = 20;	
	
	@Override
	public String call() throws Exception {
		for (int i = count; i > 0; i--) {
			//Thread.yield()
			System.out.println(Thread.currentThread().getName()+"当前票数:"+i);
		}
		return "sale out";
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		Callable<String> callable = new MyThread03();
		FutureTask<String> futureTask = new FutureTask<>(callable);
		Thread myThread1 = new Thread(futureTask);
		Thread myThread2 = new Thread(futureTask);
		Thread myThread3 = new Thread(futureTask);
		
		myThread1.start();
		myThread2.start();
		myThread3.start();
		System.out.println(futureTask.get());
	}
}





















