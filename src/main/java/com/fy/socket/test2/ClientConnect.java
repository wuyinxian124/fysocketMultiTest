package com.fy.socket.test2;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.drafts.Draft_17;

public class ClientConnect implements Runnable {

	private String HOST;
	private int tagid;

	// private final String HOST = ""ws://222.201.139.159:8877"";
	// private final int PORT = 0;
	private Phaser phaser;

	public ClientConnect(String HOST, Phaser phaser, int tagid) {
		this.HOST = HOST;
		this.phaser = phaser;
		this.tagid = tagid;
	}

	public void start0(String user, String verify) throws InterruptedException,
			URISyntaxException {
		phaser.register();
		WebsocketC client = new WebsocketC(new URI(HOST), new Draft_10());
		client.connect();
		phaser.arriveAndAwaitAdvance();
		TimeUnit.SECONDS.sleep(3);
		client.send(user + ":" + verify + ":" + "homewtb");
		System.out.println("等待线程数目:" + phaser.arriveAndAwaitAdvance());
		int i = 0;
		while (i < 10) {
			int chatid = new Random().nextInt(5);
			client.send("chatroom" + chatid + "##0##" + user + " send a msg "
					+ i);
			i++;
		}
		phaser.arriveAndDeregister();
	}

	@Override
	public void run() {
		try {
			start0("user" + tagid, "verify" + tagid);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
