package sec12_multiThread.example.threadState.threadStateControlExample.waitExample2;

public class DataBox {
    private String data;

    public synchronized String getData() {
        if(this.data == null) {
            try {
                System.out.println("ConsumerThread 대기중...");
                wait(1000);  // 1초간 wait 후 깨어남 (데이터 없으면 다시 대기)
            } catch (InterruptedException e) {}
        }
        String returnValue = data;
        System.out.println("ConsummerThread가 읽은 데이터 : " + returnValue);

        data = null;
        notify();
        return returnValue;
    }

    public synchronized void setData(String data) {
        if(this.data != null) {
            try {
                wait();
            } catch(InterruptedException e) {}
        }
        this.data = data;
        System.out.println("ProducerThread가 생성한 데이터 : " + data);
        notify();
    }
}
