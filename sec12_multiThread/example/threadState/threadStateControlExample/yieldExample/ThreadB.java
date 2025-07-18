package sec12_multiThread.example.threadState.threadStateControlExample.yieldExample;

public class ThreadB extends Thread {
    public boolean stop = false;
    public boolean work = true;
    private long startTime;

    public ThreadB(long startTime) {
        this.startTime = startTime;
    }

    public void run() {
        while (!stop) {
            if (work) {
                long elapsed = System.currentTimeMillis() - startTime;
                System.out.println("ThreadB 작업 내용 (" + elapsed + "ms)");
                try {
                    Thread.sleep(100); // 0.1초 대기
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Thread.yield();
            }
        }
        System.out.println("ThreadB 종료");
    }
}

