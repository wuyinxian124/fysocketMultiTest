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
	
	public void start(String[] args) {
		
		
		int connectNum = 0;
		int connectLastNum = 200;
		int sendTimes = 3;
		int sendWaite = 10;
		String hostIP = "222.201.139.159";
		if(args.length == 5){
			try{
				connectNum = Integer.parseInt(args[0]);
				connectLastNum = Integer.parseInt(args[1]);
				sendTimes = Integer.parseInt(args[2]);
				sendWaite = Integer.parseInt(args[3]);
				hostIP = args[4];
				logger.log(Level.INFO, "设置参数connectNum is setting .connectNum=" + connectNum+",connectLastNum="+ connectLastNum+",sendTimes=" +sendTimes +",sendWaite="+sendWaite);
				
			}catch(Exception e){
				 logger.log(Level.SEVERE,"error 异常"+e.toString());
			}			
		}else{
			try{
				logger.log(Level.INFO, "设置参数connectNum no setting,so  using default value 0-100 ,3次，间隔10s ." + connectNum+",connectLastNum="+ connectLastNum+",sendTimes=" +sendTimes +",sendWaite="+sendWaite );
			}catch(Exception e){
				 logger.log(Level.SEVERE,"error 异常"+e.toString());
			}
		}
		
		//Phaser phaser=new Phaser();
//		CountDownLatch startCdl = new CountDownLatch(1);// 启动的闸门值为 1
		CountDownLatch doneCdl = new CountDownLatch(connectLastNum - connectNum);// 连接的总数为 100
		
		for (int i = connectNum; i < connectLastNum ; i++) {
			
			SocketConnect sc = new SocketConnect(hostIP,doneCdl,i, null,sendTimes,sendWaite);
			new Thread(sc, "connectThread" + i).start();
//			if(i%100 == 99){
//				try {
//					TimeUnit.SECONDS.sleep(20);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
		}
		// 记录所有连接线程的开始时间
		long start = System.currentTimeMillis();
		// 所有线程虽然都已建立，并 start。但只有等闸门打开才都开始运行。
//		startCdl.countDown();
		try {
			
			doneCdl.await();// 主线程等待所有连接结束
			// 连接达到峰值后，执行一些测试逻辑代码
		//	logger.log(Level.INFO, "Terminated: %s\n",phaser.isTerminated());
			
		} catch (InterruptedException e) {
			 logger.log(Level.SEVERE,"error 异常"+e.toString());
		}
		// 记录所有连接线程的结束时间
		long end = System.currentTimeMillis();
		float execTime = (float)(end - start) / 1000;
		logger.log(Level.INFO, "The task takes time : " + execTime +" (秒）");
		try {
			logger.log(Level.INFO, "等待5秒，然后强制退出");
			TimeUnit.SECONDS.sleep(5);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			 logger.log(Level.SEVERE,"error 异常"+e.toString());
		}
		System.exit(0);
	}
}