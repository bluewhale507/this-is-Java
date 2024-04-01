package sec12_multiThread.threadGroup;

public class WorkThread extends Thread {
    public WorkThread(ThreadGroup threadGroup, String threadName) {
        //상위 클래스(Thread)의 생성자로 넘겨줌 => WorkThread는 전달받은 인자 threadGroup에 속하고, thread이름은 threadName이 된다.
        super(threadGroup, threadName);
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(getName() + " interrupted");
                break;
            }
        }
        System.out.println(getName() + " 종료됨");
    }
}
