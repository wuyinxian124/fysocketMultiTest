package com.fy.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * 
 * @author wurunzhou
 *
 */
public class Test {
	
	
	public static void main(String[] args) {

		new Test().test7();
	}

	private void test7(){
		new ThreadObject().connect();
	}
	private void test6(){
		String lala = "\"content\":hello,senderAccount\":\"user1\",\"chatview:chatroom1";
		int userAindex = lala.indexOf("senderAccount");
		int userAindex1 = lala.indexOf("\":\"", userAindex);
		int userAindex2 = lala.indexOf("\",\"", userAindex1);
		String sendUserAccout = lala.substring(userAindex1+3, userAindex2); 
		System.out.println(sendUserAccout);
	}
	
	private void test5(){
		boolean pass = true;
		while(pass){
		try{
			
			int lala = 10/0;
		
		}catch(Exception e){
//			logger.log(Level.SEVERE, e.toString());		
			pass = false;
		}
		}

	}
	private void test4(){
		countTime("","");
//		Date begin = new Date();
//		
//		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		TimeUnit.SECONDS.sleep(5);
//		String begins = 
	}
	
	/**
	 * 计算时间差
	 * 
	 * @param begin  
	 * @param end  
	 * @return
	 */
	public  String countTime(String begin,String end){
		int hour = 0;
		int minute = 0;
		long total_minute = 0;
		StringBuffer sb = new StringBuffer();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date begin_date = new Date();
			TimeUnit.SECONDS.sleep(6);
			Date end_date = new Date();

			total_minute = (end_date.getTime() - begin_date.getTime())/(1000*60);

			hour = (int) total_minute/60;
			minute = (int) total_minute%60;
			int mills = (int)(end_date.getTime() - begin_date.getTime())/1000;
			System.out.println(hour+","+minute+","+mills);
			System.out.println(new Date());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		sb.append("工作时间为：").append(hour).append("小时").append(minute).append("分钟");
		return sb.toString();
	}

	
	
	private void test3(){
		int maskLoad = 4;
		int length = 90;
		
		if(0==4){
			System.out.println(true);
		}else{
			System.out.println(false);
		}
	}
	
	private void test2(){
		int b0=0;
		b0|=1<<7;
		int b1 = 0x1%128;
		System.out.println(b1);
	}
	/**
	 * 
	 */
	private void test1(){

//		try{
//			new Test2().test1();
//		}catch (Exception e){
//			logger.log(Level.SEVERE, e.toString());
//		}
	}

//	public  static void main(String[] args){
//		
////		// 62##1##{"chatroomViewId":63,"chatroomName":"����������","parentMsgId":-1,"roomType":2,"content":"5555","sendDate":"2015-05-21 17:39","senderAccount":"zhangliangming","senderName":"������","chatRoomId":62,"messageId":372481,"receiverAccounts":"wtbpublic,","messageType":2,"attachements":null}
////		
////		String lala = "";
////		DataInputStream din = new DataInputStream(System.in);
////		try {
////			lala = din.readLine();
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		String[] mdm = lala.split("##");
////		int lal = mdm[2].indexOf("senderAccount");
////		int lal1 = mdm[2].indexOf("\":\"", lal);
////		int lal2 = mdm[2].indexOf("\",\"", lal1);
////		String meme = mdm[2].substring(lal1+3, lal2); 
////		System.out.println(meme + "."+ lal1 +"," + lal + "," + lal2);
//	}
}
