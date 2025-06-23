package sec12_multiThread.example.createThread;

public class BeepPrintExample3 {
	public static void main(String[] args) {
		//작업객체 생성
		Thread thread = new BeepThread();
		thread.start();
		
		for(int i=0; i<5; i++) {
			System.out.println("띵");
			try {Thread.sleep(500);} catch(Exception e) {}
		}
	}
}
