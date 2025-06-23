package sec12_multiThread.example.threadState.threadStopExample;

public class PrintThread2 extends Thread {
    public void run() {
//        try {
//            while(true) {
//                System.out.println("실행 중");
//                Thread.sleep(1);        //InterruptedException 발생
//            }
//        } catch (InterruptedException e) {}

        while(true) {
            System.out.println("실행 중");
            if(Thread.interrupted()) {  //WAIT발생 대신 interrupted() 메서드 사용
                break;
            }
        }

        System.out.println("자원 정리");
        System.out.println("실행 종료");
    }
}
