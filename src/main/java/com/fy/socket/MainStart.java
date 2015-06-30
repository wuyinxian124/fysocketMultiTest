package com.fy.socket;

import com.fy.socket.test2.MultClient;

public class MainStart {

	public static void main(String[] args) {

		// 使用appCient 测试
		new MultMain().start(args);
		
		// 直接使用fysocket 项目中的client
		//new MultClient().start0();
	}

}
