package sec12_multiThread.example.threadState.threadStateControlExample;

import java.awt.*;

public class SleepExample {
    public static void main(String[] args) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        for(int i=0; i<10; i++) {
            toolkit.beep();
            try{
                Thread.sleep(3000);
            } catch (InterruptedException e) {}
        }
    }
}
