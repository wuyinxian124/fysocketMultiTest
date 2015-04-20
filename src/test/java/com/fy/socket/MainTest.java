package com.fy.socket;

import java.util.Random;

public class MainTest {

	public static void main(String[] args) {
		for(int i=1;i<100;i++){
			int ma = new Random().nextInt(5);
			System.out.print(ma+",");
			if(i%10 ==0){
				System.out.println();
			}
		}

	}

}
