package sec12_multiThread;

public class BeepPrintExample2 {
	public static void main(String[] args) {
		//작업객체 생성
		Runnable beepTask = new BeepTask();
		Thread thread = new Thread(beepTask);
		thread.start();
		
		for(int i=0; i<5; i++) {
			System.out.println("띵");
			try {Thread.sleep(500);} catch(Exception e) {}
		}
	}
}
