package com.fy.socket.test2;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

public class WebsocketC extends WebSocketClient {

	
	public WebsocketC(URI serverUri, Draft draft) {
		super(serverUri, draft);
	}
	
	public WebsocketC(URI serverUri){
		super(serverUri);
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		System.out.println("You are connected to ChatServer: " + getURI()  );
	}

	@Override
	public void onMessage(String message) {
		System.out.println("got: " + message  );
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		System.out.println( "You have been disconnected from: " + getURI() + "; Code: " + code + " " + reason );
	}

	@Override
	public void onError(Exception ex) {
		System.out.println( "Exception occured ...\n" + ex );
	}

	public void verifyUser(String username,String verifyCode,String url){
		send(username+"##"+verifyCode+"##"+url);
	}
}
