package sec12_multiThread;

import java.awt.Toolkit;

public class BeepPrintExample1 {
	public static void main(String[] args) {
		//main 스레드만 이용할 경우
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		for(int i=0; i<5; i++) {
			toolkit.beep();
			try {Thread.sleep(500);} catch(Exception e) {}
		}
		
		for(int i=0; i<5; i++) {
			System.out.println("띵");
			try { Thread.sleep(500);} catch(Exception e) {}     
		}
	}

}
