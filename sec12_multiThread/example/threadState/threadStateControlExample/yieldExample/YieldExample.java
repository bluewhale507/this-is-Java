package sec12_multiThread.example.threadState.threadStateControlExample.yieldExample;

public class YieldExample {
    public static void main(String[] args) {
        /* 각 스레드 호출 후 경과시간을 같이 출력하도록, 스레드 출력의 텀에 0.1초의 텀을 두도록 개선(너무 많은 호출은 스레드 상태 변화 흐름을 간략히 보기 불편함) */
        long startTime = System.currentTimeMillis(); // 기준 시각

        ThreadA threadA = new ThreadA(startTime);
        ThreadB threadB = new ThreadB(startTime);

        threadA.start();
        threadB.start();

        try { Thread.sleep(3000); } catch (InterruptedException e) {}
        threadA.work = false;

        try { Thread.sleep(3000); } catch (InterruptedException e) {}
        threadB.work = false;

        try { Thread.sleep(3000); } catch (InterruptedException e) {}
        threadA.stop = true;
        threadB.stop = true;
    }
}
