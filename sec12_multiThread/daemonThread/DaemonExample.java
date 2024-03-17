package sec12_multiThread.daemonThread;

public class DaemonExample {
    public static void main(String[] args) {
        AutoSaveThread autoSaveThread = new AutoSaveThread();
        //데몬스레드로 지정하지 않는경우 스레드는 메인스레드가 종료되어도 계속 동작
//        autoSaveThread.setDaemon(true);
        autoSaveThread.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }

        System.out.println("메인 스레드 종료");
    }
}
