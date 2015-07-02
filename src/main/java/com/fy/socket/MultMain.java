package com.fy.socket;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.java_websocket.util.logger.LoggerUtil;

/**
 * Hello world!
 * 
 */


public class MultMain {
	
	private Logger logger = LoggerUtil.getLogger(this.getClass().getName());
	
	/**
	 * 模拟用户并发数目
	 */
	private final static int StaticNum = 0;
	/**
	 * 用户名开始
	 */
	private final static int StaticLastNum = 100;
	/**
	 * 模拟每个互动室成员数目
	 */
	//private final static int StaticCRNum = 10;
	
	public void start(String[] args) {
		
		
		int connectNum = 0;
		int connectLastNum = 100;
		int sendTimes = 3;
		int sendWaite = 10;
		if(args.length == 4){
			try{
				connectNum = Integer.parseInt(args[0]);
				connectLastNum = Integer.parseInt(args[1]);
				sendTimes = Integer.parseInt(args[2]);
				sendWaite = Integer.parseInt(args[3]);
				logger.log(Level.INFO, "设置参数connectNum is setting .connectNum=" + connectNum+",connectLastNum="+ connectLastNum+",sendTimes=" +sendTimes +",sendWaite"+sendWaite);
				
			}catch(Exception e){
				e.printStackTrace();
			}			
		}else{
			try{
				logger.log(Level.INFO, "设置参数connectNum no setting,so  using default value 0-100 ,3次，间隔10s ." + connectNum+",connectLastNum="+ connectLastNum+",sendTimes=" +sendTimes +",sendWaite"+sendWaite );
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		Phaser phaser=new Phaser();
//		CountDownLatch startCdl = new CountDownLatch(1);// 启动的闸门值为 1
		CountDownLatch doneCdl = new CountDownLatch(connectLastNum - connectNum);// 连接的总数为 100
		
		for (int i = connectNum; i < connectLastNum ; i++) {
			
			SocketConnect sc = new SocketConnect(doneCdl,i, phaser,sendTimes,sendWaite);
			new Thread(sc, "connectThread" + i).start();
			if(i%100 == 99){
				try {
					TimeUnit.SECONDS.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// 记录所有连接线程的开始时间
		long start = System.currentTimeMillis();
		// 所有线程虽然都已建立，并 start。但只有等闸门打开才都开始运行。
//		startCdl.countDown();
		try {
			
			doneCdl.await();// 主线程等待所有连接结束
			// 连接达到峰值后，执行一些测试逻辑代码
			logger.log(Level.INFO, "Terminated: %s\n",phaser.isTerminated());
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 记录所有连接线程的结束时间
		long end = System.currentTimeMillis();
		float execTime = (float)(end - start) / 1000;
		logger.log(Level.INFO, "The task takes time : " + execTime +" (秒）");

	}
}