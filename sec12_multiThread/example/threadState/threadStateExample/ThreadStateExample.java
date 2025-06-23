package sec12_multiThread.example.threadState.threadStateExample;

public class ThreadStateExample {
    public static void main(String[] args) {
        StatePrintThread statePrintThread = new StatePrintThread(new TargetThread());
        statePrintThread.start();
    }
}
