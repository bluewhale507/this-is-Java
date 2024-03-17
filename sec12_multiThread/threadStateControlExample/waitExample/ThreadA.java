package sec12_multiThread.threadStateControlExample.waitExample;

public class ThreadA extends Thread {
    private WorkObject workObject;
    public ThreadA(WorkObject workObject) {
        this.workObject = workObject;
        setName("ThreadA");
    }

    @Override
    public void run() {
        for(int i=0; i<10; i++) {
            workObject.methodA();
        }
    }

}
