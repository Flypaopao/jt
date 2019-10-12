package jt.com;
/**
 * 覆写Runnable()接口实现多线程，而后同样覆写run().推荐此方式
 * 1)实现Runnable接口避免多继承局限
 * 2)实现Runnable()可以更好的体现共享的概念
 */
public class MyThread02 implements Runnable{

	public static int count = 20;
	@Override
	public void run() {
		while (count>0) {
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+"当前剩余票数"+count--);
		}
	}
	
	public static void main(String[] args) {
		MyThread02 Thread1 = new MyThread02();
		Thread myThread1 = new Thread(Thread1, "线程1");
		Thread myThread2 = new Thread(Thread1, "线程2");
		Thread myThread3 = new Thread(Thread1, "线程3");
		myThread1.start();
		myThread2.start();
		myThread3.start();
	}
}





















