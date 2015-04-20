package com.fy.socket;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import fy.socket.SocketAPPClient.exception.ConnectWebsocketException;
import fy.socket.SocketAPPClient.exception.HandshakeWebsocketException;
import fy.socket.SocketAPPClient.service.APPClient;
import fy.socket.SocketAPPClient.util.logger.LoggerUtil;

/**
 * Hello world!
 * 
 */


public class MultMain {
	
	
	private final static int StaticNum = 200;
	
	public void start(String[] args) {
		
		
		int connectNum = 0;
		
		if(args.length <1){
			connectNum = StaticNum;
			System.out.println("args is null,so set connectNum is " + connectNum);
		}else{
			try{
				connectNum = Integer.parseInt(args[0]);
				System.out.println("connectNum is setting " + connectNum);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		Phaser phaser=new Phaser(connectNum);
//		CountDownLatch startCdl = new CountDownLatch(1);// 启动的闸门值为 1
		CountDownLatch doneCdl = new CountDownLatch(connectNum);// 连接的总数为 100
		
		for (int i = 1; i <= connectNum; i++) {
			SocketConnect tt = new SocketConnect(doneCdl,i, phaser);
			new Thread(tt, "connectThread" + i).start();
		}
		// 记录所有连接线程的开始时间
		long start = System.nanoTime();
		// 所有线程虽然都已建立，并 start。但只有等闸门打开才都开始运行。
//		startCdl.countDown();
		try {
			doneCdl.await();// 主线程等待所有连接结束
			// 连接达到峰值后，执行一些测试逻辑代码
			System.out.printf("Terminated: %s\n",phaser.isTerminated());
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 记录所有连接线程的结束时间
		long end = System.nanoTime();
		System.out
				.println("The task takes time(ms): " + (end - start) / 100000);

	}
}