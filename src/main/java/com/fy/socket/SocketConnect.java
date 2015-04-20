package com.fy.socket;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import fy.socket.SocketAPPClient.exception.ConnectWebsocketException;
import fy.socket.SocketAPPClient.exception.HandshakeWebsocketException;
import fy.socket.SocketAPPClient.service.APPClient;
import fy.socket.SocketAPPClient.util.logger.LoggerUtil;

public 
class SocketConnect implements Runnable {
	
	private final int PORT = 8877;
	private final String HOST = "222.201.139.162";
	private Logger logger = LoggerUtil.getLogger(this.getClass().getName());
//	private static CountDownLatch startCdl; // 用于启动所有连接线程的闸门
	private static CountDownLatch doneCdl;// 所有连接工作都结束的控制器
	private int tagi;
	private Phaser phaser;

	
	
	public SocketConnect( CountDownLatch doneCdl,int i,Phaser phaser) {
//		this.startCdl = startCdl;
		this.doneCdl = doneCdl;
		this.tagi = i;
		this.phaser=phaser;
	}

	public void run() {
		// 确保线程都到达。
		phaser.arriveAndAwaitAdvance();
		
		try {
			// 此处需要代码清单一的那些连接操作
			//new URI("ws://localhost:8887")
			try {
				APPClient client = new APPClient(HOST, PORT);
				client.connection();
				
				TimeUnit.SECONDS.sleep(5);
				client.virify("user"+tagi, "verify"+tagi,"homewtb");
				logger.log(Level.INFO, "user"+tagi + " conncet and verify ");
				TimeUnit.SECONDS.sleep(10);
				
				// 等待所有线程一起收发消息
				phaser.arriveAndAwaitAdvance();
				
				for(int i = 0 ;i<60;i++){
					int chatid = new Random().nextInt(5);
					String msg = "chatroom"+chatid+"##"+0+"##"+"user"+tagi+" send a mes "+i + " to chatroom"+chatid;
					logger.log(Level.INFO, "user"+tagi + " send a msg to" + " chatroom"+chatid+" msg=("+msg+")");
					client.sendMsg(msg,0,0);
					TimeUnit.SECONDS.sleep(1);
				}
				
			} catch (ConnectWebsocketException  e) {
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
