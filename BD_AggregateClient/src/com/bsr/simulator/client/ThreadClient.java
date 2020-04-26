package com.bsr.simulator.client;
//import java.io.* ;
public class ThreadClient{
	public static void main(String args[]) throws Exception {	// �����쳣�׳�
		
		
		String deviceId="0526";
		long start = System.currentTimeMillis();
		//开启1000个线程，并发 发送数据包(1,2,3,4)s
		for (int i = 0; i <100; i++) {
			new Thread(new StepcountPackageThread(Integer.parseInt(deviceId+i))).start();
		}
		long end = System.currentTimeMillis();
		System.out.println("消耗时间为"+(end-start));
	}
}
