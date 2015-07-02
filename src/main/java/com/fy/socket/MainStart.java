package com.fy.socket;

import com.fy.socket.test2.MultClient;

public class MainStart {

	public static void main(String[] args) {

		System.out.println("0 -99 用户，互动室 /10 ,最大id9");
		System.out.println("100 -199 用户，互动室 /10 ,最大id19");
		System.out.println("900 -999 用户，互动室 /10 ,最大id99");
		System.out.println("1000 -1009 用户，互动室 /10 ,最大id100");
		// 使用appCient 测试
		new MultMain().start(args);
		
		// 直接使用fysocket 项目中的client
		//new MultClient().start0();
	}

}
