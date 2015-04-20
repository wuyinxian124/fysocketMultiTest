package com.fy.socket;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import fy.socket.SocketAPPClient.exception.ConnectWebsocketException;
import fy.socket.SocketAPPClient.exception.HandshakeWebsocketException;
import fy.socket.SocketAPPClient.server.APPClient;
import fy.socket.SocketAPPClient.util.logger.LoggerUtil;

/**
 * Hello world!
 * 
 */

class SocketConnect implements Runnable {
	
	private Logger logger = LoggerUtil.getLogger(this.getClass().getName());
//	private static CountDownLatch startCdl; // 用于启动所有连接线程的闸门
	private static CountDownLatch doneCdl;// 所有连接工作都结束的控制器
	private int tagi;
	private Phaser phaser;

	
	
	public SocketConnect( CountDownLatch doneCdl,int i,Phaser phaser) {
//		this.startCdl = startCdl;
//		this.doneCdl = doneCdl;
		this.tagi = i;
		this.phaser=phaser;
	}

	public void run() {
		// 确保线程都到达。
		phaser.arriveAndAwaitAdvance();
		
		try {
//			startCdl.await();
			System.out.println(Thread.currentThread().getName()
					+ " has been working!!!!");
			// 此处需要代码清单一的那些连接操作

			
			//new URI("ws://localhost:8887")
			try {
				APPClient client = new APPClient("localhost", 8877);
				client.connection();
				
				TimeUnit.SECONDS.sleep(5);
				client.virify("user"+tagi, "verify"+tagi,"homewtb");
				logger.log(Level.INFO, "user"+tagi + " conncet and verify ");
				TimeUnit.SECONDS.sleep(10);
				
				// 等待所有线程一起收发消息
				phaser.arriveAndAwaitAdvance();
				
				for(int i = 0 ;i<60;i++){
					int chatid = new Random().nextInt(5);
					String Msg = "chatroom"+chatid+"##"+0+"##"+"user"+tagi+" send a mes "+i + " to chatroom-"+chatid;
					logger.log(Level.INFO, "user"+tagi + "send a msg to" + " chatroom"+chatid);
					client.sendMsg(Msg,100,20);
					TimeUnit.SECONDS.sleep(1);
				}
				
			} catch (ConnectWebsocketException | IOException | URISyntaxException | HandshakeWebsocketException | InterruptedException e) {
				e.printStackTrace();
			}
			// 等待所有线程一起结束
			phaser.arriveAndAwaitAdvance();
			// 测试结束
			phaser.arriveAndDeregister();
			doneCdl.countDown();
		} catch (Exception e) {
			phaser.arriveAndDeregister();
			e.printStackTrace();
		}
	}
}

public class MultMain {
	
	
	private final static int StaticNum = 20;
	
	public static void main(String[] args) {
		
		
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