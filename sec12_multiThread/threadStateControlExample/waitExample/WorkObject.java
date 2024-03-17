package sec12_multiThread.threadStateControlExample.waitExample;

public class WorkObject {
    public synchronized void methodA() {
        //두 스레드가 작업객체의 한 method를 번갈아가며 수행하도록 수정.
        System.out.println(Thread.currentThread().getName());
        if(Thread.currentThread().getName() == "ThreadA") {
            System.out.println("ThreadA의 methodA() 작업 실행");
        } else {
            System.out.println("ThreadB의 methodA() 작업 실행");
        }

//        System.out.println("ThreadA의 methodA() 작업 실행");

        notify();
        try {
            wait();
        } catch (InterruptedException e) {}
    }

    public synchronized void methodB() {
        System.out.println("ThreadB의 methodB() 작업 실행");
        notify();
        try {
            wait();
        } catch (InterruptedException e) {}
    }
}
