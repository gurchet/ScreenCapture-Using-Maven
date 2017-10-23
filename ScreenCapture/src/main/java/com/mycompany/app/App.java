package com.mycompany.app;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		Thread thread = new Thread() {
			public void run() {
				new SingleButtonapp();
				System.out.println("Main Thread Strated");
				try {
					Thread.sleep(500000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					//System.exit(1);
				};
			}
		};
		thread.start();
		
	}
}
