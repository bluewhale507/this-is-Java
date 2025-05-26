package sec11_basicAPI.example.objectClass.finalize;

public class FinalizeExample {
    public static void main(String[] args) {
        Counter counter = null;
        for(int i=0; i<=500; i++) {
            counter = new Counter(i);
            counter = null;
            System.gc();
        }
    }
}
