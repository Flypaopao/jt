package jt.com;

public class B implements Runnable{

	@Override
	public void run() {
		System.out.println("11");
	}
	public static void main(String[] args) {
		B b = new B();
		Thread thread = new Thread(b);
		thread.start();
	}

	
}
