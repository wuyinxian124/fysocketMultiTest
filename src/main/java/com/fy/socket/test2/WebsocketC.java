package com.fy.socket.test2;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.util.logger.LoggerUtil;

public class WebsocketC extends WebSocketClient {
	

	private Logger logger = LoggerUtil.getLogger(this.getClass().getName());
	
	public WebsocketC(URI serverUri, Draft draft) {
		super(serverUri, draft);
	}
	
	public WebsocketC(URI serverUri){
		super(serverUri);
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		logger.log(Level.INFO,"You are connected to ChatServer: " + getURI()  );
	}

	@Override
	public void onMessage(String message) {
		logger.log(Level.INFO,Thread.currentThread().getName()+" got: " + message  );
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		logger.log(Level.INFO, "You have been disconnected from: " + getURI() + "; Code: " + code + " " + reason );
	}

	@Override
	public void onError(Exception ex) {
		logger.log(Level.INFO, "Exception occured ...\n" + ex );
	}

	public void verifyUser(String username,String verifyCode,String url){
		send(username+"##"+verifyCode+"##"+url);
	}
}
