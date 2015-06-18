package com.fy.socket;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Bryan-zhou
 * @date 2015年6月16日下午4:34:59
 **/
public class TestThreadSyn implements Runnable{

	private AtomicInteger pingTimes = new AtomicInteger(0);
	
	private Thread work;
	
	public void start0(){
		work = new Thread(this);
		work.start();
		System.out.println("33333");
	}

	@Override
	public void run() {

		Thread.currentThread().setName("testThread");
		
		new Thread(new OtherThread()).start();
		
		boolean pass = true;
		int meme = 0;
		while(pass&&!Thread.currentThread().isInterrupted()){
			if(meme++ <3)
				panduanOP();
			else pass = false;
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("sleep 结束");
				pass = false;
			}
System.out.println("------------");
		}
		System.out.println("4444444444");
	}
	private void  panduanOP(){
		System.out.println("getpingtime = "+pingTimes.get());
		System.out.println("设置0");
		 pingTimes.set(0);
	}
	
	private class OtherThread implements Runnable{

		private int dida = 0;
		@Override
		public void run() {
			// 先增加
			// 然后判断
			// 记得给其他线程留时间设置 pingtime = 0；
			Thread.currentThread().setName("otherThread");
			
			while(pingTimes.getAndIncrement()<5){
				System.out.println("发送-"+pingTimes.get());
				
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		if(work.isAlive())
			work.interrupt();
			
			System.out.println("bucuo");
		}
		
	}
}
