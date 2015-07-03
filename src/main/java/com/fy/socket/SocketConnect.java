package com.fy.socket;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import fy.socket.JavaWebsocket.exception.ConnectWebsocketException;
import fy.socket.JavaWebsocket.service.APPClient;

import org.java_websocket.util.logger.LoggerUtil;

public class SocketConnect implements Runnable {
	
	
	private Logger logger = LoggerUtil.getLogger(this.getClass().getName());
	/**
	 * 依照模拟用户数目，指定互动时数目。
	 */

	private final int PORT = 8877;
	//private final String HOST = "222.201.139.159";
	
//	private static CountDownLatch startCdl; // 用于启动所有连接线程的闸门
	private static CountDownLatch doneCdl;// 所有连接工作都结束的控制器
	private int tagi;
	private Phaser phaser;
	private int sendTimes;
	private int sendWaite;
	private String hostIP;
	
	
	public SocketConnect(String hostIP, CountDownLatch doneCdl,int i,Phaser phaser,int sendTimes,int sendWaite) {
//		this.startCdl = startCdl;
		this.doneCdl = doneCdl;
		this.tagi = i;
		this.phaser=phaser;
		this.sendWaite = sendWaite;
		this.sendTimes = sendTimes;
		this.hostIP = hostIP;
	}

	public void run() {
		phaser.register();
		// 确保线程都到达。
		
		try {
			// 此处需要代码清单一的那些连接操作
			// new URI("ws://localhost:8887")
			APPClient client = new APPClient(hostIP, PORT,tagi);
			client.connection();

			phaser.arriveAndAwaitAdvance();
			TimeUnit.SECONDS.sleep(5);
			client.verify("user" + tagi, "verify" + tagi, "homewtb");
			logger.log(Level.INFO, "user" + tagi + " conncet and verify ");
			TimeUnit.SECONDS.sleep(10);

			// 等待所有线程一起收发消息
			String chatid = tagi/10 +"";
			logger.log(Level.INFO,"等待线程数目:"+phaser.arriveAndAwaitAdvance());
			for (int i = 0; i < sendTimes; i++) {
				String hello = "send a msg "+i;
				String content ="chatroom" + chatid + "##0##content:"+hello +",senderAccount\":\""+"user" + tagi+"\",\"chatview:"+"chatroom" + chatid ;
				logger.log(Level.INFO,"发送消息 "+content);
				client.sendMsg(content, 0, 0);
				TimeUnit.SECONDS.sleep(sendWaite);
			}


		} catch (ConnectWebsocketException  e) {
			phaser.arriveAndDeregister();
			e.printStackTrace();
		} catch (Exception e) {
			phaser.arriveAndDeregister();
			e.printStackTrace();
		}finally{
			// 等待所有线程一起结束
			// 测试结束
			doneCdl.countDown();
		}
	}
}
