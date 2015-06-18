package com.fy.socket;

/**
 * @author Bryan-zhou
 * @date 2015年6月18日下午5:21:34
 **/

public class ThreadObject extends Thread {
	
	private boolean beatpass = true;
	
	private Thread work;
	public void connect(){
		work = 	new Thread(new ThreadObject());
		work.start();
	}
	
	public void run() {
		

		System.out.println("Main Method Entry");
 
		work =  new Thread( new OtherThread());
		work.setDaemon(true);
		// When false, (i.e. when it's a user thread), the Worker thread
		// continues to run.
		// When true, (i.e. when it's a daemon thread), the Worker thread
		// terminates when the main thread terminates.
		work.start();
		while (beatpass) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException x) {
				beatpass = false;
			}

		}
		System.out.println("Main Method Exit");
	
	}

	private class OtherThread implements Runnable{

		@Override
		public void run() {


			int dida = 0;
			System.out.println("run Method Entry");
	 
			try {
				System.out.println("In run Method: currentThread() is"
						+ Thread.currentThread());
	 
				while (true) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException x) {
					}
					if(dida++ > 5){beatpass = false; }
					System.out.println("In run method.." + Thread.currentThread());
				}
			} finally {
				System.out.println("run Method Exit");
			}

		}
	}
}